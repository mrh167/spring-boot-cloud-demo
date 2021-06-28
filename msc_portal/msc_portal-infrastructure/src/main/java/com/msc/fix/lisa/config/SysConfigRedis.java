/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.msc.fix.lisa.config;


import com.msc.fix.lisa.domain.common.utils.RedisUtils;
import com.msc.fix.lisa.domain.entity.system.SysConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统配置Redis
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class SysConfigRedis {
    @Resource
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfig config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfig get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfig.class);
    }
}
