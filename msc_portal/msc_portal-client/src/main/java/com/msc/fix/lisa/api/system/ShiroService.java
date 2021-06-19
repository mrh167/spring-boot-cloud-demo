/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.api.system;

import com.msc.fix.lisa.dto.system.cto.SysUserTokenCo;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenCo queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserTokenCo queryUser(Long userId);
}
