package com.msc.fix.lisa.api;

import com.msc.fix.lisa.base.OssCallbackResult;
import com.msc.fix.lisa.base.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
    /**
     * oss 上传生成策略
     * @return
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     * @param request
     * @return
     */
    OssCallbackResult callback(HttpServletRequest request);
}