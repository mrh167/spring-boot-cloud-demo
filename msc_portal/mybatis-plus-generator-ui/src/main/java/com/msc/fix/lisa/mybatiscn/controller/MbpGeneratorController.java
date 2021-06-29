package com.msc.fix.lisa.mybatiscn.controller;

import com.msc.fix.lisa.mybatiscn.common.Result;
import com.msc.fix.lisa.mybatiscn.common.ResultGenerator;
import com.msc.fix.lisa.mybatiscn.dto.MpgGenCodeDto;
import com.msc.fix.lisa.mybatiscn.service.MbpGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mbp-generator")
public class MbpGeneratorController {

    @Autowired
    private MbpGeneratorService mbpGeneratorService;

    @PostMapping("/gen-code")
    public Result genCode(@RequestBody MpgGenCodeDto dto) {
        mbpGeneratorService.genCodeBatch(dto.getGenSetting(), dto.getTables());
        return ResultGenerator.genSuccessResult();
    }

}
