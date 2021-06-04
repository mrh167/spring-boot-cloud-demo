package com.msc.mall.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.msc.mall.dto.CustomerAddCmd;
import com.msc.mall.dto.CustomerListByNameQry;
import com.msc.mall.dto.domainmodel.Customer;

public interface CustomerServiceI {

    public Response addCustomer(CustomerAddCmd customerAddCmd);

    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry);
}
