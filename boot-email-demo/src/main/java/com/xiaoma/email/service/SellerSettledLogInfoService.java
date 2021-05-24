package com.xiaoma.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;

import java.util.List;

public interface SellerSettledLogInfoService extends IService<SellerSettledLogInfoEntity> {


    List<SellerSettledLogInfoEntity> listAccountApp(List<String> sellerNo);


    List<SellerSettledLogInfoEntity> listPendingSellers(List<UserSellersErpEntity> value);
}