package com.msc.fix.lisa.domain.gateway.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.entity.system.SysRole;
import com.msc.fix.lisa.dto.system.SysRoleQry;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/28
 * Time: 15:42
 * Description: No Description
 */
public interface SysRoleGateway extends IService<SysRole> {
    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRoleQry role);

    void update(SysRoleQry role);

    void deleteBatch(Long[] roleIds);


    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
}
