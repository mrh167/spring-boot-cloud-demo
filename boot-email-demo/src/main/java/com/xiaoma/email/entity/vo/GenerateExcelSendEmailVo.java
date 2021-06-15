package com.xiaoma.email.entity.vo;

import com.xiaoma.email.entity.excelvo.SellerExcelVo;
import lombok.Data;

import java.io.OutputStream;
import java.util.List;

@Data
public class GenerateExcelSendEmailVo<T> {
 
    /**
     * 生成excel的数据
     */
    private List<T> dataList;
 
    /**
     * excel的表头
     */
    private List<SellerExcelVo> tableHeadList;
 
    /**
     * 邮件收件人邮箱，支持多个收件人邮箱
     */
    private List<String> acceptAddressList;
 
    /**
     * 邮件的标题
     */
    private String emailTitle;
 
    /**
     * 邮件内容
     */
    private String emailContent;

    private OutputStream outputStream;
}