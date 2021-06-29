package com.msc.fix.lisa.dto;

import java.util.List;

public class MpgGenCodeDto {

    private List<String> tables;

    private GenSetting genSetting;

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public GenSetting getGenSetting() {
        return genSetting;
    }

    public void setGenSetting(GenSetting genSetting) {
        this.genSetting = genSetting;
    }
}
