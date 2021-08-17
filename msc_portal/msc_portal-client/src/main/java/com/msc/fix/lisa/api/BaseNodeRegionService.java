package com.msc.fix.lisa.api;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.base.PageResponse;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import com.msc.fix.lisa.dto.ImageUploadCmd;
import com.msc.fix.lisa.dto.system.cto.BaseNodeRegionCo;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 15:50
 * Description: No Description
 */
public interface BaseNodeRegionService {

    PageResponse<BaseNodeRegionCo> queryPage(BaseNodeRegionPageQry baseNodeRegionPageQry);

    SingleResponse upload(ImageUploadCmd file);
}
