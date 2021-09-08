package com.msc.fix.lisa.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.dto.system.SysUserCoooo;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/31
 * Time: 18:44
 * Description: No Description
 */
@Api(tags = "测试注册")
@RestController
public class TestController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/register")
    public SingleResponse<SysUserCo> register(@RequestBody SysUserCoooo sysUser){
        SysUser user = new SysUser();
        user.setUsername(sysUser.getUsername());
        String pwd = passwordEncoder.encode(sysUser.getPassword());
        user.setPassword(pwd);
        user.setAccount(sysUser.getAccount());
        user.setEmail(sysUser.getEmail());
        user.setEnabled(sysUser.getEnabled() ? 0 : 1);
        user.setPhone(sysUser.getPhone());
        user.setCreateUser("JD_SCM");
        user.setCreateTime(new Date());
        user.setUpdateUser("JD_SCM");
        user.setUpdateTime(new Date());
        user.setYn(1);
//        user.initCreate(sysUser.getAccount());
//        SysUserDo userDo = BeanUtils.convert(user, SysUserDo.class);
//        CommonUtil.fillByCreate(new Date(),sysUser.getAccount(),userDo);
        sysUserMapper.insert(user);
       return SingleResponse.buildSuccess();
    }
}
