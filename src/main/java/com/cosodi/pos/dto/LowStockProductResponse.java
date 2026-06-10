package com.cosodi.pos.dto;

public record LowStockProductResponse(

        Long productId,

        String productName,

        Integer currentStock

) {}
