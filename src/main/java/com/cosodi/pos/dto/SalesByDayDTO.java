package com.cosodi.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesByDayDTO {
    private String day;
    private Double total;
    private Long count;
}
