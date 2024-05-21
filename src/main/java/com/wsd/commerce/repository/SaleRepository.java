package com.wsd.commerce.repository;

import com.wsd.commerce.model.dto.product.ProductSale;
import com.wsd.commerce.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findAllBySaleAt(LocalDate localDate);

    List<Sale> findAllBySaleAtBetween(LocalDate start, LocalDate end);

    @Query("""
            SELECT p.name as name, SUM(s.quantity * s.product.price) AS total
            FROM Product p
            INNER JOIN Sale s ON p.id = s.product.id
            GROUP BY p.name
            ORDER BY total DESC
            LIMIT 5
            """)
    List<ProductSale> findTopSaleProduct();

    @Query("""
            SELECT p.name AS name, SUM(s.totalAmount) AS total
                    FROM Product p 
                    INNER JOIN Sale s ON p.id = s.product.id 
                    WHERE s.saleAt >= :startDate AND s.saleAt < :endDate
                    GROUP BY p.name
                    ORDER BY total DESC
                    LIMIT 5
            """)
    List<ProductSale> findTopFiveSellingProductsLastMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
