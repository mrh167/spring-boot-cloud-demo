package com.msc.fix.lisa.dto.system.cto;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class SysUserRoleCo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
