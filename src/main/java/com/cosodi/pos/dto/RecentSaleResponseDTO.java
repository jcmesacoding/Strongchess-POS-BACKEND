package com.cosodi.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecentSaleResponseDTO(
        Long id,
        String voucherSerie,
        String voucherNumber,
        String customer,
        BigDecimal total,
        LocalDateTime saleDate
) {}
