package com.xiaoma.email.emaildemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/5/20
 * Time: 14:22
 * Description: No Description
 */
@Data
@AllArgsConstructor
public class UrgeLogInfoCo {
    /**
     * 主键
     */

    private Long id;

    /**
     * 商家编码
     */

    private String sellerNo;

    /**
     * 商家名称
     */

    private String sellerName;

    /**
     * 催办次数
     */

    private Integer urgeCount;

    /**
     * 最新催办时间
     */

    private Date lastUrgeTime;

    /**
     * 签约区域
     */
    private String registerRegion;
}
