package com.cosodi.pos.dto;

import java.math.BigDecimal;

public record TopCustomerDTO(

        String customerName,

        Long purchases,

        BigDecimal totalSpent

) {}