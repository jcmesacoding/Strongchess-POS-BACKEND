package com.cosodi.pos.service.impl;

import com.cosodi.pos.entity.Employee;
import com.cosodi.pos.repository.IEmployeeRepository;
import com.cosodi.pos.repository.IGenericRepository;
import com.cosodi.pos.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl
        extends CRUDImpl<Employee, Long>
        implements IEmployeeService {

    private final IEmployeeRepository repository;

    @Override
    protected IGenericRepository<Employee, Long> getRepository() {
        return repository;
    }
}