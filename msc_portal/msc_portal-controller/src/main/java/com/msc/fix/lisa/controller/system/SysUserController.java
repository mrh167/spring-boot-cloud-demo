package com.msc.fix.lisa.controller.system;

import com.msc.fix.lisa.api.system.SysUserService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:13
 * Description: No Description
 */
@Api(tags = "用户管理页面",produces = "用户管理页面")
@RequestMapping("/api")
@RestController
public class SysUserController{

    @Autowired
    private SysUserService sysUserService;


    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/pageList")
    public PageResponse<SysUserCo> pageList(@RequestBody SysUserQry sysUserQry){
        return sysUserService.pageList(sysUserQry);
    }
}
