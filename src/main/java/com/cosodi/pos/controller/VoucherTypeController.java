package com.cosodi.pos.controller;

import com.cosodi.pos.entity.VoucherType;
import com.cosodi.pos.service.IVoucherTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher-types")
@RequiredArgsConstructor
public class VoucherTypeController {

    private final IVoucherTypeService service;

    @GetMapping
    public ResponseEntity<List<VoucherType>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherType> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.findById(id)
        );
    }
}
