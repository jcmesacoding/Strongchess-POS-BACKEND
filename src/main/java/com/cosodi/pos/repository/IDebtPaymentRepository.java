package com.cosodi.pos.repository;

import com.cosodi.pos.entity.DebtPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDebtPaymentRepository
        extends JpaRepository<DebtPayment, Long> {
}
