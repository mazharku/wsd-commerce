package com.wsd.commerce.service;

import com.wsd.commerce.model.dto.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> fetchAllCustomer();
}
