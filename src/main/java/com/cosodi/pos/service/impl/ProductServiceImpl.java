package com.cosodi.pos.service.impl;

import com.cosodi.pos.entity.Product;
import com.cosodi.pos.repository.IGenericRepository;
import com.cosodi.pos.repository.IProductRepository;
import com.cosodi.pos.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl
        extends CRUDImpl<Product, Long>
        implements IProductService {

    private final IProductRepository iProductRepository;

    @Override
    protected IGenericRepository<Product, Long> getRepository() {
        return this.iProductRepository;
    }


    @Override
    public List<Product> findLowStockProducts() {

        return iProductRepository.findAll()
                .stream()
                .filter(Product::isLowStock)
                .toList();
    }
}