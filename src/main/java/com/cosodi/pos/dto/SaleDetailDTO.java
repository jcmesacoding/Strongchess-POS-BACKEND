package com.cosodi.pos.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleDetailDTO {

    private Long id;

    private Long productId;

    private String productName;

    private Integer units;

    private BigDecimal salePrice;

    private BigDecimal discount;

    @Column(nullable = false)
    private BigDecimal total;
}