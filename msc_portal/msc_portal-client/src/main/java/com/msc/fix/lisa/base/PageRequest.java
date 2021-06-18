package com.msc.fix.lisa.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分页 Request
 *
 * @author lishudong
 * @version 1.0
 * @date 2019/7/16
 */
@ApiModel
@Data
public class PageRequest extends BaseQuery {

    /**
     * 当前页码，下标从1开始
     */
    @NotNull
    @Range(min = 1)
    @ApiModelProperty(value = "当前页码，下标从1开始", required = true)
    private Integer pageNum;

    /**
     * 每页的数量
     */
    @NotNull
    @ApiModelProperty(value = "每页的数量", required = true)
    private Integer pageSize;

}


