package com.xiaoma.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.email.entity.SellerSettledUserInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.mapper.SellerSettledUserInfoMapper;
import com.xiaoma.email.service.SellerSettledUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sellerSettledUserInfoService")
public class SellerSettledUserInfoServiceImpl extends ServiceImpl<SellerSettledUserInfoMapper, SellerSettledUserInfoEntity> implements SellerSettledUserInfoService {

    @Autowired
    private SellerSettledUserInfoMapper userInfoMapper;

    @Override
    public List<SellerSettledUserInfoEntity> listPendingAccounts(List<UserSellersErpEntity> sellerList) {
        return userInfoMapper.listPendingAccounts(sellerList);
    }
}