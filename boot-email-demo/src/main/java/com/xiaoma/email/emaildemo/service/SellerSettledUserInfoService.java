package com.xiaoma.email.emaildemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.email.emaildemo.entity.SellerSettledUserInfoEntity;
import com.xiaoma.email.emaildemo.entity.UserSellersErpEntity;
import com.xiaoma.email.emaildemo.entity.excelvo.SellerExcelVo;

import java.util.List;

public interface SellerSettledUserInfoService extends IService<SellerSettledUserInfoEntity> {


    List<SellerExcelVo> listPendingAccounts(List<UserSellersErpEntity> sellerList);
}