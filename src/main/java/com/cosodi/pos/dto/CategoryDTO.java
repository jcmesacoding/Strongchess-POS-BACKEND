package com.cosodi.pos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "El nombre de la categoría es requerido")
    @Size(min = 3, max = 100,
            message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @Size(max = 255,
            message = "La descripción no puede exceder 255 caracteres")
    private String description;
}