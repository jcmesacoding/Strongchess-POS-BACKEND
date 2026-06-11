package com.cosodi.pos.repository;

import com.cosodi.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchByName(@Param("query") String query);
}