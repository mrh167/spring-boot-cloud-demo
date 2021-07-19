package com.msc.fix.lisa.controller.system;

import com.msc.fix.lisa.base.AbstractController;
import com.msc.fix.lisa.common.R;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysCaptchaGateway;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.domain.gateway.system.SysUserTokenGateway;
import com.msc.fix.lisa.dto.system.SysLoginForm;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/15
 * Time: 18:10
 * Description: No Description
 */
@RestController
@Api(tags = "导入模板", produces = "application/json")
@RequestMapping("/api")
public class SysLoginController extends AbstractController {

    @Autowired
    private SysCaptchaGateway sysCaptchaGateway;
    @Autowired
    private SysUserGateway sysUserGateway;

    @Autowired
    private SysUserTokenGateway sysUserTokenGateway;


    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response,String uuid){
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaGateway.getCaptcha(uuid);

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(out);
    }
    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form)throws IOException {
        boolean captcha = sysCaptchaGateway.validate(form.getUuid(), form.getCaptcha());

        if(!captcha){
            R.error("验证码无效");
        }
        //用户信息
        SysUser user = sysUserGateway.queryByUserName(form.getUsername());

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            R.error("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            R.error("账号已被锁定,请联系管理员");
        }

        R r = sysUserTokenGateway.createToken(user.getUserId());
        //生成token，并保存到数据库
        return r;
    }

    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public R logout() {
        sysUserTokenGateway.logouts(getUserId());
        return R.ok();
    }
}



