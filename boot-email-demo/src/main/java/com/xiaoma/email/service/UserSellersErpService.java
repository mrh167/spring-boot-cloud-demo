package com.xiaoma.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.email.entity.UserSellersErpEntity;

import java.util.List;
import java.util.Map;

public interface UserSellersErpService extends IService<UserSellersErpEntity> {


    List<UserSellersErpEntity> listAll(Map<String, Object> pageParams);

    Integer totalCount();

    List<UserSellersErpEntity> getSellerNo(String accountId);
}