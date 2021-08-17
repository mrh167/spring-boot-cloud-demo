package com.msc.fix.lisa.service;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.api.BaseNodeRegionService;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import com.msc.fix.lisa.dto.ImageUploadCmd;
import com.msc.fix.lisa.dto.system.cto.BaseNodeRegionCo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 15:50
 * Description: No Description
 */
@Service
public class BaseNodeRegionServiceImpl implements BaseNodeRegionService {

    @Autowired
    private CommandBusI commandBusI;
    @Override
    public PageResponse<BaseNodeRegionCo> queryPage(BaseNodeRegionPageQry baseNodeRegionPageQry) {
        return (PageResponse<BaseNodeRegionCo>) commandBusI.send(baseNodeRegionPageQry);
    }

    @Override
    public SingleResponse upload(ImageUploadCmd file) {

        return (SingleResponse) commandBusI.send(file);
    }
}
