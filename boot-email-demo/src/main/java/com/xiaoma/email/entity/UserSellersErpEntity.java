package com.xiaoma.email.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("portal_user_sellers_erp")
public class UserSellersErpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 账号ID
	 */
	private String accountId;
	/**
	 * 商家编号
	 */
	private String sellerNo;
	/**
	 * 商家名称
	 */
	private String sellerName;
	/**
	 * 中文名
	 */
	private String username;
	/**
	 * 邮箱
	 */
	private String email;
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