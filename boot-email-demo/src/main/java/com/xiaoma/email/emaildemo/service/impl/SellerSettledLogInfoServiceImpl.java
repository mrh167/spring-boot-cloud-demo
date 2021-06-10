package com.xiaoma.email.emaildemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.email.emaildemo.common.enums.RegisterRegionEnum;
import com.xiaoma.email.emaildemo.entity.SellerSettledLogInfoEntity;
import com.xiaoma.email.emaildemo.entity.UserSellersErpEntity;
import com.xiaoma.email.emaildemo.entity.excelvo.SellerExcelVo;
import com.xiaoma.email.emaildemo.mapper.SellerSettledLogInfoMapper;
import com.xiaoma.email.emaildemo.service.SellerSettledLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sellerSettledLogInfoService")
public class SellerSettledLogInfoServiceImpl extends ServiceImpl<SellerSettledLogInfoMapper, SellerSettledLogInfoEntity> implements SellerSettledLogInfoService {


    @Autowired
    private SellerSettledLogInfoMapper settledLogInfoMapper;

    @Override
    public List<SellerSettledLogInfoEntity> listAccountApp(List<String> sellerNo) {
        return settledLogInfoMapper.listAccountApp(sellerNo);
    }

    @Override
    public List<SellerExcelVo> listPendingSellers(List<UserSellersErpEntity> sellerList) {
        List<SellerExcelVo> list = settledLogInfoMapper.listPendingSellers(sellerList);
        for (SellerExcelVo excelVo : list) {
            excelVo.setRegisterRegion(RegisterRegionEnum.parseRegisterRegion(excelVo.getRegisterRegion()));
        }
        return list;
    }
}