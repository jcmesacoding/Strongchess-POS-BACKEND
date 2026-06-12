package com.cosodi.pos.repository;

import com.cosodi.pos.dto.SalesByDayDTO;
import com.cosodi.pos.dto.SalesByMonthDTO;
import com.cosodi.pos.dto.TopCustomerDTO;
import com.cosodi.pos.dto.TopProductDTO;
import com.cosodi.pos.entity.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleRepository extends IGenericRepository<Sale, Long> {

    List<Sale> findBySaleDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<Sale> findTop5ByOrderBySaleDateDesc();

    @Query("SELECT new com.cosodi.pos.dto.SalesByDayDTO(" +
            "CAST(s.saleDate AS string), SUM(s.total), COUNT(s)) " +
            "FROM Sale s " +
            "WHERE s.saleDate >= :startDate " +
            "GROUP BY CAST(s.saleDate AS string) " +
            "ORDER BY s.saleDate ASC")
    List<SalesByDayDTO> findSalesByDay(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT new com.cosodi.pos.dto.SalesByMonthDTO(" +
            "FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m'), SUM(s.total), COUNT(s)) " +
            "FROM Sale s " +
            "WHERE YEAR(s.saleDate) = :year " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m') ASC")
    List<SalesByMonthDTO> findSalesByMonth(@Param("year") int year);

    @Query("SELECT new com.cosodi.pos.dto.TopProductDTO(" +
            "p.name, SUM(sd.units), SUM(sd.units * sd.salePrice)) " +
            "FROM SaleDetail sd JOIN sd.product p " +
            "GROUP BY p.name " +
            "ORDER BY SUM(sd.units) DESC")
    List<TopProductDTO> findTopProducts(Pageable pageable);

    @Query("""
            SELECT new com.cosodi.pos.dto.TopCustomerDTO(
                COALESCE(c.socialReason,
                         CONCAT(c.firstname, ' ', c.lastname)),
                COUNT(s.id),
                SUM(s.total)
            )
            FROM Sale s
            JOIN s.customer c
            GROUP BY c.id
            ORDER BY SUM(s.total) DESC
            """)
    List<TopCustomerDTO> findTopCustomers(Pageable pageable);
}
