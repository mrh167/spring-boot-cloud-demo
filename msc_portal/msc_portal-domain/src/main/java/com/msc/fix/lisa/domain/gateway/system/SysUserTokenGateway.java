package com.msc.fix.lisa.domain.gateway.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msc.fix.lisa.common.R;
import com.msc.fix.lisa.domain.entity.system.SysUserToken;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/18
 * Time: 15:38
 * Description: No Description
 */
public interface SysUserTokenGateway extends IService<SysUserToken> {
    R createToken(Long userId);

    void logouts(Long userId);
}
