package com.cosodi.pos.controller;

import com.cosodi.pos.dto.*;
import com.cosodi.pos.entity.Sale;
import com.cosodi.pos.service.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final ISaleService iSaleService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody SaleRequestDTO saleRequestDTO) {
        // return new ResponseEntity<>(this.convertToDTO(this.iSaleService.saveTransactional(this.convertToEntity(saleDTO))), HttpStatus.OK);
        Sale createdSale =
                this.iSaleService.registerSale(saleRequestDTO);

        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdSale.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> findAll() {

        List<SaleResponseDTO> list =
                iSaleService.findAll()
                        .stream()
                        .map(this::convertToResponseDTO)
                        .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                convertToResponseDTO(
                        iSaleService.findById(id)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<SaleResponseDTO>> searchByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<SaleResponseDTO> list =
                iSaleService.findByDateRange(
                                startDate.atStartOfDay(),
                                endDate.atTime(23,59,59)
                        )
                        .stream()
                        .map(this::convertToResponseDTO)
                        .toList();

        return ResponseEntity.ok(list);
    }

    private SaleResponseDTO convertToResponseDTO(Sale sale) {

        SaleResponseDTO dto = new SaleResponseDTO();

        dto.setId(sale.getId());

        String customerName;
        if (sale.getCustomer().getSocialReason() != null) {
            customerName = sale.getCustomer().getSocialReason();
        } else {
            customerName = sale.getCustomer().getFirstname() + " " + sale.getCustomer().getLastname();
        }

        dto.setCustomerName(customerName);
        dto.setEmployeeName(sale.getEmployee().getFirstname() + " " + sale.getEmployee().getLastname());
        dto.setVoucherNumber(sale.getVoucherNumber());
        dto.setVoucherSerie(sale.getVoucherSerie());
        dto.setTotal(sale.getTotal());
        dto.setSaleDate(sale.getSaleDate());

        dto.setDetails(
                sale.getDetails()
                        .stream()
                        .map(detail -> {
                            SaleDetailResponseDTO item = new SaleDetailResponseDTO();
                            item.setProductId(detail.getProduct().getId());
                            item.setProductName(detail.getProduct().getName());
                            item.setUnits(detail.getUnits());
                            item.setSalePrice(detail.getSalePrice());
                            item.setDiscount(detail.getDiscount());
                            item.setSubtotal(
                                    detail.getSalePrice()
                                            .multiply(BigDecimal.valueOf(detail.getUnits()))
                                            .subtract(detail.getDiscount())
                            );
                            return item;
                        })
                        .toList()
        );

        return dto;
    }
}
