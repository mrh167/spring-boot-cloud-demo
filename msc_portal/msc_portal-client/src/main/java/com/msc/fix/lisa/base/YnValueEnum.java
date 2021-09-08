package com.msc.fix.lisa.base;

public enum YnValueEnum {
    N(0, "无效"),
    Y(1, "有效");

    private Integer code;
    private String name;

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private YnValueEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Integer getYesCode() {
        return Y.getCode();
    }

    public static Integer yesCode() {
        return Y.getCode();
    }

    public static Integer getNoCode() {
        return N.getCode();
    }

    public static Integer noCode() {
        return N.getCode();
    }
}