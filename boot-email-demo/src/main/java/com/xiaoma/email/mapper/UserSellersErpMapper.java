package com.xiaoma.email.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoma.email.entity.UserSellersErpEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSellersErpMapper extends BaseMapper<UserSellersErpEntity> {

    Integer totalCount();

    List<UserSellersErpEntity> listAll(Map<String, Object> pageParams);

    List<UserSellersErpEntity> getSellerNo(String accountId);

    List<UserSellersErpEntity> selectSellerNoListByAccounts(List<String> accountIds);
}