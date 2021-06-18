package com.msc.fix.lisa.domain.common.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanUtils
 * 复制Util
 *
 * @author guoshihua
 * @date 2019-11-13 14:15
 */
public class BeanUtils {
    /**
     * 私有BeanUtils
     */
    private BeanUtils() {
    }

    /**
     * BeanCopier 缓存
     */
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>(1000);


    /**
     * 转化复制实体
     *
     * @param orig   orig
     * @param target target
     * @return T
     */
    public static <T> T convert(Object orig, Class<T> target) {
        if (orig == null) {
            return null;
        }
        try {
            final BeanCopier beanCopier = createBeanCopier(orig.getClass(), target);
            T destEntry = target.newInstance();
            beanCopier.copy(orig, destEntry, null);
            return destEntry;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转化复制实体LIST
     *
     * @param orig   orig
     * @param target target
     * @return java.util.List<T>
     */
    public static <T> List<T> convertList(List<?> orig, Class<T> target) {
        if (orig == null || orig.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<T> dest = new ArrayList<>(orig.size());
        try {
            final BeanCopier beanCopier = createBeanCopier(orig.get(0).getClass(), target);
            T destEntry;
            for (Object each : orig) {
                destEntry = target.newInstance();
                beanCopier.copy(each, destEntry, null);
                dest.add(destEntry);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dest;
    }

    /**
     * createBeanCopier
     *
     * @param source source
     * @param target target
     * @return org.springframework.cglib.beans.BeanCopier
     */
    private static <T> BeanCopier createBeanCopier(Class<?> source, Class<T> target) {
        String key = genKey(source, target);
        BeanCopier beanCopier = BEAN_COPIER_CACHE.get(key);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(source, target, false);
            BEAN_COPIER_CACHE.putIfAbsent(key, beanCopier);
        }
        return beanCopier;
    }


    /**
     * 生成key
     *
     * @param source source
     * @param target target
     * @return java.lang.String
     */
    private static String genKey(Class<?> source, Class<?> target) {
        return source.getName() + target.getName();
    }
}
