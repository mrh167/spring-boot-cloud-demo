package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.dto.system.SysUserCo;
import com.msc.fix.lisa.repository.db.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:57
 * Description: No Description
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUserCo queryByUserName(String username);
}
