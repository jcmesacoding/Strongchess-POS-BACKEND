package com.cosodi.pos.dto;

import java.math.BigDecimal;

public record CashBalanceResponseDTO(
        BigDecimal totalSales,
        BigDecimal totalWithdrawals,
        BigDecimal cashInRegister
) {}
