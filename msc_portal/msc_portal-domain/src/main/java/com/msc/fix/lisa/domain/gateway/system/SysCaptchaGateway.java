package com.msc.fix.lisa.domain.gateway.system;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/18
 * Time: 16:08
 * Description: No Description
 */
public interface SysCaptchaGateway {
    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
