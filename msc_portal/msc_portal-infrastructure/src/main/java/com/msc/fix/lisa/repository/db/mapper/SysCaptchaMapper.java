/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.system.SysCaptcha;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysCaptchaMapper extends BaseMapper<SysCaptcha> {

}
