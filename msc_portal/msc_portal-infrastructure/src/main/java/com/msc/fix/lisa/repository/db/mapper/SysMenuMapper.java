package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.system.SysMenu;
import com.msc.fix.lisa.domain.entity.system.SysUserToken;
import com.msc.fix.lisa.dto.system.cto.SysMenuCo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuCo> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuCo> queryNotButtonList();
}