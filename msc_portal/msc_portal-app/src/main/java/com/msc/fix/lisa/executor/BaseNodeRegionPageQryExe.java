package com.msc.fix.lisa.executor;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.QueryExecutorI;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.domain.entity.BaseNodeRegionEntity;
import com.msc.fix.lisa.domain.gateway.BaseNodeRegionGateway;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import com.msc.fix.lisa.dto.system.cto.BaseNodeRegionCo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 15:56
 * Description: No Description
 */
@Command
public class BaseNodeRegionPageQryExe implements QueryExecutorI<PageResponse<BaseNodeRegionCo>, BaseNodeRegionPageQry> {

    @Autowired
    private BaseNodeRegionGateway baseNodeRegionGateway;



    @Override
    public PageResponse<BaseNodeRegionCo> execute(BaseNodeRegionPageQry baseNodeRegionPageQry) {
        PageInfo<BaseNodeRegionEntity> pageInfo = baseNodeRegionGateway.listPage(baseNodeRegionPageQry);
        return PageResponse.buildPageSuccess(pageInfo,BaseNodeRegionCo.class);
    }
}
