package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:57
 * Description: No Description
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser queryByUserName(@Param("username") String username);



    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);



    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

}
