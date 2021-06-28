package com.msc.fix.lisa.repository.system;

import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.entity.system.SysMenu;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.ShiroGateway;
import com.msc.fix.lisa.dto.system.cto.SysUserTokenCo;
import com.msc.fix.lisa.repository.db.mapper.SysMenuMapper;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import com.msc.fix.lisa.repository.db.mapper.SysUserTokenMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroRepository implements ShiroGateway {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;






    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenu> sysMenus = sysMenuMapper.selectList(null);
            permsList = new ArrayList<>(sysMenus.size());
            for(SysMenu menu : sysMenus){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenCo queryByToken(String token) {
        return sysUserTokenMapper.queryByToken(token);
    }

    @Override
    public SysUser queryUser(Long userId) {
        return sysUserMapper.selectById(userId);
    }
}
