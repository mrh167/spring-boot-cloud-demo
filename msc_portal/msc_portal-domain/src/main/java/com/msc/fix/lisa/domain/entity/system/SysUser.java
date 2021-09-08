package com.msc.fix.lisa.domain.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.msc.fix.lisa.domain.common.utils.CustomAuthorityDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author 马瑞华
 * @since 2021-08-31
 */
@Data
@Table(name = "sys_user")
@Slf4j
public class SysUser implements Serializable,UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 角色编码
     */
    private String account;
    /**
     * 账号类型编码
     */
    private String password;

    /**
     * 角色名称
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
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

    /**
     * 逻辑删除字段
     */
    @ApiModelProperty(hidden = true)
    private Integer yn;

    @Getter(AccessLevel.NONE)
    private Integer enabled;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<SysUserRole> roleList;

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities =
                roleList.stream().map(role -> new SimpleGrantedAuthority(role.getRoleCode()))
                        .collect(Collectors.toList());
        log.info("auth ==>{}",authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
