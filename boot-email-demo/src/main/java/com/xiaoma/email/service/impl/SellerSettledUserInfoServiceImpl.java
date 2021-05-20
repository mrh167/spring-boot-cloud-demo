package com.xiaoma.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.email.entity.SellerSettledUserInfoEntity;
import com.xiaoma.email.mapper.SellerSettledUserInfoMapper;
import com.xiaoma.email.service.SellerSettledUserInfoService;
import org.springframework.stereotype.Service;

@Service("sellerSettledUserInfoService")
public class SellerSettledUserInfoServiceImpl extends ServiceImpl<SellerSettledUserInfoMapper, SellerSettledUserInfoEntity> implements SellerSettledUserInfoService {


}