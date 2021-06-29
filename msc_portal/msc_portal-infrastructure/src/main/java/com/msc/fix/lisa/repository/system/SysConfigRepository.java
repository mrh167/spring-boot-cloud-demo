package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.msc.fix.lisa.common.RRException;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.config.SysConfigRedis;
import com.msc.fix.lisa.domain.common.utils.Query;
import com.msc.fix.lisa.domain.entity.system.SysConfig;
import com.msc.fix.lisa.domain.gateway.system.SysConfigGateway;
import com.msc.fix.lisa.repository.db.mapper.SysConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/28
 * Time: 15:52
 * Description: No Description
 */
@Service
public class SysConfigRepository extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigGateway {
    @Resource
    private SysConfigRedis sysConfigRedis;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String)params.get("paramKey");

        IPage<SysConfig> page = this.page(
                new Query<SysConfig>().getPage(params),
                new QueryWrapper<SysConfig>()
                        .like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
                        .eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Override
    public void saveConfig(SysConfig config) {
        this.save(config);
        sysConfigRedis.saveOrUpdate(config);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfig config) {
        this.updateById(config);
        sysConfigRedis.saveOrUpdate(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
        sysConfigRedis.delete(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for(Long id : ids){
            SysConfig config = this.getById(id);
            sysConfigRedis.delete(config.getParamKey());
        }

        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getValue(String key) {
        SysConfig config = sysConfigRedis.get(key);
        if(config == null){
            config = baseMapper.queryByKey(key);
            sysConfigRedis.saveOrUpdate(config);
        }

        return config == null ? null : config.getParamValue();
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if(StringUtils.isNotBlank(value)){
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RRException("获取参数失败");
        }
    }

    @Override
    public SysConfig getByIds(Long id) {
        SysConfig sysConfig = baseMapper.selectById(id);
        return sysConfig;
    }
}