package com.msc.fix.lisa.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/31
 * Time: 20:25
 * Description: No Description
 */
@Data
public class SysUserCoooo {
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 角色编码
     */
    private String account;

    /**
     * 角色名称
     */
    private String username;

    /**
     * 账号类型编码
     */
    private String password;

    private String email;

    /**
     * 状态: 0禁用, 1启用
     */
    private String phone;

    /**
     * 描述说明
     */
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private String createUser;

    /**
     * 修改人
     */
    @ApiModelProperty(hidden = true)
    private String updateUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    @ApiModelProperty(hidden = true)
    private Integer yn;

//    @Getter(AccessLevel.NONE)
    private Boolean enabled;
}
