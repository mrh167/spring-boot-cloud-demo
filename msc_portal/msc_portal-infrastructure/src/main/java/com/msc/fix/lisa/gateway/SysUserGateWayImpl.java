package com.msc.fix.lisa.gateway;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.YnValueEnum;
import com.msc.fix.lisa.domain.entity.system.SysUser;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateWay;
import com.msc.fix.lisa.dto.system.SysUserQry;
import com.msc.fix.lisa.dto.system.UpdateStatusCmd;
import com.msc.fix.lisa.repository.db.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/9/6
 * Time: 14:47
 * Description: No Description
 */
@Component
public class SysUserGateWayImpl implements SysUserGateWay {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public PageInfo<SysUser> pageList(SysUserQry pageQry) {
        return PageHelper.startPage(pageQry.getPageNum(), pageQry.getPageSize(), true)
                .doSelectPageInfo(() -> sysUserMapper.listPage(pageQry));
    }

    @Override
    public SingleResponse updateStatus(UpdateStatusCmd cmd) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        SysUser user = new SysUser();
        user.setStatus(cmd.getStatus());
        wrapper.eq("id",cmd.getId()).eq("yn", YnValueEnum.getYesCode());
        sysUserMapper.update(user,wrapper);
        return SingleResponse.buildSuccess();
    }
}
