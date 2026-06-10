package com.cosodi.pos.dto;

import java.util.Date;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerRequestDTO {
    @EqualsAndHashCode.Include
    private Long id;

    @Nullable
    @Size(min = 3, max = 50, message = "El primer nombre debe tener entre 3 y 50 caracteres")
    private String firstname;

    @Nullable
    @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
    private String lastname;

    @NotBlank
    @Size(min = 3, max = 15, message = "El número de teléfono debe tener entre 3 y 15 caracteres")
    private String phoneNumber;

    @NotBlank
    @Size(min = 3, max = 255, message = "La dirección debe tener entre 3 y 255 caracteres")
    private String address;

    @NotBlank
    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    private Date registrationDate;
}