package com.msc.fix.lisa.service.system;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysLoginService;
import com.msc.fix.lisa.dto.system.SysUserCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 15:54
 * Description: No Description
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {


    @Autowired
    private CommandBusI commandBusI;


    @Override
    public SingleResponse<SysUserCo> login(SysUserCmd sysUserCmd) {
        return (SingleResponse<SysUserCo>) commandBusI.send(sysUserCmd);
    }
}
