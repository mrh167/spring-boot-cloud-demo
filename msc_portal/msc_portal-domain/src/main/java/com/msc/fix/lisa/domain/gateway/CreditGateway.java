package com.msc.fix.lisa.domain.gateway;

import com.msc.fix.lisa.domain.customer.Customer;
import com.msc.fix.lisa.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}
