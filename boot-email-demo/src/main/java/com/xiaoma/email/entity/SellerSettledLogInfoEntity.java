package com.xiaoma.email.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商家入驻信息记录表
 * 
 * @author maruihua
 * @email 1003534413@qq.com
 * @date 2021-05-20 18:00:03
 */
@Data
@TableName("portal_seller_settled_log_info")
public class SellerSettledLogInfoEntity implements Serializable {
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
	 * 商家类型ECLP,OTHER
	 */
	private String sellerType;
	/**
	 * 集团销售,逗号分隔
	 */
	private String groupAccount;
	/**
	 * 区域销售,逗号分隔
	 */
	private String regionAccount;
	/**
	 * 青龙业主号
	 */
	private String bdOwnerNo;
	/**
	 * 签约区域,逗号分隔，详见签约区域枚举
	 */
	private String registerRegion;
	/**
	 * eclp商家类型FCS/FBP/SOP,逗号分隔
	 */
	private String eclpSellerType;
	/**
	 * C码,逗号分隔
	 */
	private String clientCode;
	/**
	 * 主合同编号,逗号分隔
	 */
	private String contractCode;
	/**
	 * 事业部编码,逗号分隔
	 */
	private String deptNo;
	/**
	 * 申请账号,逗号分隔
	 */
	private String applyAccount;
	/**
	 * 京慧开通时间
	 */
	private Date startTime;
	/**
	 * 京慧结束时间
	 */
	private Date endTime;
	/**
	 * 开通渠道 1 线上申请 2 签约开通 3 手动开通 
	 */
	private Integer openChannel;
	/**
	 * 开通状态 2 待审核 3已拒绝 4 已关闭 5使用中
	 */
	private Integer openStatus;
	/**
	 * 使用版本
	 */
	private String useVersion;
	/**
	 * 是否vsc运营 0否 1是
	 */
	private Integer vscFlag;
	/**
	 * 催办次数
	 */
	private Integer urgeCount;
	/**
	 * 最新催办时间
	 */
	private Date lastUrgeTime;
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
