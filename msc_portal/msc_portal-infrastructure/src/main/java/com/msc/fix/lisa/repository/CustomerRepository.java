package com.msc.fix.lisa.repository;

import com.msc.fix.lisa.domain.customer.Customer;
import com.msc.fix.lisa.domain.gateway.CustomerGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository implements CustomerGateway {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer getByById(String customerId){
      CustomerDO customerDO = customerMapper.getById(customerId);
      //Convert to Customer
      return null;
    }
}
