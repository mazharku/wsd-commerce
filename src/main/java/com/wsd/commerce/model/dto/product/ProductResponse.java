package com.wsd.commerce.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductResponse {
    private String name;
    private double price;
}
