package com.msc.fix.lisa.repository.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.dto.system.SysUserCo;
import com.msc.fix.lisa.dto.system.UploadQry;
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
public class SysUserRepository implements SysUserGateway {


    @Autowired
    private SysUserMapper sysUserMapper;



    @Override
    public SingleResponse<List<SysUserCo>> list(UploadQry uploadQry) {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        List<SysUserCo> uploadCos = BeanUtils.convertList(sysUsers, SysUserCo.class);
        return SingleResponse.of(uploadCos);
    }

    @Override
    public SysUserCo queryByUserName(String username) {
        return sysUserMapper.queryByUserName(username);
    }
}
