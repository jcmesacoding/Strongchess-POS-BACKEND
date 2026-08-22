package com.cosodi.pos.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashWithdrawalRequestDTO {

    private BigDecimal amount;

    private String reason;
}
