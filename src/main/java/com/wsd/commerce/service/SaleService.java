package com.wsd.commerce.service;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {

    double currentDaySaleAmount();

    LocalDate maxSaleDateOfRange(LocalDate start, LocalDate end);

    List<String> topFiveSaleProduct();

    List<String> lastMonthTopFiveSaleProduct();

}
