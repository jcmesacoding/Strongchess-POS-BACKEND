package com.cosodi.pos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100,
            message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @Size(max = 255,
            message = "La descripción no puede exceder 255 caracteres")
    private String description;

    @NotNull(message = "El precio de venta es requerido")
    @DecimalMin(value = "0.01",
            message = "El precio de venta debe ser mayor que 0")
    private BigDecimal salePrice;

    @NotNull(message = "El precio de compra es requerido")
    @DecimalMin(value = "0.01",
            message = "El precio de compra debe ser mayor que 0")
    private BigDecimal purchasePrice;

    @NotNull(message = "El stock actual es requerido")
    @Min(value = 0,
            message = "El stock actual no puede ser negativo")
    private Integer currentStock;

    @NotNull(message = "El stock mínimo es requerido")
    @Min(value = 0,
            message = "El stock mínimo no puede ser negativo")
    private Integer minStock;

    @NotNull(message = "El stock máximo es requerido")
    @Min(value = 1,
            message = "El stock máximo debe ser mayor que cero")
    private Integer maxStock;

    @NotNull(message = "El proveedor es requerido")
    private Long providerId;

    @NotNull(message = "La categoría es requerida")
    private Long categoryId;

    private LocalDateTime registrationDate;
}