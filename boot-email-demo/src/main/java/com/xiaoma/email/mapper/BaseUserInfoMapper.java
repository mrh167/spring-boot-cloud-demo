package com.xiaoma.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoma.email.entity.BaseUserInfoEntity;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseUserInfoMapper extends BaseMapper<BaseUserInfoMapper> {

//    List<BaseUserInfoEntity> listAll(Map<String, Object> pageParams);


    List<BaseUserInfoEntity> getAccountId(String sellerNo);

    Integer totalCountDimension(@Param("countDimension") int countDimension);

    List<BaseUserInfoEntity> taskPageManageDimension(Map<String, Object> pageParams);

    String getEmail(@Param("accountId") String accountId);
}