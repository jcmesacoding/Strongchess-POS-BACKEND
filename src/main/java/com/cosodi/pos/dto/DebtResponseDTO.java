package com.cosodi.pos.dto;

import com.cosodi.pos.util.DebtStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DebtResponseDTO(

        Long id,

        String customer,

        String voucher,

        BigDecimal total,

        BigDecimal paid,

        BigDecimal pending,

        LocalDate dueDate,

        DebtStatus status

) {
}