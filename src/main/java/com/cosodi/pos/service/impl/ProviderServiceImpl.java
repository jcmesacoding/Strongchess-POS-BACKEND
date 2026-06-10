package com.cosodi.pos.service.impl;

import com.cosodi.pos.entity.Provider;
import com.cosodi.pos.repository.IGenericRepository;
import com.cosodi.pos.repository.IProviderRepository;
import com.cosodi.pos.service.IProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl
        extends CRUDImpl<Provider, Long>
        implements IProviderService {

    private final IProviderRepository providerRepository;

    @Override
    protected IGenericRepository<Provider, Long> getRepository() {
        return providerRepository;
    }
}