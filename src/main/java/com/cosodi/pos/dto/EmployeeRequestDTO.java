package com.cosodi.pos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeRequestDTO {

    @NotNull(message = "El tipo de persona es obligatorio")
    private Long personTypeId;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Long documentTypeId;

    @NotBlank(message = "El número de documento es obligatorio")
    private String documentNumber;

    @NotBlank(message = "El nombre es obligatorio")
    private String firstname;

    private String middlename; // nullable

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String lastname;

    @NotBlank(message = "El apellido materno es obligatorio")
    private String surname;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    private String phoneNumber; // nullable

    @NotNull(message = "La fecha de contratación es obligatoria")
    private LocalDateTime hiringDate;

    @NotNull(message = "El género es obligatorio")
    private Long genderId;

    private LocalDate birthdate; // nullable

    @NotNull(message = "El cargo es obligatorio")
    private Long jobPositionId;

    @NotNull(message = "El departamento es obligatorio")
    private Long departmentId;

    @NotNull(message = "La provincia es obligatoria")
    private Long provinceId;

    @NotNull(message = "El distrito es obligatorio")
    private Long districtId;
}