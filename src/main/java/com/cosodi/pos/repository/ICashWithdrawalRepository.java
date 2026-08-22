package com.cosodi.pos.repository;

import com.cosodi.pos.entity.CashWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ICashWithdrawalRepository extends JpaRepository<CashWithdrawal, Long> {

    List<CashWithdrawal> findAllByOrderByWithdrawalDateDesc();

    @Query("SELECT COALESCE(SUM(w.amount), 0) FROM CashWithdrawal w")
    BigDecimal sumAllWithdrawals();
}
