package com.xiaoma.email.emaildemo.common.enums;

import lombok.Getter;

/**
 * 账号管理维度枚举
 *
 * @author wumeng
 * @date 2021/2/25 19:34
 */
@Getter
public enum ManageDimensionEnum {


    SELLER(1, "商家"),

    REGISTERREGION(2, "签约区域"),

    ALL(3, "全部商家"),

    SALES(4, "销售"),

    ;

    ManageDimensionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ManageDimensionEnum getEnumByCode(int code) {
        ManageDimensionEnum[] values = values();
        for (ManageDimensionEnum manageDimensionEnum : values) {
            if (manageDimensionEnum.getCode() == code) {
                return manageDimensionEnum;
            }
        }
        return null;
    }

    private int code;
    private String desc;
}
