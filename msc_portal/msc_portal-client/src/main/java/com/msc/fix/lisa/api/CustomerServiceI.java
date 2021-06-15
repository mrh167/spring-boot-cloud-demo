package com.msc.fix.lisa.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.msc.fix.lisa.dto.CustomerAddCmd;
import com.msc.fix.lisa.dto.CustomerListByNameQry;
import com.msc.fix.lisa.dto.domainmodel.Customer;

public interface CustomerServiceI {

    public Response addCustomer(CustomerAddCmd customerAddCmd);

    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry);
}
