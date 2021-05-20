package com.xiaoma.email.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 自主入驻用户表
 * 
 * @author maruihua
 * @email 1003534413@qq.com
 * @date 2021-05-20 18:19:54
 */
@Data
@TableName("portal_seller_settled_user_info")
public class SellerSettledUserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
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
	 * 申请账号
	 */
	private String applyAccount;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 审批人
	 */
	private String approver;
	/**
	 * 审批时间
	 */
	private Date approveTime;
	/**
	 * 状态: PENDING,OPEN,CLOSED
	 */
	private String approveStatus;
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