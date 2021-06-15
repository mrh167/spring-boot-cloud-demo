package com.xiaoma.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoma.email.entity.SellerSettledUserInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.entity.excelvo.SellerExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerSettledUserInfoMapper extends BaseMapper<SellerSettledUserInfoEntity> {


    List<SellerExcelVo> listPendingAccounts(@Param("sellerList") List<UserSellersErpEntity> sellerList);
}