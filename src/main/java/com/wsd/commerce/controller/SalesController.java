package com.wsd.commerce.controller;

import com.wsd.commerce.annotation.SecureAPI;
import com.wsd.commerce.model.dto.product.ProductResponse;
import com.wsd.commerce.service.SaleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
@Tag(name = "Sales Controller")
@SecureAPI
public class SalesController {
    private final SaleService service;

    @GetMapping("/customer/wish")
    public ResponseEntity<Page<ProductResponse>> customerWishList(@RequestParam(name = "offset") int offset,
                                                                  @RequestParam(name = "limit") int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("price").descending());
        Page<ProductResponse> productResponses = service.customerWishList(pageable);
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/today")
    public ResponseEntity<?> saleOfToday() {
        double saleAmount = service.currentDaySaleAmount();
        return ResponseEntity.ok(saleAmount);
    }

    @GetMapping("/{startDate}/{endDate}")
    public ResponseEntity<?> mostSellableDate(@PathVariable("startDate")LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
        LocalDate maxSaleDateOfRange = service.maxSaleDateOfRange(startDate, endDate);
        return ResponseEntity.ok(maxSaleDateOfRange);
    }

    @GetMapping("/top-item")
    public ResponseEntity<?> topSellableItem() {
        List<String> topProducts = service.topFiveSaleProduct();
        return ResponseEntity.ok(topProducts);
    }

    @GetMapping("/last-month/top-item")
    public ResponseEntity<?> lastMonthSellableProduct() {
        List<String> topProducts = service.lastMonthTopFiveSaleProduct();
        return ResponseEntity.ok(topProducts);
    }

}
