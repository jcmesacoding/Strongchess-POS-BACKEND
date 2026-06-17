package com.cosodi.pos.repository;

import com.cosodi.pos.entity.Sale;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleRepository extends IGenericRepository<Sale, Long> {

    @Override
    @EntityGraph(attributePaths = {"details", "details.product", "customer", "employee"})
    List<Sale> findAll();

    @EntityGraph(attributePaths = {"details", "details.product", "customer", "employee"})
    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    List<Sale> findTop5ByOrderBySaleDateDesc();
//    @Query("SELECT new com.cosodi.pos.dto.SalesByDayDTO(" +
//            "CAST(s.saleDate AS string), SUM(s.total), COUNT(s)) " +
//            "FROM Sale s " +
//            "WHERE s.saleDate >= :startDate " +
//            "GROUP BY CAST(s.saleDate AS string) " +
//            "ORDER BY s.saleDate ASC")
//    List<SalesByDayDTO> findSalesByDay(@Param("startDate") LocalDateTime startDate);
//
//    @Query("SELECT new com.cosodi.pos.dto.SalesByMonthDTO(" +
//            "FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m'), SUM(s.total), COUNT(s)) " +
//            "FROM Sale s " +
//            "WHERE YEAR(s.saleDate) = :year " +
//            "GROUP BY FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m') " +
//            "ORDER BY FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m') ASC")
//    List<SalesByMonthDTO> findSalesByMonth(@Param("year") int year);
//
//    @Query("SELECT new com.cosodi.pos.dto.TopProductDTO(" +
//            "p.name, SUM(sd.units), SUM(sd.units * sd.salePrice)) " +
//            "FROM SaleDetail sd JOIN sd.product p " +
//            "GROUP BY p.name " +
//            "ORDER BY SUM(sd.units) DESC")
//    List<TopProductDTO> findTopProducts(Pageable pageable);

}
