package com.cosodi.pos.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaleRequestDTO {

    private Long customerId;

    private Long employeeId;

    private Long voucherTypeId;

    private List<SaleDetailRequestDTO> details;
}