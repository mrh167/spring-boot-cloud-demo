package com.msc.fix.lisa.service;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysUserService;
import com.msc.fix.lisa.dto.system.QueryName;
import com.msc.fix.lisa.dto.system.SysUserCo;
import com.msc.fix.lisa.dto.system.UploadQry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:39
 * Description: No Description
 */
@Service
public class UploadServiceImpl implements SysUserService {

    @Autowired
    private CommandBusI commandBus;



    @Override
    public SingleResponse<List<SysUserCo>> list(UploadQry uploadQry) {
        return (SingleResponse<List<SysUserCo>>) commandBus.send(uploadQry);
    }

    @Override
    public SingleResponse<SysUserCo> queryByUserName(QueryName queryName) {

        return (SingleResponse<SysUserCo>) commandBus.send(queryName);
    }
}
