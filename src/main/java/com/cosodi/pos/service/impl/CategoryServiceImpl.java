package com.cosodi.pos.service.impl;

import com.cosodi.pos.entity.Category;
import com.cosodi.pos.repository.ICategoryRepository;
import com.cosodi.pos.repository.IGenericRepository;
import com.cosodi.pos.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        extends CRUDImpl<Category, Long>
        implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    protected IGenericRepository<Category, Long> getRepository() {
        return categoryRepository;
    }
}