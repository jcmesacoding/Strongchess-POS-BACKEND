package com.cosodi.pos.service;

import com.cosodi.pos.entity.Product;

import java.util.List;

public interface IProductService extends ICRUDService<Product, Long> {
    List<Product> findLowStockProducts();
}