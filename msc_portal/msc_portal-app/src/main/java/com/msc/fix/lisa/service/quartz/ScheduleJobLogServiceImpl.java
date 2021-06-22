/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.service.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.api.quartz.ScheduleJobLogService;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.common.utils.Query;
import com.msc.fix.lisa.domain.entity.quartz.ScheduleJobLog;
import com.msc.fix.lisa.dto.system.cto.ScheduleJobLogCo;
import com.msc.fix.lisa.repository.db.mapper.ScheduleJobLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements ScheduleJobLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String jobId = (String)params.get("jobId");

		IPage<ScheduleJobLog> page = this.page(
			new Query<ScheduleJobLog>().getPage(params),
			new QueryWrapper<ScheduleJobLog>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
		);

		return new PageUtils(page);
	}

	@Override
	public void save(ScheduleJobLogCo log) {
		ScheduleJobLog scheduleJobLog = BeanUtils.convert(log, ScheduleJobLog.class);
		baseMapper.insert(scheduleJobLog);
	}

	@Override
	public ScheduleJobLogCo getByIds(Long logId) {
		ScheduleJobLog scheduleJobLog = baseMapper.selectById(logId);
		ScheduleJobLogCo scheduleJobLogCo = BeanUtils.convert(scheduleJobLog, ScheduleJobLogCo.class);
		return scheduleJobLogCo;
	}

}
