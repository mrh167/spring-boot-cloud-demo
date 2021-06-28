package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.common.utils.MapUtils;
import com.msc.fix.lisa.domain.entity.system.SysMenu;
import com.msc.fix.lisa.domain.gateway.system.SysMenuGateway;
import com.msc.fix.lisa.domain.gateway.system.SysRoleMenuGateway;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.repository.db.mapper.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuRepository extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuGateway {


    @Autowired
    private SysUserGateway sysUserGateway;


    @Autowired
    private SysRoleMenuGateway sysRoleMenuGateway;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenu> queryListParentId(Long parentId) {
        return sysMenuMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return sysMenuMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserGateway.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }
    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();

        for(SysMenu entity : menuList){
            //目录
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuGateway.removeByMap(new MapUtils().put("menu_id", menuId));
    }

//    @Override
//    public SysMenu getById(Long parentId) {
//        return sysMenuMapper.selectByIds(parentId);
//    }

    @Override
    public void updateByIds(SysMenu menu) {
        baseMapper.updateById(menu);
    }

    @Override
    public void saves(SysMenu menu) {
        baseMapper.insert(menu);
    }

    @Override
    public List<SysMenu> select() {
        return baseMapper.selectList(null);
    }

}
