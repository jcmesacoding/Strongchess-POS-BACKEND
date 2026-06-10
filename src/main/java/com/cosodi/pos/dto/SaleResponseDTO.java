package com.cosodi.pos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SaleResponseDTO {

    private Long id;

    private String customerName;

    private String employeeName;

    private String voucherNumber;

    private String voucherSerie;

    private BigDecimal total;

    private LocalDateTime saleDate;
}
