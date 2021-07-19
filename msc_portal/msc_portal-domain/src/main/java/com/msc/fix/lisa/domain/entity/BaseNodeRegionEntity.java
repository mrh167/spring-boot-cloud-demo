package com.msc.fix.lisa.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 节点基础信息地区关系表
 * 
 * @author maruihua
 * @email 1003534413@qq.com
 * @date 2021-07-19 15:44:46
 */
@Getter
@Setter
@TableName("portal_base_node_region")
public class BaseNodeRegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
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
	/**
	 * 区域
	 */
	private String region;

}
