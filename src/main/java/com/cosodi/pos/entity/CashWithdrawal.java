package com.cosodi.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cash_withdrawals")
public class CashWithdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 255)
    private String reason;

    @Column(name = "performed_by", nullable = false, length = 60)
    private String performedBy;

    @Column(name = "withdrawal_date", nullable = false)
    private LocalDateTime withdrawalDate;

    @PrePersist
    public void assignWithdrawalDate() {
        this.withdrawalDate = LocalDateTime.now();
    }
}
