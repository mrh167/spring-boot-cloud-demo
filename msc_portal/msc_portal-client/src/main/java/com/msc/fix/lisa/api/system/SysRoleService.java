/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.api.system;

import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.dto.system.cto.SysRoleCo;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysRoleService {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleCo role);

	void update(SysRoleCo role);

	void deleteBatch(Long[] roleIds);


	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
