package com.cosodi.pos.dto;

import com.cosodi.pos.util.MovementType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryMovementDTO {

    private Long id;

    private Long productId;

    private String productName;

    private Integer quantity;

    private MovementType type;

    private String notes;

    private LocalDateTime movementDate;
}