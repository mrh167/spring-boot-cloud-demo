package com.msc.mall.base;

import com.alibaba.cola.dto.Query;
import com.alibaba.cola.extension.BizScenario;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * BaseQuery
 * 查询
 *
 * @author lzp
 * @version 1.0
 * @date 2020/4/10
 */
@Data
public class BaseQuery extends Query {
    /**
     * 当前用户
     */
    @ApiModelProperty(hidden = true)
    private String account;

    /**
     * 当前用户
     */
    @ApiModelProperty(hidden = true)
    private String pin;

    /**
     * 商户编号
     */
    @ApiModelProperty(hidden = true)
    private String sellerNo;

    /**
     * 父类Command的属性，不需要ApiModelProperty
     */
    @ApiModelProperty(hidden = true)
    private BizScenario bizScenario;
}
