package com.msc.fix.lisa.domain.gateway;

import com.msc.fix.lisa.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}
