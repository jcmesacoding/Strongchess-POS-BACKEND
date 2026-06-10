package com.cosodi.pos.controller;

import com.cosodi.pos.dto.EmployeeRequestDTO;
import com.cosodi.pos.dto.EmployeeResponseDTO;
import com.cosodi.pos.entity.Employee;
import com.cosodi.pos.mapper.EmployeeMapper;
import com.cosodi.pos.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService service;
    private final EmployeeMapper mapper;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        List<EmployeeResponseDTO> employees = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> save(
            @RequestBody @Valid EmployeeRequestDTO requestDTO) {

        Employee employee =
                mapper.toEntity(requestDTO);

        Employee saved =
                service.save(employee);

        return ResponseEntity.ok(
                mapper.toDTO(saved)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeRequestDTO requestDTO) {

        Employee employee = mapper.toEntity(requestDTO);
        employee.setId(id);
        Employee updated = service.update(employee, id);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}