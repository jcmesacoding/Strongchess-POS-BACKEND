package com.cosodi.pos.repository;

import com.cosodi.pos.entity.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryMovementRepository
        extends JpaRepository<InventoryMovement, Long> {
}