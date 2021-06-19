package com.msc.fix.lisa.repository.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.dto.system.cto.SysUserCo;
import com.msc.fix.lisa.repository.db.SysUser;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:55
 * Description: No Description
 */
@Component
public class SysUserRepository extends ServiceImpl<SysUserMapper, SysUser> implements SysUserGateway {


    @Autowired
    private SysUserMapper sysUserMapper;



    @Override
    public SysUserCo queryByUserName(String username) {
        return sysUserMapper.queryByUserName(username);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }
}
