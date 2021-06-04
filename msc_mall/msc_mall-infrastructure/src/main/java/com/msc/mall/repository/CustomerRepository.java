package com.msc.mall.repository;

import com.msc.mall.domain.customer.Customer;
import com.msc.mall.domain.gateway.CustomerGateway;

import com.msc.mall.repository.db.doo.CustomerDO;
import com.msc.mall.repository.db.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository implements CustomerGateway {
    @Autowired
    private CustomerMapper customerMapper;

    public Customer getByById(String customerId){
      CustomerDO customerDO = customerMapper.getById(customerId);
      //Convert to Customer
      return null;
    }
}
