package com.cosodi.pos.repository;

import com.cosodi.pos.entity.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleRepository extends IGenericRepository<Sale, Long> {

    List<Sale> findBySaleDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<Sale> findTop5ByOrderBySaleDateDesc();

}
