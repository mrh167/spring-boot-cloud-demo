package com.msc.fix.lisa.domain.gateway.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.dto.system.SysUserCo;
import com.msc.fix.lisa.dto.system.UploadQry;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:53
 * Description: No Description
 */
public interface SysUserGateway {

    SingleResponse<List<SysUserCo>> list(UploadQry uploadQry);

    SysUserCo queryByUserName(String username);

}
