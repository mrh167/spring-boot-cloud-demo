package com.xiaoma.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.mapper.UserSellersErpMapper;
import com.xiaoma.email.service.UserSellersErpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userSellersErpService")
public class UserSellersErpServiceImpl extends ServiceImpl<UserSellersErpMapper, UserSellersErpEntity> implements UserSellersErpService {

    @Autowired
    UserSellersErpMapper userSellersErpMapper;

    @Override
    public List<UserSellersErpEntity> listAll(Map<String, Object> pageParams) {
        return userSellersErpMapper.listAll(pageParams);
    }

    @Override
    public Integer totalCount() {
        return userSellersErpMapper.totalCount();
    }

    @Override
    public List<UserSellersErpEntity> getSellerNo(String accountId) {
        return userSellersErpMapper.getSellerNo(accountId);
    }


}