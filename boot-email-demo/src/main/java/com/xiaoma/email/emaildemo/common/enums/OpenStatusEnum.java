package com.xiaoma.email.emaildemo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OpenStatusEnum {
    PENDING_STATUS(2, "待审核"),
    REJECT_STATUS(3, "待申请"),
    FINISH_STATUS(4, "已关闭"),
    ENABLE_STATUS(5, "使用中"),

    /**
     * 已过期状态页面查询使用，数据库中没有保存此状态
     */
    EXPIRE_STATUS(6, "已过期"),
    ;

    /**
     * MAPPINGS
     */
    private static final Map<Integer, OpenStatusEnum> MAPPINGS = new HashMap<>(values().length << 1);
    private static final Map<String, OpenStatusEnum> DESC_MAPPINGS = new HashMap<>(values().length << 1);

    static {
        for (OpenStatusEnum value : values()) {
            MAPPINGS.put(value.getCode(), value);
            DESC_MAPPINGS.put(value.getDesc(), value);
        }
    }

    private Integer code;
    private String desc;


    /**
     * 根据code获取枚举
     *
     * @param code
     * @return
     */
    public static OpenStatusEnum getDescByCode(String code) {
        for (OpenStatusEnum enumType : values()) {
            if (enumType.getCode().equals(Integer.parseInt(code))) {
                return enumType;
            }
        }
        return null;
    }


    /**
     * code => 枚举
     */
    public static String convertToDesc(Integer code) {
        if (code == null) {
            return StringUtils.EMPTY;
        }
        OpenStatusEnum item = MAPPINGS.get(code);
        if (item == null) {
            return StringUtils.EMPTY;
        }
        return item.getDesc();
    }

    /**
     * code => 枚举
     */
    public static Integer convertToCode(String desc) {
        OpenStatusEnum item = DESC_MAPPINGS.get(desc);
        if (item == null) {
            return null;
        }
        return item.getCode();
    }
}
