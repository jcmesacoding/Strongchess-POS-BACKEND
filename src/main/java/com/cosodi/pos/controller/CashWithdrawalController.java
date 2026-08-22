package com.cosodi.pos.controller;

import com.cosodi.pos.dto.CashBalanceResponseDTO;
import com.cosodi.pos.dto.CashWithdrawalRequestDTO;
import com.cosodi.pos.dto.CashWithdrawalResponseDTO;
import com.cosodi.pos.entity.CashWithdrawal;
import com.cosodi.pos.repository.ICashWithdrawalRepository;
import com.cosodi.pos.repository.ISaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cash-withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final ICashWithdrawalRepository cashWithdrawalRepository;
    private final ISaleRepository saleRepository;

    @GetMapping("/balance")
    public ResponseEntity<CashBalanceResponseDTO> getBalance() {
        return ResponseEntity.ok(buildBalance());
    }

    @GetMapping
    public ResponseEntity<List<CashWithdrawalResponseDTO>> getAll() {

        List<CashWithdrawalResponseDTO> result =
                cashWithdrawalRepository.findAllByOrderByWithdrawalDateDesc()
                        .stream()
                        .map(w -> new CashWithdrawalResponseDTO(
                                w.getId(),
                                w.getAmount(),
                                w.getReason(),
                                w.getPerformedBy(),
                                w.getWithdrawalDate()
                        ))
                        .toList();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CashWithdrawalResponseDTO> create(
            @RequestBody CashWithdrawalRequestDTO request,
            Authentication authentication
    ) {

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto del retiro debe ser mayor a cero");
        }

        BigDecimal currentBalance = buildBalance().cashInRegister();

        if (request.getAmount().compareTo(currentBalance) > 0) {
            throw new RuntimeException("El monto del retiro supera el efectivo disponible en caja");
        }

        CashWithdrawal withdrawal = new CashWithdrawal();
        withdrawal.setAmount(request.getAmount());
        withdrawal.setReason(request.getReason());
        withdrawal.setPerformedBy(authentication.getName());

        CashWithdrawal saved = cashWithdrawalRepository.save(withdrawal);

        CashWithdrawalResponseDTO response = new CashWithdrawalResponseDTO(
                saved.getId(),
                saved.getAmount(),
                saved.getReason(),
                saved.getPerformedBy(),
                saved.getWithdrawalDate()
        );

        return ResponseEntity.ok(response);
    }

    private CashBalanceResponseDTO buildBalance() {
        BigDecimal totalSales = saleRepository.sumAllSales();
        BigDecimal totalWithdrawals = cashWithdrawalRepository.sumAllWithdrawals();
        BigDecimal cashInRegister = totalSales.subtract(totalWithdrawals);

        return new CashBalanceResponseDTO(totalSales, totalWithdrawals, cashInRegister);
    }
}
