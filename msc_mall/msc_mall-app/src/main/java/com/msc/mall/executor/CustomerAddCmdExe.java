package com.msc.mall.executor;


import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.domain.DomainEventServiceI;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.msc.mall.dto.CustomerAddCmd;
import com.msc.mall.dto.domainmodel.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;

@Command
public class CustomerAddCmdExe implements CommandExecutorI<Response, CustomerAddCmd>{

    @Override
    public Response execute(CustomerAddCmd cmd) {
        //The flow of usecase is defined here.
        //The core ablility should be implemented in Domain. or sink to Domian gradually
        if(cmd.getCustomer().getCompanyName().equals("ConflictCompanyName")){
            throw new BizException(ErrorCode.B_CUSTOMER_companyNameConflict, "公司名冲突");
        }
        return Response.buildSuccess();
    }

}
