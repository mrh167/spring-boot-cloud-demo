package com.msc.fix.lisa.domain.gateway.system;

import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.dto.system.SysUserQry;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:40
 * Description: No Description
 */
public interface SysUserGateWay {

    PageInfo<SysUser> pageList(SysUserQry pageQry);
}
