package com.msc.fix.lisa.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msc.fix.lisa.domain.entity.BaseNodeRegionEntity;
import com.msc.fix.lisa.domain.gateway.BaseNodeRegionGateway;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import com.msc.fix.lisa.repository.db.mapper.BaseNodeRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 16:01
 * Description: No Description
 */
@Component
public class BaseNodeRegionRepository implements BaseNodeRegionGateway {

    @Autowired
    private BaseNodeRegionMapper baseNodeRegionmapper;


    @Override
    public PageInfo<BaseNodeRegionEntity> listPage(BaseNodeRegionPageQry baseNodeRegionPageQry) {
        return PageHelper.startPage(baseNodeRegionPageQry.getPageNum(),baseNodeRegionPageQry.getPageSize(),true)
                .doSelectPageInfo(() -> baseNodeRegionmapper.listPage(baseNodeRegionPageQry));


    }
}
