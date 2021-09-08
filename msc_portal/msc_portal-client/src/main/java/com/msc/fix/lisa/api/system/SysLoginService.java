package com.msc.fix.lisa.api.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.dto.system.SysUserCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 15:51
 * Description: No Description
 */
public interface SysLoginService {


    SingleResponse<SysUserCo> login(SysUserCmd sysUserCmd);


}
