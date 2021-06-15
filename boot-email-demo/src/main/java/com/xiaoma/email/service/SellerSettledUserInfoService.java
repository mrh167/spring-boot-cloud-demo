package com.xiaoma.email.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.email.entity.SellerSettledUserInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.entity.excelvo.SellerExcelVo;

import java.util.List;

public interface SellerSettledUserInfoService extends IService<SellerSettledUserInfoEntity> {


    List<SellerExcelVo> listPendingAccounts(List<UserSellersErpEntity> sellerList);
}