package com.msc.fix.lisa.controller.system;

import com.msc.fix.lisa.base.AbstractController;
import com.msc.fix.lisa.common.R;
import com.msc.fix.lisa.common.utils.PageUtils;
import com.msc.fix.lisa.domain.common.annotation.SysLog;
import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.common.validator.Assert;
import com.msc.fix.lisa.domain.common.validator.ValidatorUtils;
import com.msc.fix.lisa.domain.common.validator.group.AddGroup;
import com.msc.fix.lisa.domain.common.validator.group.UpdateGroup;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.domain.gateway.system.SysUserRoleGateway;
import com.msc.fix.lisa.executor.query.system.PasswordForm;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserGateway sysUserGateway;
    @Autowired
    private SysUserRoleGateway sysUserRoleGateway;

    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有管理员列表
        if(getUserId() != Constant.SUPER_ADMIN){
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysUserGateway.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info(){
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form){
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserGateway.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId){
        SysUser user = sysUserGateway.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleGateway.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUser user){
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        sysUserGateway.saveUser(user);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUser user){
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getUserId());
        sysUserGateway.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return R.error("系统管理员不能删除");
        }

        if(ArrayUtils.contains(userIds, getUserId())){
            return R.error("当前用户不能删除");
        }

        sysUserGateway.deleteBatch(userIds);

        return R.ok();
    }
}
