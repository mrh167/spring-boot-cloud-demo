package com.msc.fix.lisa.mybatiscn.strategy;

public class MapperXmlStrategy {


    /**
     * 是否生成baseResultMap
     */
    private boolean baseResultMap = false;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache = false;

    public boolean isBaseResultMap() {
        return baseResultMap;
    }

    public void setBaseResultMap(boolean baseResultMap) {
        this.baseResultMap = baseResultMap;
    }

    public boolean isEnableCache() {
        return enableCache;
    }

    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }
}
