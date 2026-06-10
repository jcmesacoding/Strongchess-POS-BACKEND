package com.cosodi.pos.service.impl;

import com.cosodi.pos.entity.InventoryMovement;
import com.cosodi.pos.entity.Product;
import com.cosodi.pos.exception.ModelNotFoundException;
import com.cosodi.pos.repository.IInventoryMovementRepository;
import com.cosodi.pos.repository.IProductRepository;
import com.cosodi.pos.service.IInventoryMovementService;
import com.cosodi.pos.util.MovementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryMovementServiceImpl
        implements IInventoryMovementService {

    private final IInventoryMovementRepository movementRepository;
    private final IProductRepository productRepository;

    @Override
    public InventoryMovement registerEntry(
            Long productId,
            Integer quantity,
            String notes) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ModelNotFoundException(
                                "Product not found: " + productId));

        product.setCurrentStock(
                product.getCurrentStock() + quantity
        );

        productRepository.save(product);

        InventoryMovement movement =
                new InventoryMovement();

        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(MovementType.ENTRY);
        movement.setNotes(notes);
        movement.setMovementDate(LocalDateTime.now());

        return movementRepository.save(movement);
    }

    @Override
    public InventoryMovement registerOutput(
            Long productId,
            Integer quantity,
            String notes) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ModelNotFoundException(
                                "Product not found: " + productId));

        if (product.getCurrentStock() < quantity) {
            throw new IllegalArgumentException(
                    "Insufficient stock"
            );
        }

        product.setCurrentStock(
                product.getCurrentStock() - quantity
        );

        productRepository.save(product);

        InventoryMovement movement =
                new InventoryMovement();

        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(MovementType.OUTPUT);
        movement.setNotes(notes);
        movement.setMovementDate(LocalDateTime.now());

        return movementRepository.save(movement);
    }

    @Override
    public InventoryMovement registerAdjustment(
            Long productId,
            Integer quantity,
            String notes) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ModelNotFoundException(
                                "Product not found: " + productId));

        product.setCurrentStock(quantity);

        productRepository.save(product);

        InventoryMovement movement =
                new InventoryMovement();

        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(MovementType.ADJUSTMENT);
        movement.setNotes(notes);
        movement.setMovementDate(LocalDateTime.now());

        return movementRepository.save(movement);
    }

    @Override
    public List<InventoryMovement> findAll() {
        return movementRepository.findAll();
    }
}