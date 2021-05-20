package com.xiaoma.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
import com.xiaoma.email.mapper.SellerSettledLogInfoMapper;
import com.xiaoma.email.service.SellerSettledLogInfoService;
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
}