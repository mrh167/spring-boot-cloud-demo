package com.xiaoma.email.common.utils;

import com.xiaoma.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class EmailUtil{
 
//    @Autowired
//    private EasyExcelUtil excelFactory;
 
    @Autowired
    private EmailService emailService = new EmailService();
 
    @Resource
    private ThreadPoolTaskExecutor threadPool;
 
    /**
     * @Author      xuhongchang
     * @Date        2021/3/5  10:47 上午
     * @Describetion 发送邮件的入口方法
     */
//    public void sendEmail(GenerateExcelSendEmailVo vo) throws Exception {
//        Long nanoTime = System.nanoTime();
//        log.info("####开始发送邮件#### : {} -- {}", vo, nanoTime);
////        threadPool.execute(() -> {
//            generateExcelSendEmail(vo.getDataList(), vo.getTableHeadList(), vo.getAcceptAddressList(), vo.getEmailTitle(), vo.getEmailContent());
//            log.info("####发送邮件结束#### : {}", nanoTime);
////        });
//
//    }
 
    /**
     * @Author xuhongchang
     * @Date 2021/3/4  11:26 上午
     * @Describetion 生成excel并发送邮件
     */
//    public void generateExcelSendEmail( List<T> dataList, List<SellerExcelVo> tableHeadList, List<String> acceptAddressList, String emailTitle, String content) throws Exception {
////        try {
////            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            String fileName = "email_" + new Random().nextInt(10000) + System.currentTimeMillis() + ".xlsx";
//        OutputStream out = new FileOutputStream(fileName);
//
//            //生成excel
//            EasyExcelUtil.writeExcelWithModel(out, dataList, tableHeadList,"待审核商家",new CommonCellWriterHandler(null));
//            InputStream inputStream = null;
//            // 发送邮件
//            emailService.sendMsgFileDs(acceptAddressList, emailTitle, content, fileName, new FileInputStream(fileName));
////        } catch (Exception e) {
////            log.error("发送邮件时报错：{}", e);
////        }
//    }
 
}