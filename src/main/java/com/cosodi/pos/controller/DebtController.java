package com.cosodi.pos.controller;

import com.cosodi.pos.dto.DebtPaymentRequestDTO;
import com.cosodi.pos.dto.DebtResponseDTO;
import com.cosodi.pos.entity.Debt;
import com.cosodi.pos.repository.IDebtRepository;
import com.cosodi.pos.util.DebtStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @PostMapping(
            "/{id}/pay"
    )
    public ResponseEntity<Void>
    payDebt(

            @PathVariable
            Long id,

            @RequestBody
            DebtPaymentRequestDTO request

    ) {

        Debt debt =

                debtRepository
                        .findById(
                                id
                        )

                        .orElseThrow(

                                () ->

                                        new RuntimeException(
                                                "Debt not found"
                                        )

                        );

        BigDecimal newPaid =

                debt.getPaidAmount()
                        .add(
                                request.amount()
                        );

        BigDecimal pending =

                debt.getTotalAmount()
                        .subtract(
                                newPaid
                        );

        if (
                pending.compareTo(
                        BigDecimal.ZERO
                ) < 0
        ) {

            throw new RuntimeException(
                    "Payment exceeds remaining debt"
            );

        }

        debt.setPaidAmount(
                newPaid
        );

        debt.setPendingAmount(
                pending
        );

        if (
                pending.compareTo(
                        BigDecimal.ZERO
                ) == 0
        ) {

            debt.setStatus(
                    DebtStatus.PAID
            );

        }

        else {

            debt.setStatus(
                    DebtStatus.PARTIAL
            );

        }

        debtRepository.save(
                debt
        );

        return ResponseEntity
                .ok()
                .build();

    }
}
