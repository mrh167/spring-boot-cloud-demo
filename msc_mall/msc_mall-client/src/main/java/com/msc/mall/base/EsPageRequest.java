package com.msc.mall.base;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.extension.BizScenario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class EsPageRequest extends Command {

    /**
     * 当前页码，下标从1开始
     */
    @NotNull
    @ApiModelProperty(value = "当前页码，下标从1开始，最大值1000", required = true)
    private Integer pageNum;

    /**
     * 每页的数量
     */
    @NotNull
    @ApiModelProperty(value = "每页的数量", required = true)
    private Integer pageSize;

    /**
     * 当前用户
     */
    @ApiModelProperty(value = "当前用户", hidden = true)
    private String pin;

    /**
     * 当前用户
     */
    @ApiModelProperty(value = "当前用户", hidden = true)
    private String account;

    @ApiModelProperty(hidden = true)
    private String sellerNo;

    /**
     * 父类Command的属性，不需要ApiModelProperty
     */
    @ApiModelProperty(hidden = true)
    private BizScenario bizScenario;
}


