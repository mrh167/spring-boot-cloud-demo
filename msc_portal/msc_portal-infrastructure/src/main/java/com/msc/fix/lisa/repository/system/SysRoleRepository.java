package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.common.RRException;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.common.utils.Query;
import com.msc.fix.lisa.domain.entity.system.SysRole;
import com.msc.fix.lisa.domain.entity.system.SysRoleMenu;
import com.msc.fix.lisa.domain.gateway.system.SysRoleGateway;
import com.msc.fix.lisa.domain.gateway.system.SysRoleMenuGateway;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.domain.gateway.system.SysUserRoleGateway;
import com.msc.fix.lisa.dto.system.SysRoleQry;
import com.msc.fix.lisa.repository.db.mapper.SysRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/28
 * Time: 15:44
 * Description: No Description
 */
@Service
public class SysRoleRepository extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleGateway {
    @Autowired
    private SysRoleMenuGateway sysRoleMenuGateway;

    @Autowired
    private SysUserRoleGateway sysUserRoleGateway;

    @Autowired
    private SysUserGateway sysUserGateway;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        Long createUserId = (Long)params.get("createUserId");

        IPage<SysRole> page = this.page(
                new Query<SysRole>().getPage(params),
                new QueryWrapper<SysRole>()
                        .like(StringUtils.isNotBlank(roleName),"role_name", roleName)
                        .eq(createUserId != null,"create_user_id", createUserId)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleQry role) {
        role.setCreateTime(new Date());
        SysRole sysRole = BeanUtils.convert(role, SysRole.class);
        baseMapper.insert(sysRole);

        //检查权限是否越权
        checkPrems(role);
        SysRoleMenu roles = BeanUtils.convert(role, SysRoleMenu.class);
        //保存角色与菜单关系
        sysRoleMenuGateway.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleQry role) {
        SysRole sysRole = BeanUtils.convert(role, SysRole.class);

        baseMapper.updateById(sysRole);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuGateway.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuGateway.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleGateway.deleteBatch(roleIds);
    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

//    @Override
//    public List<SysRole> selectByMap(Map<String, Object> map) {
//        return baseMapper.selectByMap(map);
//    }
//
//    @Override
//    public SysRole getByIds(Long roleId) {
//        return baseMapper.selectById(roleId);
//    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleQry role){
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if(role.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserGateway.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if(!menuIdList.containsAll(role.getMenuIdList())){
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
