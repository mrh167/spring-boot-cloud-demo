package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author 马瑞华
 * @since 2021-08-31
 */
public interface SysRoleMapper extends BaseMapper<SysUserRole> {

    List<SysUserRole> getRoles(Long id);
}
