package com.xiaoma.email.emaildemo.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;

public class JdStringUtils {
    public JdStringUtils() {
    }

    public static String toStr(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        } else {
            return obj.getClass().isPrimitive() ? "" + obj : obj.toString();
        }
    }

    public static String toStr(Object obj) {
        return toStr(obj, "");
    }

    public static int toInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException var3) {
            return defaultValue;
        }
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static long toLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException var4) {
            return defaultValue;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    public static double toDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException var4) {
            return defaultValue;
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0D);
    }

    public static String briefStr(String str, int length) {
        return str.length() > length ? str.substring(0, length) + "..." : str;
    }

    public static String toChinese(String str) {
        String[] s1 = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = new String[]{"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        StringBuilder result = new StringBuilder();
        int n = str.length();

        for(int i = 0; i < n; ++i) {
            int num = str.charAt(i) - 48;
            if (i != n - 1 && num != 0) {
                result.append(s1[num]).append(s2[n - 2 - i]);
            } else {
                result.append(s1[num]);
            }
        }

        return result.toString();
    }

    public static String urlEncode(String srcStr) {
        try {
            return URLEncoder.encode(srcStr, Charsets.UTF_8.toString());
        } catch (UnsupportedEncodingException var2) {
            return srcStr;
        }
    }

    public static String urlDecode(String srcStr) {
        try {
            return URLDecoder.decode(srcStr, Charsets.UTF_8.toString());
        } catch (UnsupportedEncodingException var2) {
            return srcStr;
        }
    }

    public static String toISO88591Str(String str) {
        try {
            return new String(str.getBytes(Charsets.UTF_8.toString()), Charsets.ISO_8859_1.toString());
        } catch (UnsupportedEncodingException var2) {
            return str;
        }
    }

    public static String removeSpecialChar(String str, String[] chars) {
        String[] replacements = new String[chars.length];

        for(int i = 0; i < replacements.length; ++i) {
            replacements[i] = "";
        }

        return StringUtils.replaceEach(str, chars, replacements);
    }

    public static String removeSpecialChar(String str) {
        return str == null ? null : removeSpecialChar(str, new String[]{"(", ")", "{", "}", "[", "]"});
    }

    public static void main(String[] args) {
        System.out.println(Charsets.ISO_8859_1.toString());
    }
}