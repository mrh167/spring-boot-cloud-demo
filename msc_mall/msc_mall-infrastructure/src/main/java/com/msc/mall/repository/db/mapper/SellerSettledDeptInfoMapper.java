package com.msc.mall.repository.db.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msc.mall.base.BaseQuery;
import com.msc.mall.base.PageRequest;
import com.msc.mall.domain.deptinfo.SellerSettledDeptInfoEntity;
import com.msc.mall.repository.db.doo.SellerSettledDeptInfoDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商家入驻事业部详细表 Mapper
 *
 * @author maruihua
 * @version 1.0
 * @date 2021-06-04 18:28:53
 */
@Mapper
public interface SellerSettledDeptInfoMapper extends BaseMapper<SellerSettledDeptInfoDo> {
    /**
     * 分页查询
     *
     * @param pageQry 分页查询参数
     * @return {@link List<SellerSettledDeptInfoEntity>}
     */
    List<SellerSettledDeptInfoEntity> listPage(PageRequest pageQry);

    /**
     * 批量导出
     *
     * @param
     * @return {@link List<SellerSettledDeptInfoEntity>}
     */
    List<SellerSettledDeptInfoEntity> excelExport(BaseQuery exportCmd);





}
