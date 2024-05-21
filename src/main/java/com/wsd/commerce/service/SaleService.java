package com.wsd.commerce.service;

import com.wsd.commerce.model.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {

    double currentDaySaleAmount();

    LocalDate maxSaleDateOfRange(LocalDate start, LocalDate end);

    List<String> topFiveSaleProduct();

    List<String> lastMonthTopFiveSaleProduct();

    Page<ProductResponse> customerWishList(Pageable pageable);

}
