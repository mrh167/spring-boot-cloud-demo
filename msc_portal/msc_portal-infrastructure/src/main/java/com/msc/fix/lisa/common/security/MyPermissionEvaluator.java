package com.msc.fix.lisa.common.security;

import cn.hutool.core.collection.CollUtil;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.entity.system.SysUserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Configuration
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        SysUser principal = (SysUser) authentication.getPrincipal();
        List<SysUserRole> roleList = principal.getRoleList();
        if (CollUtil.isNotEmpty(roleList)) {
            //&&StringUtils.equals(x.getCode(), (CharSequence) permission)
            return roleList.stream().anyMatch(x -> StringUtils.equals(x.getRoleCode(), (CharSequence) targetDomainObject));
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
