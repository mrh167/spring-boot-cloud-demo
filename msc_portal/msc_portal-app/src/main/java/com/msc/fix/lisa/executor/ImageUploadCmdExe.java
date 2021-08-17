package com.msc.fix.lisa.executor;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.excel.util.StringUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.msc.fix.lisa.common.BusinessException;
import com.msc.fix.lisa.dto.ImageUploadCmd;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/8/3
 * Time: 16:21
 * Description: No Description
 */
@Command
public class ImageUploadCmdExe implements CommandExecutorI<SingleResponse, ImageUploadCmd> {


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
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${file.path}")
    private String filePath;


    @SneakyThrows
    @Override
    public SingleResponse execute(ImageUploadCmd cmd) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ALIYUN_OSS_ENDPOINT, accessKeyId, accessKeySecret);
        String filename = cmd.getFile().getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            throw new BusinessException("文件名未找到,请重新上传");
        }
        InputStream inputStream = new FileInputStream(filePath + filename);
        ossClient.putObject(bucketName, filename,inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();

        System.out.println("上传成功...");
        return SingleResponse.buildSuccess();
    }
}
