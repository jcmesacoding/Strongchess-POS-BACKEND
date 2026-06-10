package com.cosodi.pos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProviderDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "El nombre de la empresa es requerido")
    @Size(min = 3, max = 100)
    private String companyName;

    @NotBlank(message = "El nombre de contacto es requerido")
    @Size(min = 3, max = 100)
    private String contactName;

    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 7, max = 20)
    private String phone;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "La dirección es requerida")
    @Size(min = 5, max = 255)
    private String address;

    private LocalDateTime registrationDate;
}