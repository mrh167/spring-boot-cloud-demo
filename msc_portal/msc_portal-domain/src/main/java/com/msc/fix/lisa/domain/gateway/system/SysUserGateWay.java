package com.msc.fix.lisa.domain.gateway.system;

import com.alibaba.cola.dto.SingleResponse;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.UpdateStatusCmd;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:40
 * Description: No Description
 */
public interface SysUserGateWay {

    PageInfo<SysUser> pageList(SysUserQry pageQry);

    SingleResponse updateStatus(UpdateStatusCmd cmd);
}
