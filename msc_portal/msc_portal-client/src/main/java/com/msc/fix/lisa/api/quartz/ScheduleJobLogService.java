/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.api.quartz;

import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.dto.system.cto.ScheduleJobLogCo;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobLogService {

	PageUtils queryPage(Map<String, Object> params);


	void save(ScheduleJobLogCo log);

	ScheduleJobLogCo getByIds(Long logId);
}
