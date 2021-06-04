package com.msc.mall.domain.gateway;

import com.msc.mall.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}
