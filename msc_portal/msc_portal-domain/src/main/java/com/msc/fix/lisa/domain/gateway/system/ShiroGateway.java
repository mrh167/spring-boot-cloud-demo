package com.msc.fix.lisa.domain.gateway.system;

import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.dto.system.cto.SysUserTokenCo;

import java.util.Set;

public interface ShiroGateway {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenCo queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUser queryUser(Long userId);
}
