package com.xiaoma.email.task;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoma.email.common.enums.ManageDimensionEnum;
import com.xiaoma.email.common.utils.EasyExcelUtil;
import com.xiaoma.email.common.utils.EmailUtil;
import com.xiaoma.email.common.utils.FileUtils;
import com.xiaoma.email.common.utils.api.IFileManagerService;
import com.xiaoma.email.config.MailProperties;
import com.xiaoma.email.entity.BaseUserInfoEntity;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.entity.excelvo.SellerExcelVo;
import com.xiaoma.email.mapper.BaseUserInfoMapper;
import com.xiaoma.email.mapper.UserSellersErpMapper;
import com.xiaoma.email.service.EmailService;
import com.xiaoma.email.service.SellerSettledLogInfoService;
import com.xiaoma.email.service.SellerSettledUserInfoService;
import com.xiaoma.email.service.UserSellersErpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateEngine;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/5/28
 * Time: 17:57
 * Description: No Description
 */
@Slf4j
public class SendEmailTask {
    EmailUtil emailUtil = new EmailUtil();
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private UserSellersErpService userSellersErpService;
    @Autowired
    private UserSellersErpMapper userSellersErpMapper;
    @Autowired
    private SellerSettledLogInfoService settledLogInfoService;
    @Autowired
    private SellerSettledUserInfoService settledUserInfoService;
    @Autowired
    private BaseUserInfoMapper baseUserInfoMapper;
    @Autowired
    private IFileManagerService iFileManagerService;
    @Value("${sync.user.erp.task.pageSize:20}")
    private Integer pageSize;
    @Value("${project.tmp.files.path}")
    private String filePath;
    private final String subject = "京慧系统客户开通申请提醒";




    public void sendMail() {
        /**
         * 催办邮件配置类
         */
        log.info("MailScheduleTask.sendMail start");
        Map<String, Object> pageParams = new HashMap<>();
        pageParams.put("id", 0);
        pageParams.put("pageSize", pageSize);
        pageParams.put("manageDimension", ManageDimensionEnum.SALES.getCode());

        //统计运营登录的销售的数量
        Integer totalSize = baseUserInfoMapper.totalCountDimension(ManageDimensionEnum.SALES.getCode());
        log.info("销售erp中的-总数user - total size: 【{}】", totalSize);
        if (totalSize > 0) {
            int pages = (int) (totalSize / pageSize + ((totalSize % pageSize == 0) ? 0 : 1));
            for (int i = 0; i < pages; i++) {
                //分页查询销售维度的商家信息
                List<BaseUserInfoEntity> userList = baseUserInfoMapper.taskPageManageDimension(pageParams);
                log.info("user - userList size: 【{}】, pages = {}", userList.size(), i + 1);
                //判断是否有销售维度的商家，如果有就去seller_settled_user_info和seller_settled_log_info查找该销售下的所有待审核商家
                if (CollUtil.isNotEmpty(userList)) {
                    //获取所有销售erp账号
                    List<String> accountIds = userList.stream().map(BaseUserInfoEntity::getAccountId).distinct().collect(Collectors.toList());
                    //通过userinfo的erp账号查询sellerErp的所有商家编码的商家信息
                    List<UserSellersErpEntity> erpList = userSellersErpMapper.selectSellerNoListByAccounts(accountIds);
                    log.info("user - erpList size: 【{}】", erpList.size());
                    /**
                     * 数据样式
                     * key：销售账号    value:所有商家编号
                     */
                    Map<String, List<UserSellersErpEntity>> erpMap = erpList.stream().distinct().collect(Collectors.groupingBy(UserSellersErpEntity::getAccountId));
                    erpMap.forEach((accountId, sellerList) -> {
                        try {
                        List<SellerExcelVo> settledLogInfo = settledLogInfoService.listPendingSellers(sellerList);
                        List<SellerExcelVo> accountsInfo  = settledUserInfoService.listPendingAccounts(sellerList);
                        String emails = baseUserInfoMapper.getEmail(accountId);
                        generateExcelSendEmail(settledLogInfo,accountsInfo,emails);
                        //睡眠50毫秒
                       } catch (Exception e){
                           log.error("邮件数据查询异常：{}",e);
                        }
                    });
                    BaseUserInfoEntity lastUser = userList.get(userList.size() - 1);
                    pageParams.put("id", lastUser.getId());
                }
            }
        }
    }

    private void generateExcelSendEmail(List<SellerExcelVo> dataList,List<SellerExcelVo> accountsInfo, String emails) {
        try {
            String fileName =filePath+ "email_" + new Random().nextInt(10000) + System.currentTimeMillis() + ".xlsx";
            File f2=new File(filePath);
            if(!f2.exists())
            {
                f2.mkdirs();
            }
            OutputStream out = new FileOutputStream(fileName);
            //生成excel
            EasyExcelUtil.writeExcelWithModel(out, dataList,accountsInfo, SellerExcelVo.class);
            buildMailConpent(emails,dataList, accountsInfo,new File(fileName).getPath());
            FileUtils.delAllFile(filePath);
        } catch (Exception e) {
            log.error("发送邮件时报错：{}", e);
        }
    }





