package com.msc.fix.lisa.strategy;

import com.baomidou.mybatisplus.generator.config.ConstVal;

public class MapperStrategy {

    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass = ConstVal.SUPER_MAPPER_CLASS;

    public String getSuperMapperClass() {
        return superMapperClass;
    }

    public void setSuperMapperClass(String superMapperClass) {
        this.superMapperClass = superMapperClass;
    }
}
