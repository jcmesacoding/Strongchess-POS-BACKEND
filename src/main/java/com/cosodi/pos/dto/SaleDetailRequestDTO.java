package com.cosodi.pos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleDetailRequestDTO {

    private Long productId;

    @NotNull
    @Min(1)
    private Integer units;

    @NotNull
    @Min(0)
    private BigDecimal salePrice;

    @NotNull
    @Min(0)
    private BigDecimal discount;
}