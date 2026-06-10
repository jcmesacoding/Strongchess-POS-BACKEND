package com.cosodi.pos.mapper;

import com.cosodi.pos.dto.EmployeeRequestDTO;
import com.cosodi.pos.dto.EmployeeResponseDTO;
import com.cosodi.pos.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponseDTO toDTO(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(e.getId());
        dto.setDocumentNumber(e.getDocumentNumber());
        dto.setFirstname(e.getFirstname());
        dto.setMiddlename(e.getMiddlename());
        dto.setLastname(e.getLastname());
        dto.setSurname(e.getSurname());
        dto.setEmail(e.getEmail());
        dto.setPhoneNumber(e.getPhoneNumber());
        dto.setHiringDate(e.getHiringDate());
        dto.setBirthdate(e.getBirthdate());
        dto.setRegistrationDate(e.getRegistrationDate());

        dto.setPersonType(e.getPersonType().getDescription());
        dto.setDocumentType(e.getDocumentType().getDescription());
        dto.setGender(e.getGender().getName());
        dto.setJobPosition(e.getJobPosition().getName());
        dto.setDepartment(e.getDepartment().getDescription());
        dto.setProvince(e.getProvince().getDescription());
        dto.setDistrict(e.getDistrict().getDescription());

        return dto;
    }

    public Employee toEntity(EmployeeRequestDTO dto) {
        Employee e = new Employee();

        e.setDocumentNumber(dto.getDocumentNumber());
        e.setFirstname(dto.getFirstname());
        e.setMiddlename(dto.getMiddlename());
        e.setLastname(dto.getLastname());
        e.setSurname(dto.getSurname());
        e.setEmail(dto.getEmail());
        e.setPhoneNumber(dto.getPhoneNumber());
        e.setHiringDate(dto.getHiringDate());
        e.setBirthdate(dto.getBirthdate());

        PersonType personType = new PersonType();
        personType.setId(Math.toIntExact(dto.getPersonTypeId()));
        e.setPersonType(personType);

        DocumentType documentType = new DocumentType();
        documentType.setId(Math.toIntExact(dto.getDocumentTypeId()));
        e.setDocumentType(documentType);

        Gender gender = new Gender();
        gender.setId(Math.toIntExact(dto.getGenderId()));
        e.setGender(gender);

        JobPosition jobPosition = new JobPosition();
        jobPosition.setId(dto.getJobPositionId());
        e.setJobPosition(jobPosition);

        Department department = new Department();
        department.setId(dto.getDepartmentId());
        e.setDepartment(department);

        Province province = new Province();
        province.setId(dto.getProvinceId());
        e.setProvince(province);

        District district = new District();
        district.setId(dto.getDistrictId());
        e.setDistrict(district);

        return e;
    }
}