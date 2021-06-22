/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.api.system.SysRoleService;
import com.msc.fix.lisa.api.system.SysUserRoleService;
import com.msc.fix.lisa.api.system.SysUserService;
import com.msc.fix.lisa.common.RRException;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.common.utils.Query;
import com.msc.fix.lisa.domain.entity.system.SysRole;
import com.msc.fix.lisa.domain.entity.system.SysRoleMenu;
import com.msc.fix.lisa.domain.gateway.system.SysRoleMenuGateway;
import com.msc.fix.lisa.dto.system.SysRoleQry;
import com.msc.fix.lisa.dto.system.cto.SysRoleCo;
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
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	@Autowired
	private SysRoleMenuGateway sysRoleMenuService;
	@Autowired
	private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

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
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleQry role) {
		SysRole sysRole = BeanUtils.convert(role, SysRole.class);

		baseMapper.updateById(sysRole);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

	@Override
	public List<SysRoleCo> selectByMap(Map<String, Object> map) {
		List<SysRole> sysRoles = baseMapper.selectByMap(map);
		List<SysRoleCo> sysRoleCos = BeanUtils.convertList(sysRoles, SysRoleCo.class);
		return sysRoleCos;
	}

	@Override
	public SysRoleCo getByIds(Long roleId) {
		SysRole sysRole = baseMapper.selectById(roleId);
		SysRoleCo roleCo = BeanUtils.convert(sysRole, SysRoleCo.class);
		return roleCo;
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleQry role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new RRException("新增角色的权限，已超出你的权限范围");
		}
	}
}
