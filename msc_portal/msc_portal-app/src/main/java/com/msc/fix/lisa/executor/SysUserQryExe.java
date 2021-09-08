package com.msc.fix.lisa.executor;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:33
 * Description: No Description
 */
@Command
public class SysUserQryExe implements CommandExecutorI<PageResponse<SysUserCo>, SysUserQry> {
    @Autowired
    private SysUserGateWay sysUserGateWay;


    @Override
    public PageResponse<SysUserCo> execute(SysUserQry pageQry) {
        PageInfo<SysUser> pageInfo = sysUserGateWay.pageList(pageQry);
        return PageResponse.buildPageSuccess(pageInfo,SysUserCo.class);
    }
}
