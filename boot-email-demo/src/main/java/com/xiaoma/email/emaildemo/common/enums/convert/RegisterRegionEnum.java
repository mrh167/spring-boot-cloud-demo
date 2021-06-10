package com.xiaoma.email.emaildemo.common.enums.convert;

import com.google.common.collect.Lists;
//import com.jd.wl.vsc.framework.common.exception.BusinessException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 签约区域枚举
 *
 * @author wumeng
 * @date 2021/2/25 17:26
 */
@Getter
public enum RegisterRegionEnum {

    /**
     * 对应ECP接口枚举中的未填写
     */
    WEITIANXIE(0, "其他"),

    HUABEI(1, "华北"),

    HUADONG(2, "华东"),

    HUAZHONG(3, "华中"),

    HUANAN(4, "华南"),

    XIBEI(5, "西北"),

    XINAN(6, "西南"),

    DONGBEI(7, "东北"),

    GJ_SUPPLY_CHAIL(8, "国际供应链"),

    SHANDONG(9, "山东"),

    HENAN(10, "河南"),

    ZHEJIANG(11, "浙江"),

    FUJIAN(12, "福建"),

    JT_KY(13, "集团快运"),
    ;

    /**
     * MAPPINGS
     */
    private static final Map<String, RegisterRegionEnum> MAPPINGS = new HashMap<>(values().length);

    static {
        for (RegisterRegionEnum value : values()) {
            MAPPINGS.put(String.valueOf(value.getCode()), value);
        }
    }

    /**
     * code
     */
    private int code;
    /**
     * desc
     */
    private String desc;

    /**
     * 构造器
     *
     * @param code
     * @param desc
     */
    RegisterRegionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     *
     * @param code
     * @return
     */
    public static RegisterRegionEnum getEnumByCode(String code) {
        RegisterRegionEnum[] values = values();
        for (RegisterRegionEnum registerRegionEnum : values) {
            if (String.valueOf(registerRegionEnum.getCode()).equals(code)) {
                return registerRegionEnum;
            }
        }
        return null;
    }


    /**
     * code => 枚举
     */
    public static RegisterRegionEnum resolve(String code) {
        return MAPPINGS.get(code);
    }

    /**
     * code => 枚举
     */
    public static String convert(String code) {
        if (code == null) {
            return StringUtils.EMPTY;
        }
        RegisterRegionEnum item = MAPPINGS.get(code);
        if (item == null) {
            return StringUtils.EMPTY;
        }
        return item.getDesc();
    }

//    /**
//     * 校验
//     *
//     * @param code
//     */
//    public static void valid(String code) {
//        RegisterRegionEnum resolve = resolve(code);
//        if (resolve == null) {
//            throw new Exception("Unmatched enum RegisterRegionEnum");
//        }
//    }

    /**
     * 转化区域逗号分隔的数据转化成枚举
     *
     * @param registerRegion
     * @return
     */
    public static String parseRegisterRegion(String registerRegion) {
        List<String> resionList = Lists.newArrayList();
        Stream.of(registerRegion.split(",")).forEach(o -> {
            resionList.add(RegisterRegionEnum.convert(o));
        });
        return StringUtils.join(resionList.listIterator(), ",");
    }
}