    private void buildMailConpent(String emails, List<SellerExcelVo> settledLogInfo, List<SellerExcelVo> accountsInfo, String inputStream){
        Session session = assembleSession();
        String[] toAddresses = emails.split(",");
        log.info("收件人：{}", JSON.toJSONString(toAddresses));

        // 创建邮件对象
        Message msg = new MimeMessage(session);
        try {
            //容器类，可以包含多个MimeBodyPart对象
            Multipart mp = new MimeMultipart();
            //MimeBodyPart可以包装文本，图片，附件
            MimeBodyPart body = new MimeBodyPart();
            String msgContent = htmlMail(settledLogInfo,accountsInfo);
            //HTML正文
            body.setContent(msgContent, "text/html; charset=utf-8");
            mp.addBodyPart(body);
            msg.setSubject(MimeUtility.encodeText(subject,"utf-8",null));

            //添加图片&附件
            body = new MimeBodyPart();
            body.attachFile(inputStream);
            mp.addBodyPart(body);
            log.info("发送邮件内容：{}", msgContent);
//            msg.setContent(msgContent, mailProperties.getContentPart());// 设置邮件内容，为html格式
            //设置附件内容
            msg.setContent(mp);
            // 设置发件人
            msg.setFrom(new InternetAddress(mailProperties.getFrom()));// 设置邮件来源
            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), mailProperties.getPassword());
            //收件人
            ArrayList<Address> addresses = Lists.newArrayList();
            for (String address : toAddresses) {
                addresses.add(new InternetAddress(address));
            }
            //仅仅发送文本
            //mimeMessage.setText(content);
            msg.saveChanges();
            /* 发送邮件 */
            transport.sendMessage(msg, addresses.toArray(new Address[addresses.size()]));
            // 关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //初始化邮件参数
    private Session assembleSession(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.auth", mailProperties.isAuth());
        properties.put("mail.smtp.port", mailProperties.getPort());
        properties.put("mail.sender.username", mailProperties.getUsername());
        properties.put("mail.sender.password", mailProperties.getPassword());
        properties.put("mail.transport.protocol", mailProperties.getProtocol());
        log.info("properties配置信息为:{}", JSON.toJSONString(properties));
        Session session = Session.getInstance(properties);
        return session;
    }

    private String htmlMail(List<SellerExcelVo> entitys, List<SellerExcelVo> accountsInfo) {
        StringBuilder mailText = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mailText.append("京慧系统商家及账号开通申请提醒信息如下，您可通过下载附件查看，或直接访问网址：").
                append("<a href=").append("https://ssa.jd.com/sso/login?ReturnUrl=http://jh-pre2.jdl.cn/#/merchants").append(">").append("【京慧商家开通管理界面网址链接】").append("</a>");
        mailText.append("<table style='text-align: center;border-collapse:collapse;' border='1' cellpadding='0' cellspacing='0'>");
        mailText.append("<tr bgcolor='#D9F0FE'><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>序号</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>商家名称</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>商家编码(ECP)</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>签约区域</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>申请人(pin)</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>申请时间</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>区域销售</span></b></td><tr>");
        for (int i = 0; i < entitys.size(); i++) {
            SellerExcelVo entity = entitys.get(i);
            String sellerName = entity.getSellerName();
            String sellerNo = entity.getSellerNo();
            //签约区域
            String registerRegion = entity.getRegisterRegion();
            String applyAccount = entity.getApplyAccount();
            String applyTime = "";
            if (entity.getApplyTime() != null) {
                applyTime = sdf.format(entity.getApplyTime());
            }
            String regionAccount = entity.getRegionAccount();
            mailText.append("<tr style='background:#99CC00'><td>" + (i + 1) + "</td><td>" + sellerName + "</td><td>" + sellerNo + "</td><td>" + registerRegion + "</td><td>" + applyAccount + "</td><td>" + applyTime + "</td><td>"+regionAccount +"</td><tr>");
        }
        for (int j = 0; j < accountsInfo.size(); j++) {                                                                                                                                             //</td>"++"<td>
            SellerExcelVo sellerExcelVo = accountsInfo.get(j);
            //签约区域
            String registerRegion = sellerExcelVo.getRegisterRegion();
            String applyAccount = sellerExcelVo.getApplyAccount();
            String applyTime = "";
            if (sellerExcelVo.getApplyTime() != null) {
                applyTime = sdf.format(sellerExcelVo.getApplyTime());
            }
            String regionAccount = sellerExcelVo.getRegionAccount();

            mailText.append("<tr><td>" + (j + 1) + "</td><td>" + sellerExcelVo.getSellerName() + "</td><td>" + sellerExcelVo.getSellerNo() + "</td><td>" + registerRegion+"</td><td>" + applyAccount + "</td><td>" + applyTime + "</td><td>"+ regionAccount +"</td><tr>");
        }
        mailText.append("</table>");
        return mailText.toString();
    }
}
