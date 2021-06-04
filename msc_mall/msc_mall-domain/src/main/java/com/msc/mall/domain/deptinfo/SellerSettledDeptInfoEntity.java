package com.msc.mall.domain.deptinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家入驻事业部详细表
 * 
 * @author maruihua
 * @email 1003534413@qq.com
 * @date 2021-06-04 18:28:53
 */
@Data
public class SellerSettledDeptInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 事业部编码
	 */
	private String deptNo;
	/**
	 * 事业部名名称
	 */
	private String deptName;
	/**
	 * 商家编码
	 */
	private String sellerNo;
	/**
	 * 商家名称
	 */
	private String sellerName;
	/**
	 * C码
	 */
	private String clientCode;
	/**
	 * 主合同编号,逗号分隔
	 */
	private String contractCode;
	/**
	 * 智能商务仓,0 否 1 是
	 */
	private Integer businessClass;
	/**
	 * 签约区域,详见签约区域枚举
	 */
	private String registerRegion;
	/**
	 * eclp商家类型FCS/FBP/SOP
	 */
	private String eclpSellerType;
	/**
	 * 集团销售
	 */
	private String groupAccount;
	/**
	 * 区域销售
	 */
	private String regionAccount;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 修改人
	 */
	private String updateUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 0是删除 1正常
	 */
	private Integer yn;

}