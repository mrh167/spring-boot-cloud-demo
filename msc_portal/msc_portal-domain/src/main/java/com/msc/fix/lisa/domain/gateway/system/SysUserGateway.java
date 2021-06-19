package com.msc.fix.lisa.domain.gateway.system;

import com.msc.fix.lisa.dto.system.cto.SysUserCo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:53
 * Description: No Description
 */
public interface SysUserGateway {


    SysUserCo queryByUserName(String username);


    List<Long> queryAllMenuId(Long userId);
}
