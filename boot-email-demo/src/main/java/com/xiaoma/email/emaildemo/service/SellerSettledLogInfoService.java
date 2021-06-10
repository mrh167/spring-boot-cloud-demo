package com.xiaoma.email.emaildemo.service;


import com.xiaoma.email.emaildemo.entity.excelvo.SellerExcelVo;

import java.util.List;

public interface SellerSettledLogInfoService extends IService<SellerSettledLogInfoEntity> {


    List<SellerSettledLogInfoEntity> listAccountApp(List<String> sellerNo);


    List<SellerExcelVo> listPendingSellers(List<UserSellersErpEntity> sellerList);
}