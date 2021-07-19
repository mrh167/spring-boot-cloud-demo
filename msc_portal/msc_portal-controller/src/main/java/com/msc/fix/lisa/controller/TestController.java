package com.msc.fix.lisa.controller;

import com.msc.fix.lisa.api.BaseNodeRegionService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import com.msc.fix.lisa.dto.system.cto.BaseNodeRegionCo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 15:32
 * Description: No Description
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private BaseNodeRegionService baseNodeRegionService;

    /**
     * 列表
     */
    @ApiOperation("测试案例")
    @PostMapping("/testPost")
    public PageResponse<BaseNodeRegionCo> list(@RequestBody BaseNodeRegionPageQry baseNodeRegionPageQry){


        return baseNodeRegionService.queryPage(baseNodeRegionPageQry);
    }
}
