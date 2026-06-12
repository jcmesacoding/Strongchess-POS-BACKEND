package com.cosodi.pos.controller;

import com.cosodi.pos.dto.DebtResponseDTO;
import com.cosodi.pos.entity.Debt;
import com.cosodi.pos.repository.IDebtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        "/api/v1/debts"
)
@RequiredArgsConstructor
public class DebtController {

    private final
    IDebtRepository
            debtRepository;

    @GetMapping
    public ResponseEntity<
            List<DebtResponseDTO>
            > getAll() {

        List<
                DebtResponseDTO
                > result =

                debtRepository
                        .findAll()
                        .stream()

                        .map(

                                debt ->

                                        new DebtResponseDTO(

                                                debt.getId(),

                                                debt.getCustomer()
                                                        .getSocialReason(),

                                                debt.getSale()
                                                        .getVoucherSerie()

                                                        +

                                                        "-"

                                                        +

                                                        debt.getSale()
                                                                .getVoucherNumber(),

                                                debt.getTotalAmount(),

                                                debt.getPaidAmount(),

                                                debt.getPendingAmount(),

                                                debt.getDueDate(),

                                                debt.getStatus()

                                        )

                        )

                        .toList();

        return ResponseEntity
                .ok(
                        result
                );

    }

}
