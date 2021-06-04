package com.msc.mall.controller;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.msc.mall.api.CustomerServiceI;
import com.msc.mall.dto.CustomerAddCmd;
import com.msc.mall.dto.CustomerListByNameQry;
import com.msc.mall.dto.domainmodel.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Api(tags = "测试", produces = "application/json")
@RestController
@RequestMapping(value = "/api/customerapp")
public class CustomerController {

    @Autowired
    private CustomerServiceI customerService;

    @ApiOperation(value = "测试get")
    @GetMapping(value = "/customer")
    public MultiResponse<Customer> listCustomerByName(@RequestParam String name){
        CustomerListByNameQry customerListByNameQry = new CustomerListByNameQry();
        customerListByNameQry.setName(name);
        return customerService.listByName(customerListByNameQry);
    }

    @ApiOperation(value = "测试post")
    @PostMapping(value = "/customer")
    public Response addCustomer(@RequestBody CustomerAddCmd customerAddCmd){
        return customerService.addCustomer(customerAddCmd);
    }
}
