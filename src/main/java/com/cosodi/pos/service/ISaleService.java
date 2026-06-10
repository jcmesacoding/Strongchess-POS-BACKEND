package com.cosodi.pos.service;

import com.cosodi.pos.dto.SaleRequestDTO;
import com.cosodi.pos.entity.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleService extends ICRUDService<Sale, Long> {

     List<Sale> findByDateRange(
             LocalDateTime start,
             LocalDateTime end
     );

     Sale registerSale(SaleRequestDTO request);
}
