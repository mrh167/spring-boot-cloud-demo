package com.msc.fix.lisa.domain.gateway.system;

import com.alibaba.cola.dto.SingleResponse;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/18
 * Time: 15:38
 * Description: No Description
 */
public interface SysUserTokenGateway {
    SingleResponse createToken(Long userId);

    void logouts(Long userId);
}
