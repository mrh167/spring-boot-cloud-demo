package com.msc.fix.lisa.controller.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.system.SysLoginService;
import com.msc.fix.lisa.dto.system.SysUserCmd;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 14:23
 * Description: No Description
 */
@Api(tags = "登录",produces = "用户登录页面")
@RequestMapping("/api")
@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;


    @ApiModelProperty(value = "登录接口")
    @PostMapping(value = "/login")
    public SingleResponse<SysUserCo> login(@RequestBody @Valid SysUserCmd sysUserCmd, HttpServletRequest request){
//        String captcha =(String) request.getSession().getAttribute("captcha");
//        sysUserCmd.setCaptcha(captcha);
        return sysLoginService.login(sysUserCmd);
    }




}
