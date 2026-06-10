package com.cosodi.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecentSaleResponse(

        Long saleId,

        String customerName,

        String voucherNumber,

        BigDecimal total,

        LocalDateTime saleDate

) {}