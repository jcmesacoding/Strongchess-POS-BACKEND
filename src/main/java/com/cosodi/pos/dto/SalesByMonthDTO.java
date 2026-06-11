package com.cosodi.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesByMonthDTO {
    private String month;
    private Double total;
    private Long count;
}