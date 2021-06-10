package com.xiaoma.email.emaildemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoma.email.emaildemo.entity.SellerSettledLogInfoEntity;
import com.xiaoma.email.emaildemo.entity.UserSellersErpEntity;
import com.xiaoma.email.emaildemo.entity.excelvo.SellerExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerSettledLogInfoMapper extends BaseMapper<SellerSettledLogInfoEntity> {


    List<SellerSettledLogInfoEntity> listAccountApp(@Param("sellerNo") List<String> sellerNo);

    List<SellerSettledLogInfoEntity> getSellerNo(String sellerNo);

    List<SellerExcelVo> listPendingSellers(@Param("sellerList") List<UserSellersErpEntity> sellerList);
}