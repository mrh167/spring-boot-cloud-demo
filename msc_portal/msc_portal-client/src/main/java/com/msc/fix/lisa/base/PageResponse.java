package com.msc.fix.lisa.base;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BasePageResponse
 * 返回对象 page
 *
 * @author lishudong
 * @version 1.0
 * @date 2019/7/16
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class PageResponse<T> extends MultiResponse<T> {

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private int pages;

    /**
     * 当前页码，下标从1开始
     */
    @ApiModelProperty(value = "当前页码，下标从1开始")
    private int pageNum;

    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页的数量")
    private int pageSize;

    /**
     * 当前页记录数
     */
    @ApiModelProperty(value = "当前页记录数")
    private int size;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数最大返回 10000")
    private int total;

    /**
     * 总的记录数量
     */
    @ApiModelProperty(value = "总的记录数量")
    private Long count;

    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集")
    private List<T> data;


    /**
     * 返回空分页对象
     *
     * @param <T> 结果集泛型
     * @return 空分页对象
     */
    public static <T> PageResponse<T> buildPageSuccess() {
        return getPageResponse();
    }

    /**
     * 返回分页对象
     *
     * @param list 数据源
     * @param <T>  结果集泛型
     * @return 分页对象
     */
    public static <T> PageResponse<T> buildListSuccess(List<T> list) {
        PageResponse<T> pageResponse = copyPageResponse(getPageInfo(list));
        pageResponse.setData(list);
        return pageResponse;
    }


    /**
     * 返回分页对象
     *
     * @param pageInfo 数据源
     * @param <T>      结果集泛型
     * @return 分页对象
     */
    public static <T> PageResponse<T> buildPageSuccess(PageInfo<T> pageInfo) {
        PageResponse<T> pageResponse = copyPageResponse(pageInfo);
        pageResponse.setData(pageInfo.getList());
        return pageResponse;
    }

    /**
     * 返回分页对象
     *
     * @param pageInfo 数据源
     * @param clazz    结果集类型
     * @param <R>      数据源泛型
     * @param <T>      结果集泛型
     * @return 分页对象
     */
    public static <R, T> PageResponse<T> buildPageSuccess(PageInfo<R> pageInfo, Class<T> clazz) {
        PageResponse<T> pageResponse = copyPageResponse(pageInfo);
        pageResponse.setData(convertList(pageInfo.getList(), clazz));
        return pageResponse;
    }

    /**
     * 返回分页对象
     *
     * @param pageInfo 数据源
     * @param clazz    结果集类型
     * @param <R>      数据源泛型
     * @param <T>      结果集泛型
     * @return 分页对象
     */
    public static <R, T> PageResponse<T> buildPageSuccessByMap(PageInfo<R> pageInfo, Class<T> clazz) {
        PageResponse<T> pageResponse = copyPageResponse(pageInfo);
        String data = JSONObject.toJSONString(pageInfo.getList());
        pageResponse.setData(JSONArray.parseArray(data, clazz));
        return pageResponse;
    }

    /**
     * copy properties
     *
     * @param pageInfo 数据源
     * @param <T>      结果集泛型
     * @return 分页属性的分页对象
     */
    private static <T> PageResponse<T> copyPageResponse(PageInfo<?> pageInfo) {
        PageResponse<T> response = getPageResponse();
        response.setPages(pageInfo.getPages());
        response.setPageNum(pageInfo.getPageNum());
        response.setPageSize(pageInfo.getPageSize());
        response.setSize(pageInfo.getSize());
        response.setTotal((int) pageInfo.getTotal());
        response.setCount(pageInfo.getTotal());
        return response;
    }

    /**
     * 获取空分页对象
     *
     * @param <T> 结果集泛型
     * @return 空分页对象
     */
    private static <T> PageResponse<T> getPageResponse() {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setData(Collections.EMPTY_LIST);
        return response;
    }

    /**
     * 获取分页属性
     *
     * @param list 数据源
     * @return PageInfo PageInfo
     */
    private static PageInfo<?> getPageInfo(List<?> list) {
        return PageInfo.of(list);
    }

    /**
     * 对list中对象进行属性拷贝，生成新list
     *
     * @param orig  orig
     * @param clazz clazz
     * @param <T>   <T>
     * @return List<T>
     */
    private static <T> List<T> convertList(List<?> orig, Class<T> clazz) {
        if (CollUtil.isEmpty(orig)) {
            return new ArrayList<>(0);
        }
        List<T> dest = new ArrayList<>(orig.size());
        try {
            final BeanCopier copier = BeanCopier.create(orig.get(0).getClass(), clazz, false);
            for (Object each : orig) {
                if (each == null) {
                    dest.add(null);
                }
                T destEntry = clazz.newInstance();
                copier.copy(each, destEntry, null);
                dest.add(destEntry);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dest;
    }

}
