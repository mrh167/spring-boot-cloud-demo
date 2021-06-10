package com.xiaoma.email.emaildemo.common.utils;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * StringUtils
 *
 * @Author khj
 * @Date 2020/4/3 14:02
 */
public class StringUtils {

    /**
     * DecimalFormat-100
     */
    private static final BigDecimal BIGDECIMAL_HUNDRED = new BigDecimal(100);
    /**
     * DecimalFormat-1
     */
    private static final BigDecimal BIGDECIMAL_ONE = new BigDecimal(1);

    /**
     * null
     */
    private final static String NULL = "null";

    /**
     * <pre>
     * 判断是否为空，为空则返回true
     * 为空的条件：null、""、"null"
     * </pre>
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        String str = obj.toString().trim();
        if ("".equals(str) || NULL.equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * 判断是否不为空，不为空则返回true
     * 为空的条件：null、""、"null"
     * </pre>
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 判断集合是否为空
     *
     * @param c list/set
     * @return
     */
    public static boolean collectionIsEmpty(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合是否不为空
     *
     * @param c list/set
     * @return
     */
    public static boolean collectionIsNotEmpty(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 字段值是否发送了改变
     *
     * @param source 原值
     * @param target 新值
     * @return true:已改变
     */
    public static boolean isChanged(String source, String target) {
        if (isBlank(source) && isBlank(target)) {
            return false;
        }
        if (isNotBlank(source) && isNotBlank(target)) {
            return !source.equals(target);
        }
        return true;
    }

    /**
     * 环比的百分比数-带正负号,toStr相对于yearStr上升下降了多少
     *
     * @param toStr   除数
     * @param yearStr 被除数
     * @return
     */
    public static BigDecimal divide(String toStr, String yearStr) {
        // toDay yearStr为负数不考虑
        BigDecimal toDay = isNotBlank(toStr) ? new BigDecimal(toStr) : BigDecimal.ZERO;
        BigDecimal yearDay = isNotBlank(yearStr) ? new BigDecimal(yearStr) : BigDecimal.ZERO;
        BigDecimal increaseNum;
        if (yearDay.compareTo(BigDecimal.ZERO) == 0) {
            if (toDay.compareTo(BigDecimal.ZERO) == 0) {
                increaseNum = BigDecimal.ZERO;
            } else {
                increaseNum = BIGDECIMAL_ONE;
            }
        } else {
            increaseNum = (toDay.subtract(yearDay)).divide(yearDay, 4, BigDecimal.ROUND_HALF_UP);
        }
        return increaseNum.multiply(BIGDECIMAL_HUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}

