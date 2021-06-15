package com.xiaoma.email.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("portal_base_user_info")
public class BaseUserInfoEntity implements Serializable {
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
	 * 账号类型 1运营 2商家 3其他第三方
	 */
	private Integer accountType;
	/**
	 * passpor账号跟account相同，erp账号为 account#ERP
	 */
	private String accountId;
	/**
	 * 表示当前账号最新选择的商家no
	 */
	private String sellerNo;
	/**
	 * 表示当前账号最新选择的商家name
	 */
	private String sellerName;
	/**
	 * 状态1 正常  0禁用
	 */
	private Integer status;
	/**
	 * 联系手机
	 */
	private String phone;
	/**
	 * 联系邮箱
	 */
	private String email;
	/**
	 * 0非全部数据权限 1全部数据权限
	 */
	private Integer alwaysAllPermission;
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
	 * 管理维度 1商家 2签约区域 3全部商家
	 */
	private Integer manageDimension;
	/**
	 * 签约区域,逗号分隔，详见签约区域枚举
	 */
	private String registerRegion;

}