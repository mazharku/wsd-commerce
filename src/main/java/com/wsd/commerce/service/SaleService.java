package com.wsd.commerce.service;

import com.wsd.commerce.model.dto.product.ProductResponse;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {

    double currentDaySaleAmount();

    LocalDate maxSaleDateOfRange(LocalDate start, LocalDate end);

    List<String> topFiveSaleProduct();

    List<String> lastMonthTopFiveSaleProduct();

    List<ProductResponse> customerWishList();

}
