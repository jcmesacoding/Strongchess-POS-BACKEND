package com.cosodi.pos.controller;

import com.cosodi.pos.dto.DashboardResponse;
import com.cosodi.pos.repository.ICustomerRepository;
import com.cosodi.pos.repository.IProductRepository;
import com.cosodi.pos.repository.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final IProductRepository productRepository;
    private final ICustomerRepository customerRepository;
    private final ISaleRepository saleRepository;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {

        BigDecimal inventoryValue =
                productRepository.findAll()
                        .stream()
                        .map(product ->
                                product.getPurchasePrice()
                                        .multiply(
                                                BigDecimal.valueOf(
                                                        product.getCurrentStock()
                                                )
                                        )
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        DashboardResponse response =
                new DashboardResponse(

                        productRepository.count(),

                        customerRepository.count(),

                        saleRepository.count(),

                        inventoryValue,

                        List.of(), // Top Products

                        List.of(), // Recent Sales

                        List.of()  // Low Stock Products
                );

        return ResponseEntity.ok(response);
    }
}