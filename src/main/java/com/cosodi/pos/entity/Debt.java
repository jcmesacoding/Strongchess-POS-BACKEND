package com.cosodi.pos.entity;

import com.cosodi.pos.util.DebtStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "debts")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;

    private BigDecimal pendingAmount;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private DebtStatus status;
}