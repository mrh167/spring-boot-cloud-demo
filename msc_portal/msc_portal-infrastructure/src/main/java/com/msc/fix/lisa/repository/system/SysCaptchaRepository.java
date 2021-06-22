package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.msc.fix.lisa.common.RRException;
import com.msc.fix.lisa.domain.common.utils.DateUtils;
import com.msc.fix.lisa.domain.entity.system.SysCaptcha;
import com.msc.fix.lisa.domain.gateway.system.SysCaptchaGateway;
import com.msc.fix.lisa.repository.db.mapper.SysCaptchaMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/18
 * Time: 16:14
 * Description: No Description
 */
@Component
@Repository
public class SysCaptchaRepository extends ServiceImpl<SysCaptchaMapper, SysCaptcha> implements SysCaptchaGateway {
    @Autowired
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptcha captchaEntity = new SysCaptcha();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        this.save(captchaEntity);
        return producer.createImage(code);
    }
    @Override
    public boolean validate(String uuid, String code) {
        SysCaptcha captchaEntity = this.getOne(new QueryWrapper<SysCaptcha>().eq("uuid", uuid));
        if(captchaEntity == null){
            return false;
        }

        //删除验证码
        this.removeById(uuid);

        if(captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }
        return false;
    }
}