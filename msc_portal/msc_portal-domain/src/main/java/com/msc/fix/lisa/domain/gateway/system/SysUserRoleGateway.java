package com.msc.fix.lisa.domain.gateway.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/28
 * Time: 16:22
 * Description: No Description
 */
public interface SysUserRoleGateway extends IService<SysUserRole> {
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
