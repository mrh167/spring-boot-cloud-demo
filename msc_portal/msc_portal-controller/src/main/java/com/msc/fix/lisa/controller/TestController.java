package com.msc.fix.lisa.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.BaseNodeRegionService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import com.msc.fix.lisa.dto.ImageUploadCmd;
import com.msc.fix.lisa.dto.system.cto.BaseNodeRegionCo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.dc.pr.PRException;

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

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public SingleResponse upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws PRException {

        if (file.getSize()<1) {
            throw new PRException("上传文件不能为空");
        }
        ImageUploadCmd uploadCmd = new ImageUploadCmd();
        uploadCmd.setFile(file);
        return baseNodeRegionService.upload(uploadCmd);
    }



}
