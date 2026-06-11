package com.cosodi.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopProductDTO {
    private String name;
    private Long totalSold;
    private Double totalRevenue;
}