package com.cosodi.pos.controller;

import com.cosodi.pos.dto.InventoryMovementDTO;
import com.cosodi.pos.dto.InventoryMovementRequestDTO;
import com.cosodi.pos.entity.InventoryMovement;
import com.cosodi.pos.service.IInventoryMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final IInventoryMovementService inventoryMovementService;

    @PostMapping("/entry")
    public ResponseEntity<InventoryMovementDTO> registerEntry(
            @Valid @RequestBody InventoryMovementRequestDTO request) {

        InventoryMovement movement =
                inventoryMovementService.registerEntry(
                        request.getProductId(),
                        request.getQuantity(),
                        request.getNotes()
                );

        return ResponseEntity.ok(
                convertToDTO(movement)
        );
    }

    @PostMapping("/output")
    public ResponseEntity<InventoryMovementDTO> output(
            @Valid @RequestBody InventoryMovementRequestDTO request) {

        InventoryMovement movement =
                inventoryMovementService.registerOutput(
                        request.getProductId(),
                        request.getQuantity(),
                        request.getNotes()
                );

        return ResponseEntity.ok(
                convertToDTO(movement)
        );
    }

    @PostMapping("/adjustment")
    public ResponseEntity<InventoryMovementDTO> adjustment(
            @Valid @RequestBody InventoryMovementRequestDTO request) {

        InventoryMovement movement =
                inventoryMovementService.registerAdjustment(
                        request.getProductId(),
                        request.getQuantity(),
                        request.getNotes()
                );

        return ResponseEntity.ok(
                convertToDTO(movement)
        );
    }

    @GetMapping
    public ResponseEntity<List<InventoryMovementDTO>> findAll() {

        return ResponseEntity.ok(
                inventoryMovementService.findAll()
                        .stream()
                        .map(this::convertToDTO)
                        .toList()
        );
    }

    private InventoryMovementDTO convertToDTO(
            InventoryMovement movement) {

        InventoryMovementDTO dto =
                new InventoryMovementDTO();

        dto.setId(movement.getId());

        dto.setProductId(
                movement.getProduct().getId()
        );

        dto.setProductName(
                movement.getProduct().getName()
        );

        dto.setQuantity(
                movement.getQuantity()
        );

        dto.setType(
                movement.getType()
        );

        dto.setNotes(
                movement.getNotes()
        );

        dto.setMovementDate(
                movement.getMovementDate()
        );

        return dto;
    }
}