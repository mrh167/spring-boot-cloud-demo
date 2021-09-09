package com.msc.fix.lisa.executor.query.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.UpdateStatusCmd;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/8
 * Time: 12:25
 * Description: No Description
 */
@Command
public class UpdateStatusCmdExe implements CommandExecutorI<SingleResponse, UpdateStatusCmd> {

    @Autowired
    private SysUserGateWay sysUserGateWay;


    @Override
    public SingleResponse execute(UpdateStatusCmd cmd) {

        return sysUserGateWay.updateStatus(cmd);
    }
}
