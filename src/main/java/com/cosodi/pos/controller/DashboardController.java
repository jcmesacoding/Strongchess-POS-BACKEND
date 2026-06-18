package com.cosodi.pos.controller;

import com.cosodi.pos.dto.DashboardResponseDTO;
import com.cosodi.pos.dto.SalesByDayDTO;
import com.cosodi.pos.dto.SalesByMonthDTO;
import com.cosodi.pos.dto.TopProductDTO;
import com.cosodi.pos.entity.Sale;
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
    public ResponseEntity<DashboardResponseDTO> getDashboard() {

        // 1. Calcular el valor total del inventario
        BigDecimal inventoryValue = productRepository.findAll()
                .stream()
                .map(product -> product.getPurchasePrice()
                        .multiply(BigDecimal.valueOf(product.getCurrentStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Extraer los productos con bajo stock (Menos de 5 unidades, por ejemplo)
        List<?> lowStockProducts = productRepository.findAll()
                .stream()
                .filter(product -> product.getCurrentStock() <= 5)
                .toList();

        // 3. Extraer las últimas 5 ventas recientes ordenadas por fecha descendente
        List<Sale> recentSales = saleRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "saleDate"))
        ).getContent();

        // 4. Armar la respuesta completa utilizando la clase DashboardResponseDTO que espera tu controlador
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
