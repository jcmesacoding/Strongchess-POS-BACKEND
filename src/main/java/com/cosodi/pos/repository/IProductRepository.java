package com.cosodi.pos.repository;

import com.cosodi.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository
        extends IGenericRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.currentStock <= 5
            ORDER BY p.currentStock ASC
            """)
    List<Product> findByCurrentStockLessThanEqual(Integer stock);
}