package com.cosodi.pos.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardDTO {

    private Long totalProducts;

    private Long totalCustomers;

    private Long totalSales;

    private BigDecimal inventoryValue;
}
