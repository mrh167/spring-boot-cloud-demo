package com.msc.fix.lisa.api.system;

import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:16
 * Description: No Description
 */
public interface SysUserService {
    PageResponse<SysUserCo> pageList(SysUserQry sysUserQry);
}
