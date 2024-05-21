package com.wsd.commerce.service.impl;

import com.wsd.commerce.model.dto.product.ProductResponse;
import com.wsd.commerce.model.dto.product.ProductSale;
import com.wsd.commerce.model.entity.Sale;
import com.wsd.commerce.repository.ProductRepository;
import com.wsd.commerce.repository.SaleRepository;
import com.wsd.commerce.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    @Override
    public double currentDaySaleAmount() {
        return saleRepository.findAllBySaleAt(LocalDate.now()).stream()
                .map(Sale::getTotalAmount)
                .mapToDouble(e -> e).sum();
    }

    @Override
    public LocalDate maxSaleDateOfRange(LocalDate start, LocalDate end) {
        Map<LocalDate, Double> dateWiseSaleAmount = saleRepository.findAllBySaleAtBetween(start, end)
                .stream()
                .collect(Collectors.groupingBy(Sale::getSaleAt, Collectors.summingDouble(Sale::getTotalAmount)));

        double maxSaleAmount = dateWiseSaleAmount.values().stream()
                .mapToDouble(e->e)
                .max().orElse(0.0);

        for(LocalDate date: dateWiseSaleAmount.keySet()){
            if(dateWiseSaleAmount.get(date)==maxSaleAmount){
                return date;
            }
        }

        return LocalDate.now();

    }

    @Override
    public List<String> topFiveSaleProduct() {
        return saleRepository.findTopSaleProduct()
                .stream()
                .map(ProductSale::getName)
                .toList();
    }

    @Override
    public List<String> lastMonthTopFiveSaleProduct() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfLastMonth = today.withDayOfMonth(1).minusMonths(1);
        int monthDuration = firstDayOfLastMonth.withMonth(firstDayOfLastMonth.getMonthValue()).lengthOfMonth();
        LocalDate lastDateOfLastMonth = firstDayOfLastMonth.plusDays(monthDuration);

        return saleRepository.findTopFiveSellingProductsLastMonth(firstDayOfLastMonth,lastDateOfLastMonth)
                .stream()
                .map(ProductSale::getName)
                .toList();
    }

    @Override
    public List<ProductResponse> customerWishList() {
        return productRepository.findAll()
                .stream()
                .map(e-> new ProductResponse(e.getName(),e.getPrice()))
                .toList();
    }
}
