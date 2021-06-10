package com.xiaoma.email.emaildemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.email.emaildemo.common.enums.RegisterRegionEnum;
import com.xiaoma.email.emaildemo.entity.SellerSettledUserInfoEntity;
import com.xiaoma.email.emaildemo.entity.UserSellersErpEntity;
import com.xiaoma.email.emaildemo.entity.excelvo.SellerExcelVo;
import com.xiaoma.email.emaildemo.mapper.SellerSettledUserInfoMapper;
import com.xiaoma.email.emaildemo.service.SellerSettledUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sellerSettledUserInfoService")
public class SellerSettledUserInfoServiceImpl extends ServiceImpl<SellerSettledUserInfoMapper, SellerSettledUserInfoEntity> implements SellerSettledUserInfoService {

    @Autowired
    private SellerSettledUserInfoMapper userInfoMapper;

    @Override
    public List<SellerExcelVo> listPendingAccounts(List<UserSellersErpEntity> sellerList) {
        List<SellerExcelVo> list = userInfoMapper.listPendingAccounts(sellerList);
        for (SellerExcelVo excelVo : list) {
            excelVo.setRegisterRegion(RegisterRegionEnum.parseRegisterRegion(excelVo.getRegisterRegion()));
        }
        return list;
    }
}