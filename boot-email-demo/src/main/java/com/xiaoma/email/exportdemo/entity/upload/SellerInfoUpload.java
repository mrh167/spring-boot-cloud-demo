package com.xiaoma.email.exportdemo.entity.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/6/10
 * Time: 14:36
 * Description: No Description
 */
@Data
public class SellerInfoUpload {


    /**
     * 商户信息Excel
     */
    private MultipartFile sellerInfoFile;
}
