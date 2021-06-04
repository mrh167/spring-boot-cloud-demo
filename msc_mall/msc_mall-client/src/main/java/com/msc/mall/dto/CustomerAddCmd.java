package com.msc.mall.dto;

import com.alibaba.cola.dto.Command;
import com.msc.mall.dto.domainmodel.Customer;
import lombok.Data;

@Data
public class CustomerAddCmd extends Command{

    private Customer customer;

}
