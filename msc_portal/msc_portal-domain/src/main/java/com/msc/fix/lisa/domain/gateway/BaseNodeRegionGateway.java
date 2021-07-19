package com.msc.fix.lisa.domain.gateway;

import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.BaseNodeRegionEntity;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 16:00
 * Description: No Description
 */
public interface BaseNodeRegionGateway {
    PageInfo<BaseNodeRegionEntity> listPage(BaseNodeRegionPageQry baseNodeRegionPageQry);
}
