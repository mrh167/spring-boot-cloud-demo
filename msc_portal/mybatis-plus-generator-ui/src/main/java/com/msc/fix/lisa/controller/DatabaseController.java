package com.msc.fix.lisa.controller;

import com.msc.fix.lisa.common.Result;
import com.msc.fix.lisa.common.ResultGenerator;
import com.msc.fix.lisa.dto.TableInfo;
import com.msc.fix.lisa.service.DatabaseService;
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
