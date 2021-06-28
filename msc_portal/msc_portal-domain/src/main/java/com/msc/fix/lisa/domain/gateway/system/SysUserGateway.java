package com.msc.fix.lisa.domain.gateway.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.entity.system.SysUser;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:53
 * Description: No Description
 */
public interface SysUserGateway extends IService<SysUser> {


    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUser queryByUserName(String username);

    /**
     * 保存用户
     */
    void saveUser(SysUser user);

    /**
     * 修改用户
     */
    void update(SysUser user);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);
}
