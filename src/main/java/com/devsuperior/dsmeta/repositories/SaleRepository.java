package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryMinDTO( " +
            "obj.seller.name, SUM(obj.amount) " +
            ")" +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name ")
    List<SaleSummaryMinDTO> searchSalesSummary(LocalDate minDate, LocalDate maxDate);



    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleReportMinDTO( " +
            "obj.id, obj.date, obj.amount, obj.seller.name " +
            ") " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND LOWER (obj.seller.name) LIKE LOWER(CONCAT('%',:name,'%'))")
    Page<SaleReportMinDTO> searchSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
}
