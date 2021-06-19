package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.domain.entity.system.SysRoleMenu;
import com.msc.fix.lisa.domain.gateway.system.SysRoleMenuGateway;
import com.msc.fix.lisa.repository.db.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SysRoleMenuRepository extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuGateway {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for(Long menuId : menuIdList){
            SysRoleMenu sysRoleMenuEntity = new SysRoleMenu();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            this.save(sysRoleMenuEntity);
        }
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
