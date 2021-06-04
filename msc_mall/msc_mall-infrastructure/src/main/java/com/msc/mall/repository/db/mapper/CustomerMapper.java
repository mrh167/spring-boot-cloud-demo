package com.msc.mall.repository.db.mapper;

import com.msc.mall.repository.db.doo.CustomerDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper{

  public CustomerDO getById(String customerId);
}
