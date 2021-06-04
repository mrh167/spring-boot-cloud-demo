package com.msc.mall.domain.gateway;

import com.msc.mall.domain.customer.Customer;
import com.msc.mall.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}
