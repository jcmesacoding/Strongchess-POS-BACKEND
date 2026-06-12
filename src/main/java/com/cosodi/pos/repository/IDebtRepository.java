package com.cosodi.pos.repository;

import com.cosodi.pos.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDebtRepository
        extends JpaRepository<Debt, Long> {
}
