package com.msc.fix.lisa.executor.query.system;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.QueryExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.domain.gateway.system.SysUserGateway;
import com.msc.fix.lisa.dto.system.SysUserCo;
import com.msc.fix.lisa.dto.system.UploadQry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/16
 * Time: 16:50
 * Description: No Description
 */
@Command
public class UploadQryExe implements QueryExecutorI<SingleResponse<List<SysUserCo>>, UploadQry> {

    @Autowired
    private SysUserGateway uploadGateway;

    @Override
    public SingleResponse<List<SysUserCo>> execute(UploadQry uploadQry) {
        return uploadGateway.list(uploadQry);
    }
}
