package com.cosodi.pos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleDTO {

    private Long id;

    private String voucherNumber;

    private String voucherSerie;

    private BigDecimal taxAmount;

    private BigDecimal total;

    private CustomerDTO customer;

    private EmployeeRequestDTO employee;

    private VoucherTypeDTO voucherType;

    private List<SaleDetailDTO> details;

}