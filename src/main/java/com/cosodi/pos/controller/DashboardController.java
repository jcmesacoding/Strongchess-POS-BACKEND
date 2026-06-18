package com.cosodi.pos.controller;

import com.cosodi.pos.dto.*;
import com.cosodi.pos.repository.ICustomerRepository;
import com.cosodi.pos.repository.IProductRepository;
import com.cosodi.pos.repository.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final IProductRepository productRepository;
    private final ICustomerRepository customerRepository;
    private final ISaleRepository saleRepository;

    @GetMapping
    @org.springframework.transaction.annotation.Transactional(readOnly = true) // 1. Protege todo el método de caídas de sesión
    public ResponseEntity<DashboardResponseDTO> getDashboard() {

        // 1. Calcular el valor total del inventario de forma segura
        BigDecimal inventoryValue = productRepository.findAll()
                .stream()
                .map(product -> {
                    BigDecimal price = product.getPurchasePrice() != null ? product.getPurchasePrice() : BigDecimal.ZERO;
                    Integer stock = product.getCurrentStock() != null ? product.getCurrentStock() : 0;
                    return price.multiply(BigDecimal.valueOf(stock));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Extraer los productos con bajo stock filtrando en memoria de forma segura
        List<?> lowStockProducts = productRepository.findAll()
                .stream()
                .filter(product -> product.getCurrentStock() != null && product.getCurrentStock() <= 5)
                .map(product -> new com.cosodi.pos.dto.LowStockProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getCurrentStock()
                ))
                .toList();

        // 3. Extraer las últimas 5 ventas utilizando el ordenamiento seguro de Spring Data
        List<RecentSaleResponseDTO> recentSales = saleRepository.findAll(
                        PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "saleDate"))
                ).getContent()
                .stream()
                .map(sale -> {
                    String customerName = "Unknown Customer";
                    if (sale.getCustomer() != null) {
                        if (sale.getCustomer().getSocialReason() != null && !sale.getCustomer().getSocialReason().isEmpty()) {
                            customerName = sale.getCustomer().getSocialReason();
                        } else {
                            customerName = sale.getCustomer().getFirstname() + " " + sale.getCustomer().getLastname();
                        }
                    }

                    return new RecentSaleResponseDTO(
                            sale.getId(),
                            sale.getVoucherSerie(),
                            sale.getVoucherNumber(),
                            customerName,
                            sale.getTotal(),
                            sale.getSaleDate()
                    );
                })
                .toList();

        // 4. Armar la respuesta final libre de proxies cíclicos de Hibernate
        DashboardResponseDTO response = new DashboardResponseDTO(
                productRepository.count(),
                customerRepository.count(),
                saleRepository.count(),
                inventoryValue,
                List.of(),
                recentSales,
                lowStockProducts
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales-by-day")
    public ResponseEntity<List<SalesByDayDTO>> getSalesByDay() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return ResponseEntity.ok(saleRepository.findSalesByDay(sevenDaysAgo));
    }

    @GetMapping("/sales-by-month")
    public ResponseEntity<List<SalesByMonthDTO>> getSalesByMonth() {
        int year = LocalDateTime.now().getYear();
        return ResponseEntity.ok(saleRepository.findSalesByMonth(year));
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> getTopProducts() {
        return ResponseEntity.ok(
                saleRepository.findTopProducts(PageRequest.of(0, 5))
        );
    }
}
