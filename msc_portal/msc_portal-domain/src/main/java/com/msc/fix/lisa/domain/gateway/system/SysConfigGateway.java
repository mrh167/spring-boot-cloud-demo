package com.msc.fix.lisa.domain.gateway.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.entity.system.SysConfig;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/28
 * Time: 15:51
 * Description: No Description
 */
public interface SysConfigGateway extends IService<SysConfig> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置信息
     */
    public void saveConfig(SysConfig config);

    /**
     * 更新配置信息
     */
    public void update(SysConfig config);

    /**
     * 根据key，更新value
     */
    public void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    public void deleteBatch(Long[] ids);

    /**
     * 根据key，获取配置的value值
     *
     * @param key           key
     */
    public String getValue(String key);

    /**
     * 根据key，获取value的Object对象
     * @param key    key
     * @param clazz  Object对象
     */
    public <T> T getConfigObject(String key, Class<T> clazz);


    SysConfig getByIds(Long id);
}
