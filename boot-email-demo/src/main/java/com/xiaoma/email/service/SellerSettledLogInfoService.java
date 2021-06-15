package com.xiaoma.email.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.entity.excelvo.SellerExcelVo;

import java.util.List;

public interface SellerSettledLogInfoService extends IService<SellerSettledLogInfoEntity> {


    List<SellerSettledLogInfoEntity> listAccountApp(List<String> sellerNo);


    List<SellerExcelVo> listPendingSellers(List<UserSellersErpEntity> sellerList);
}