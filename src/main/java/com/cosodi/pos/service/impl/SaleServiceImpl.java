package com.cosodi.pos.service.impl;

import com.cosodi.pos.dto.SaleDetailRequestDTO;
import com.cosodi.pos.dto.SaleRequestDTO;
import com.cosodi.pos.entity.*;
import com.cosodi.pos.exception.ModelNotFoundException;
import com.cosodi.pos.repository.*;
import com.cosodi.pos.service.IInventoryMovementService;
import com.cosodi.pos.service.ISaleService;
import com.cosodi.pos.util.DebtStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl extends CRUDImpl<Sale, Long>
        implements ISaleService {

    private final ISaleRepository iSaleRepository;

    private final ICustomerRepository customerRepository;
    private final IEmployeeRepository employeeRepository;
    private final IVoucherTypeRepository voucherTypeRepository;
    private final IProductRepository productRepository;
    private final IDebtRepository debtRepository;

    private final IInventoryMovementService inventoryMovementService;

    @Override
    protected IGenericRepository<Sale, Long> getRepository() {
        return this.iSaleRepository;
    }

    @Override
    public List<Sale> findByDateRange(
            LocalDateTime start,
            LocalDateTime end) {

        return iSaleRepository.findBySaleDateBetween(
                start,
                end
        );
    }

    @Override
    @Transactional
    public Sale registerSale(SaleRequestDTO request) {

        Customer customer =
                customerRepository.findById(request.getCustomerId())
                        .orElseThrow(() ->
                                new ModelNotFoundException(
                                        "Customer not found"));

        Employee employee =
                employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() ->
                                new ModelNotFoundException(
                                        "Employee not found"));

        VoucherType voucherType =
                voucherTypeRepository.findById(request.getVoucherTypeId())
                        .orElseThrow(() ->
                                new ModelNotFoundException(
                                        "Voucher type not found"));

        Sale sale = new Sale();

        sale.setCustomer(customer);
        sale.setEmployee(employee);
        sale.setVoucherType(voucherType);

        sale.setVoucherNumber(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
        );

        sale.setVoucherSerie("A01");

        BigDecimal total = BigDecimal.ZERO;

        List<SaleDetail> details = new ArrayList<>();

        for (SaleDetailRequestDTO item : request.getDetails()) {

            Product product =
                    productRepository.findById(item.getProductId())
                            .orElseThrow(() ->
                                    new ModelNotFoundException(
                                            "Product not found"));

            if (product.getCurrentStock() < item.getUnits()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            BigDecimal subtotal =
                    item.getSalePrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getUnits()
                                    )
                            )
                            .subtract(item.getDiscount());

            total = total.add(subtotal);

            SaleDetail detail = new SaleDetail();

            detail.setSale(sale);
            detail.setProduct(product);
            detail.setUnits(item.getUnits());
            detail.setSalePrice(item.getSalePrice());
            detail.setDiscount(item.getDiscount());

            details.add(detail);

            inventoryMovementService.registerOutput(
                    product.getId(),
                    item.getUnits(),
                    "Sale #" + sale.getVoucherNumber()
            );
        }

        sale.setDetails(details);

        sale.setTaxAmount(BigDecimal.ZERO);

        sale.setTotal(total);

// Guardar primero la venta
        sale = iSaleRepository.save(sale);

// Crear deuda si aplica
        if (
                Boolean.TRUE.equals(
                        request.getCreditSale()
                )
        ) {

            BigDecimal paid =
                    request.getInitialPayment() != null
                            ? request.getInitialPayment()
                            : BigDecimal.ZERO;

            BigDecimal pending =
                    sale.getTotal()
                            .subtract(
                                    paid
                            );

            Debt debt =
                    new Debt();

            debt.setCustomer(
                    customer
            );

            debt.setSale(
                    sale
            );

            debt.setTotalAmount(
                    sale.getTotal()
            );

            debt.setPaidAmount(
                    paid
            );

            debt.setPendingAmount(
                    pending
            );

            debt.setDueDate(
                    request.getDueDate()
            );

            debt.setStatus(

                    pending.compareTo(
                            BigDecimal.ZERO
                    ) == 0

                            ? DebtStatus.PAID

                            : paid.compareTo(
                            BigDecimal.ZERO
                    ) > 0

                            ? DebtStatus.PARTIAL

                            : DebtStatus.PENDING

            );

            debtRepository.save(
                    debt
            );
        }
// retornar al final
        return sale;
    }

    @Override
    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return iSaleRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Sale not found"));
    }
}