package com.cosodi.pos.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponseDTO(
        Long totalProducts,
        Long totalCustomers,
        Long totalSales,
        BigDecimal inventoryValue,
        List<?> topProducts,
        List<?> recentSales,
        List<?> lowStockProducts
) {}
