package com.xiaoma.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerSettledLogInfoMapper extends BaseMapper<SellerSettledLogInfoEntity> {


    List<SellerSettledLogInfoEntity> listAccountApp(@Param("sellerNo") List<String> sellerNo);

    List<SellerSettledLogInfoEntity> getSellerNo(String sellerNo);

    List<SellerSettledLogInfoEntity> listPendingSellers(@Param("sellerList") List<UserSellersErpEntity> sellerList);
}