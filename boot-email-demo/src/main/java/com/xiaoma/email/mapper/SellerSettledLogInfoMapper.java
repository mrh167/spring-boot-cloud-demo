package com.xiaoma.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerSettledLogInfoMapper extends BaseMapper<SellerSettledLogInfoEntity> {


    List<SellerSettledLogInfoEntity> listAccountApp(@Param("sellerNo") List<String> sellerNo);
}