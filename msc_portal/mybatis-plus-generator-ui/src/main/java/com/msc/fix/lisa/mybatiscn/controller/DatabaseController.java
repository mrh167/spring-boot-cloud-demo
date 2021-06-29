package com.msc.fix.lisa.mybatiscn.controller;

import com.msc.fix.lisa.mybatiscn.common.Result;
import com.msc.fix.lisa.mybatiscn.common.ResultGenerator;
import com.msc.fix.lisa.mybatiscn.dto.TableInfo;
import com.msc.fix.lisa.mybatiscn.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/tables")
    public Result getAllTables() {
        List<TableInfo> tables = databaseService.getTablesFromDb();
        return ResultGenerator.genSuccessResult(tables);
    }

}
