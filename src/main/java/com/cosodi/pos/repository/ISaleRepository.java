package com.cosodi.pos.repository;

import com.cosodi.pos.dto.SalesByDayDTO;
import com.cosodi.pos.dto.SalesByMonthDTO;
import com.cosodi.pos.dto.TopProductDTO;
import com.cosodi.pos.entity.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ISaleRepository extends IGenericRepository<Sale, Long> {

    @Override
    @EntityGraph(attributePaths = {"details", "details.product", "customer", "employee"})
    List<Sale> findAll();

    @EntityGraph(attributePaths = {"details", "details.product", "customer", "employee"})
    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

    @EntityGraph(attributePaths = {"details", "details.product", "customer", "employee"})
    @Query("SELECT s FROM Sale s WHERE s.id = :id")
    Optional<Sale> findOneWithDetails(@Param("id") Long id);

    // 1. Calcula: Día (String), Total de ingresos (Double) y Cantidad de ventas realizadas (Long)
    @Query("SELECT new com.cosodi.pos.dto.SalesByDayDTO(FUNCTION('FORMAT', s.saleDate, 'yyyy-MM-dd'), CAST(SUM(s.total) AS double), COUNT(s.id)) " +
            "FROM Sale s WHERE s.saleDate >= :startDate GROUP BY FUNCTION('FORMAT', s.saleDate, 'yyyy-MM-dd')")
    List<SalesByDayDTO> findSalesByDay(@Param("startDate") LocalDateTime startDate);

    // 2. Calcula: Mes (String), Total de ingresos (Double) y Cantidad de ventas del mes (Long)
    @Query("SELECT new com.cosodi.pos.dto.SalesByMonthDTO(FUNCTION('FORMAT', s.saleDate, 'MMMM'), CAST(SUM(s.total) AS double), COUNT(s.id)) " +
            "FROM Sale s WHERE FUNCTION('YEAR', s.saleDate) = :year GROUP BY FUNCTION('FORMAT', s.saleDate, 'MMMM')")
    List<SalesByMonthDTO> findSalesByMonth(@Param("year") int year);

    // 3. Calcula: Nombre del producto (String), Unidades vendidas (Long) e Ingresos totales generados (Double)
    @Query("SELECT new com.cosodi.pos.dto.TopProductDTO(p.name, SUM(d.units), CAST(SUM(d.units * d.salePrice - d.discount) AS double)) " +
            "FROM SaleDetail d JOIN d.product p GROUP BY p.name ORDER BY SUM(d.units) DESC")
    List<TopProductDTO> findTopProducts(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"customer", "employee"})
    org.springframework.data.domain.Page<Sale> findAll(org.springframework.data.domain.Pageable pageable);
}
