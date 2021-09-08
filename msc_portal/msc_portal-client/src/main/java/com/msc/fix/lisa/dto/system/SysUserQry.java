package com.msc.fix.lisa.dto.system;

import com.msc.fix.lisa.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:22
 * Description: No Description
 */
@Data
public class SysUserQry extends PageRequest {

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String account;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 状态: 0禁用, 1启用
     */
    @ApiModelProperty(value = "状态: 0禁用, 1启用")
    private String phone;

    /**
     * 状态
     */
    @ApiModelProperty(value = "角色编码")
    private Integer status;
}
