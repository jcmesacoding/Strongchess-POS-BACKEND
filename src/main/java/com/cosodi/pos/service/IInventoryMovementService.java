package com.cosodi.pos.service;

import com.cosodi.pos.entity.InventoryMovement;

import java.util.List;

public interface IInventoryMovementService {

    InventoryMovement registerEntry(
            Long productId,
            Integer quantity,
            String notes);

    InventoryMovement registerOutput(
            Long productId,
            Integer quantity,
            String notes);

    InventoryMovement registerAdjustment(
            Long productId,
            Integer quantity,
            String notes);

    List<InventoryMovement> findAll();
}
