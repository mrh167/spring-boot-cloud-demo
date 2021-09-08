package com.msc.fix.lisa.domain.entity.system;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author 马瑞华
 * @since 2021-08-31
 */
@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String account;

    /**
     * 角色编码
     */
    private String roleCode;



    /**
     * 账号类型编码
     */
    private String accTypeNo;

    /**
     * 状态: 0禁用, 1启用
     */
    private Integer status;

    /**
     * 描述说明
     */
    private String description;

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
