package com.msc.fix.lisa.base;


import com.msc.fix.lisa.domain.po.BasePo;

import java.util.Date;

/**
 * 填充公共属性
 *
 * @author lzp
 * @version 1.0
 * @date 2021-03-01
 */
public class CommonUtil {

    public static BasePo fillByCreate(Date createTime, String createUser, BasePo basePo) {
        basePo.setUpdateTime(createTime);
        basePo.setCreateTime(createTime);
        basePo.setUpdateUser(createUser);
        basePo.setCreateUser(createUser);
        basePo.setYn(1);
        return basePo;
    }

    public static BasePo fillByUpdate(Date updateTime, String updateUser, BasePo basePo) {
        basePo.setUpdateTime(updateTime);
        basePo.setUpdateUser(updateUser);
        return basePo;
    }

    public static BasePo fillByDelete(Date updateTime, String updateUser, BasePo basePo) {
        basePo.setUpdateTime(updateTime);
        basePo.setUpdateUser(updateUser);
        basePo.setYn(0);
        return basePo;
    }
}
