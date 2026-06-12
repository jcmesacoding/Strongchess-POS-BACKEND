package com.cosodi.pos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class SaleRequestDTO {

    private Long customerId;

    private Long employeeId;

    private Long voucherTypeId;

    Boolean creditSale;

    BigDecimal initialPayment;

    LocalDate dueDate;

    private List<SaleDetailRequestDTO> details;
}