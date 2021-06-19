package com.msc.fix.lisa.dto.system.cto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SysMenuCo implements Serializable{

        private static final long serialVersionUID = 1L;

        /**
         * 菜单ID
         */
        @ApiModelProperty(value = "菜单主键")
        private Long menuId;

        /**
         * 父菜单ID，一级菜单为0
         */
        @ApiModelProperty(value = "一级菜单为0")
        private Long parentId;

        /**
         * 父菜单名称
         */
        @ApiModelProperty(value = "父菜单名称")
        private String parentName;

        /**
         * 菜单名称
         */
        @ApiModelProperty(value = "菜单名称")
        private String name;

        /**
         * 菜单URL
         */
        @ApiModelProperty(value = "菜单URL")
        private String url;

        /**
         * 授权(多个用逗号分隔，如：user:list,user:create)
         */
        @ApiModelProperty(value = "授权")
        private String perms;

        /**
         * 类型     0：目录   1：菜单   2：按钮
         */
        @ApiModelProperty(value = "类型")
        private Integer type;

        /**
         * 菜单图标
         */
        @ApiModelProperty(value = "菜单图标")
        private String icon;

        /**
         * 排序
         */
        @ApiModelProperty(value = "排序")
        private Integer orderNum;

        /**
         * ztree属性
         */
        @ApiModelProperty(value = "ztree属性")
//        @TableField(exist=false)
        private Boolean open;

//        @TableField(exist=false)
        @ApiModelProperty(hidden = true)
        private List<?> list;

}
