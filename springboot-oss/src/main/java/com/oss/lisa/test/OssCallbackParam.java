package com.oss.lisa.test;

import lombok.Data;

@Data
public class OssCallbackParam {

    private String callbackUrl;

    private String callbackBody;

    private String callbackBodyType;
}