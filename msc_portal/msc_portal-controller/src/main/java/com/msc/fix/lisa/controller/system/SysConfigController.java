/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.controller.system;


import com.msc.fix.lisa.base.AbstractController;
import com.msc.fix.lisa.common.R;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.common.annotation.SysLog;
import com.msc.fix.lisa.domain.common.validator.ValidatorUtils;
import com.msc.fix.lisa.domain.entity.system.SysConfig;
import com.msc.fix.lisa.domain.gateway.system.SysConfigGateway;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigGateway sysConfigGateway;
	
	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigGateway.queryPage(params);

		return R.ok().put("page", page);
	}
	
	
	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		SysConfig config = sysConfigGateway.getByIds(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody SysConfig config){
		ValidatorUtils.validateEntity(config);

		sysConfigGateway.saveConfig(config);
		
		return R.ok();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfig config){
		ValidatorUtils.validateEntity(config);

		sysConfigGateway.update(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigGateway.deleteBatch(ids);
		
		return R.ok();
	}

}
