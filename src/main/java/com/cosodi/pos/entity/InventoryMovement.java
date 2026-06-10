package com.cosodi.pos.entity;

import com.cosodi.pos.util.MovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_movements")
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private LocalDateTime movementDate;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}