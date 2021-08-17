package com.msc.fix.lisa.service;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.msc.fix.lisa.api.OssService;
import com.msc.fix.lisa.base.OssCallbackResult;
import com.msc.fix.lisa.base.OssPolicyResult;
import com.msc.fix.lisa.domain.entity.OssCallbackParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OssServiceImpl implements OssService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);
    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value(("${aliyun.oss.maxSize}"))
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    @Autowired
    private OSSClient ossClient;

    /**
     * 签名生成
     * @return
     */
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult ossPolicyResult = new OssPolicyResult();
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        int maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        // 回调
        OssCallbackParam ossCallbackParam = new OssCallbackParam();
        ossCallbackParam.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        ossCallbackParam.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        ossCallbackParam.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = "http://"+ ALIYUN_OSS_ENDPOINT;
        try {
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE,0,maxSize);
            policyConditions.addConditionItem(MatchMode.StartWith,PolicyConditions.COND_KEY,dir);
            String  postPolicy = ossClient.generatePostPolicy(expiration,policyConditions);
            byte[] binaryData = new byte[0];
            binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(JSONUtil.parse(ossCallbackParam).toString().getBytes("utf-8"));
            // 返回结果
            ossPolicyResult.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            ossPolicyResult.setPolicy(policy);
            ossPolicyResult.setSignature(signature);
            ossPolicyResult.setDir(dir);
            ossPolicyResult.setCallback(callbackData);
            ossPolicyResult.setHost(action);
        } catch (Exception e) {
           LOGGER.error("签名生成失败",e);
        }
        return ossPolicyResult;
    }

    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename ="http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
        ossCallbackResult.setFilename(filename);
        ossCallbackResult.setSize(request.getParameter("size"));
        ossCallbackResult.setMimeType(request.getParameter("mimeType"));
        ossCallbackResult.setWidth(request.getParameter("width"));
        ossCallbackResult.setHeight(request.getParameter("height"));
        return ossCallbackResult;
    }
}