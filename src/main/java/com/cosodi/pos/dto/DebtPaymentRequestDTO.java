package com.cosodi.pos.dto;

import java.math.BigDecimal;

public record DebtPaymentRequestDTO(

        BigDecimal amount

) {
}