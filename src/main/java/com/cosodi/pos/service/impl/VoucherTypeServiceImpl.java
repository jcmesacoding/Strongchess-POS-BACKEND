package com.cosodi.pos.service.impl;

import com.cosodi.pos.entity.VoucherType;
import com.cosodi.pos.repository.IGenericRepository;
import com.cosodi.pos.repository.IVoucherTypeRepository;
import com.cosodi.pos.service.IVoucherTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherTypeServiceImpl
        extends CRUDImpl<VoucherType, Long>
        implements IVoucherTypeService {

    private final IVoucherTypeRepository repository;

    @Override
    protected IGenericRepository<VoucherType, Long> getRepository() {
        return repository;
    }
}