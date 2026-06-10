package com.cosodi.pos.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(

        Long totalProducts,

        Long totalCustomers,

        Long totalSales,

        BigDecimal inventoryValue,

        List<TopProductResponse> topProducts,

        List<RecentSaleResponse> recentSales,

        List<LowStockProductResponse> lowStockProducts

) {}
