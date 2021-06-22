package com.msc.fix.lisa.domain.gateway.system;

import com.msc.fix.lisa.domain.entity.system.SysMenu;
import com.msc.fix.lisa.dto.system.cto.SysMenuCo;

import java.util.List;

public interface SysMenuGateway {
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenuCo> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuCo> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuCo> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenuCo> getUserMenuList(Long userId);

    /**
     * 删除
     */
    void delete(Long menuId);

    SysMenu getById(Long parentId);

    void updateByIds(SysMenu menu);

    void saves(SysMenu menu);

    List<SysMenu> select();
}
