package com.msc.fix.lisa.domain.entity;

import lombok.Data;

@Data
public class OssCallbackParam {

    private String callbackUrl;

    private String callbackBody;

    private String callbackBodyType;
}