package com.msc.fix.lisa.dto;

import com.alibaba.cola.dto.Command;
import com.msc.fix.lisa.dto.domainmodel.Customer;
import lombok.Data;

@Data
public class CustomerAddCmd extends Command{

    private Customer customer;

}
