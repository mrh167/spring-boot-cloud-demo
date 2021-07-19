package com.msc.fix.lisa.dto;

import com.msc.fix.lisa.base.PageRequest;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 15:54
 * Description: No Description
 */
@Data
public class BaseNodeRegionPageQry extends PageRequest {
    /**
     * 商家编号
     */
    private String sellerNo;
    /**
     * 商家名称
     */
    private String sellerName;
    /**
     * 事业部编码
     */
    private String deptNo;
    /**
     * 事业部名称
     */
    private String deptName;
    /**
     * 节点编码
     */
    private String nodeNo;
    /**
     * 节点名称
     */
    private String nodeName;
}
