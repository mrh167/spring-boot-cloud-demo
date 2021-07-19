package com.msc.fix.lisa.repository.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.fix.lisa.domain.entity.BaseNodeRegionEntity;
import com.msc.fix.lisa.dto.BaseNodeRegionPageQry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/7/19
 * Time: 16:02
 * Description: No Description
 */
@Mapper
public interface BaseNodeRegionMapper extends BaseMapper<BaseNodeRegionEntity> {

    List<BaseNodeRegionEntity> listPage(BaseNodeRegionPageQry baseNodeRegionPageQry);
}
