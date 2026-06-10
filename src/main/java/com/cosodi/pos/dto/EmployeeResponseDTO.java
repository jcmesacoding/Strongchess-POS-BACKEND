package com.cosodi.pos.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeResponseDTO {

    private Long id;
    private String personType;       // PersonType.description
    private String documentType;     // DocumentType.description
    private String documentNumber;
    private String firstname;
    private String middlename;
    private String lastname;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDateTime hiringDate;
    private String gender;           // Gender.name
    private LocalDate birthdate;
    private String jobPosition;      // JobPosition.name
    private LocalDateTime registrationDate;
    private String department;       // Department.description
    private String province;         // Province.description
    private String district;         // District.description
}