package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.dto.system.SysUserQry;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author 马瑞华
 * @since 2021-08-31
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUser(String username);

    List<SysUserQry> listPage(SysUserQry pageQry);
}
