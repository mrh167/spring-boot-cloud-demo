package com.msc.mall.base;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cola.dto.MultiResponse;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
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
public class EsPageResponse<T> extends MultiResponse<T> {

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
    @ApiModelProperty(value = "总记录数")
    private int total;

    /**
     * data
     */
    @ApiModelProperty(value = "数据")
    private List<T> data;

    /**
     * 返回分页 response
     *
     * @param pageInfo pageInfo
     * @param clazz    clazz
     * @return BasePageResponse<T>
     */
    public static <T> EsPageResponse<T> buildPageSuccess(PageInfo pageInfo, Class<T> clazz) {
        EsPageResponse<T> response = new EsPageResponse<>();
        response.setSuccess(true);
        response.setPages(pageInfo.getPages());
        response.setPageNum(pageInfo.getPageNum());
        response.setPageSize(pageInfo.getPageSize());
        response.setSize(pageInfo.getSize());
        response.setTotal((int) pageInfo.getTotal() > 10000 ? 10000 : (int) pageInfo.getTotal());
        response.setData(convertList(pageInfo.getList(), clazz));
        return response;
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

    /**
     *
     * @return
     */
    @Override
    public int getTotal() {
        return Math.min(10000, total);
    }
}
