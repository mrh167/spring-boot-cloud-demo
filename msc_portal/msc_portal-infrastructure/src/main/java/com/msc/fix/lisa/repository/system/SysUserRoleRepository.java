package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.domain.common.utils.MapUtils;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;
import com.msc.fix.lisa.domain.gateway.system.SysUserRoleGateway;
import com.msc.fix.lisa.repository.db.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/28
 * Time: 16:47
 * Description: No Description
 */
@Service
public class SysUserRoleRepository extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleGateway {

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        for(Long roleId : roleIdList){
            SysUserRole sysUserRoleEntity = new SysUserRole();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            this.save(sysUserRoleEntity);
        }
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }
}
