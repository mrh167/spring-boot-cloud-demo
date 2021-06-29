package com.msc.fix.lisa.controller;

import com.msc.fix.lisa.GeneratorConfig;
import com.msc.fix.lisa.common.Result;
import com.msc.fix.lisa.common.ResultGenerator;
import com.msc.fix.lisa.dto.GenDtoFromSqlReq;
import com.msc.fix.lisa.service.SqlGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sql")
public class SqlGeneratorController {

    @Autowired
    private SqlGeneratorService sqlGeneratorService;

    @Autowired
    private GeneratorConfig generatorConfig;

    @GetMapping("/basepackage")
    public Result getBasepackage() {
        return ResultGenerator.genSuccessResult(generatorConfig.getBasePackage());
    }


    @PostMapping("/gen-mapper-method")
    public Result genMapperMethodFromSQL(@RequestBody GenDtoFromSqlReq params) throws Exception {
        sqlGeneratorService.genMapperMethod(params);
        return ResultGenerator.genSuccessResult();
    }


}
