package com.cosodi.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashWithdrawalResponseDTO(
        Long id,
        BigDecimal amount,
        String reason,
        String performedBy,
        LocalDateTime withdrawalDate
) {}
