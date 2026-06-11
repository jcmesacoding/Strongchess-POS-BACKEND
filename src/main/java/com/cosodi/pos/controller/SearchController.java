package com.cosodi.pos.controller;

import com.cosodi.pos.repository.ICustomerRepository;
import com.cosodi.pos.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final IProductRepository productRepository;
    private final ICustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(@RequestParam String query) {
        Map<String, Object> results = new HashMap<>();

        results.put("products", productRepository.searchByName(query)
                .stream()
                .limit(5)
                .map(p -> Map.of(
                        "id", p.getId(),
                        "name", p.getName(),
                        "type", "product"
                ))
                .collect(Collectors.toList()));

        results.put("customers", customerRepository.searchByName(query)
                .stream()
                .limit(5)
                .map(c -> Map.of(
                        "id", c.getId(),
                        "name", (c.getFirstname() != null ? c.getFirstname() : "") + " " + (c.getLastname() != null ? c.getLastname() : ""),
                        "type", "customer"
                ))
                .collect(Collectors.toList()));

        return ResponseEntity.ok(results);
    }
}