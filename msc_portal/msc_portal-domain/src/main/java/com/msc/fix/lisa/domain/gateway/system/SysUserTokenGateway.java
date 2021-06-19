package com.msc.fix.lisa.domain.gateway.system;

import com.msc.fix.lisa.common.R;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/18
 * Time: 15:38
 * Description: No Description
 */
public interface SysUserTokenGateway {
    R createToken(Long userId);

    void logouts(Long userId);
}
