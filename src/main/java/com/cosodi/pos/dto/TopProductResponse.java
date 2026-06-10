package com.cosodi.pos.dto;

public record TopProductResponse(

        Long productId,

        String productName,

        Integer unitsSold

) {}