package com.msc.fix.lisa.repository.db.dbdo;

import com.msc.fix.lisa.domain.po.BasePo;
import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/27
 * Time: 16:15
 * Description: No Description
 */
@Data
@Table(name = "sys_user")
public class SysUserDo extends BasePo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name ="username")
    private String username;

    /**
     * 密码
     */
    @Column(name ="password")
    private String password;

    /**
     * 邮箱
     */
    @Column(name ="email")
    private String email;

    /**
     * 手机号
     */
    @Column(name ="mobile")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @Column(name ="status")
    private Integer status;
}
