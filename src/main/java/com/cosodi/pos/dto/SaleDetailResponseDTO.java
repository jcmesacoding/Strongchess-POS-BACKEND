package com.cosodi.pos.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleDetailResponseDTO {

    private Long productId;

    private String productName;

    private Integer units;

    private BigDecimal salePrice;
zz
    private BigDecimal discount;

    private BigDecimal subtotal;
}