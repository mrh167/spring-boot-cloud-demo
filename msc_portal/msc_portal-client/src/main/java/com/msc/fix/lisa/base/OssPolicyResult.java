package com.msc.fix.lisa.base;

import lombok.Data;

@Data
public class OssPolicyResult {

    private String accessKeyId;

    private String policy;

    private String signature;

    private String dir;

    private String host;

    private String callback;
}