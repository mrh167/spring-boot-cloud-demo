package com.msc.fix.lisa.mybatiscn.dto;

public class GenDtoFromSqlReq {

    private String sql;

    private GenDtoConfig config = new GenDtoConfig();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public GenDtoConfig getConfig() {
        return config;
    }

    public void setConfig(GenDtoConfig config) {
        this.config = config;
    }
}
