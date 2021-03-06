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
    private final String subject = "????????????????????????????????????";




    public void sendMail() {
        /**
         * ?????????????????????
         */
        log.info("MailScheduleTask.sendMail start");
        Map<String, Object> pageParams = new HashMap<>();
        pageParams.put("id", 0);
        pageParams.put("pageSize", pageSize);
        pageParams.put("manageDimension", ManageDimensionEnum.SALES.getCode());

        //????????????????????????????????????
        Integer totalSize = baseUserInfoMapper.totalCountDimension(ManageDimensionEnum.SALES.getCode());
        log.info("??????erp??????-??????user - total size: ???{}???", totalSize);
        if (totalSize > 0) {
            int pages = (int) (totalSize / pageSize + ((totalSize % pageSize == 0) ? 0 : 1));
            for (int i = 0; i < pages; i++) {
                //???????????????????????????????????????
                List<BaseUserInfoEntity> userList = baseUserInfoMapper.taskPageManageDimension(pageParams);
                log.info("user - userList size: ???{}???, pages = {}", userList.size(), i + 1);
                //??????????????????????????????????????????????????????seller_settled_user_info???seller_settled_log_info??????????????????????????????????????????
                if (CollUtil.isNotEmpty(userList)) {
                    //??????????????????erp??????
                    List<String> accountIds = userList.stream().map(BaseUserInfoEntity::getAccountId).distinct().collect(Collectors.toList());
                    //??????userinfo???erp????????????sellerErp????????????????????????????????????
                    List<UserSellersErpEntity> erpList = userSellersErpMapper.selectSellerNoListByAccounts(accountIds);
                    log.info("user - erpList size: ???{}???", erpList.size());
                    /**
                     * ????????????
                     * key???????????????    value:??????????????????
                     */
                    Map<String, List<UserSellersErpEntity>> erpMap = erpList.stream().distinct().collect(Collectors.groupingBy(UserSellersErpEntity::getAccountId));
                    erpMap.forEach((accountId, sellerList) -> {
                        try {
                        List<SellerExcelVo> settledLogInfo = settledLogInfoService.listPendingSellers(sellerList);
                        List<SellerExcelVo> accountsInfo  = settledUserInfoService.listPendingAccounts(sellerList);
                        String emails = baseUserInfoMapper.getEmail(accountId);
                        generateExcelSendEmail(settledLogInfo,accountsInfo,emails);
                        //??????50??????
                       } catch (Exception e){
                           log.error("???????????????????????????{}",e);
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
            //??????excel
            EasyExcelUtil.writeExcelWithModel(out, dataList,accountsInfo, SellerExcelVo.class);
            buildMailConpent(emails,dataList, accountsInfo,new File(fileName).getPath());
            FileUtils.delAllFile(filePath);
        } catch (Exception e) {
            log.error("????????????????????????{}", e);
        }
    }





    private void buildMailConpent(String emails, List<SellerExcelVo> settledLogInfo, List<SellerExcelVo> accountsInfo, String inputStream){
        Session session = assembleSession();
        String[] toAddresses = emails.split(",");
        log.info("????????????{}", JSON.toJSONString(toAddresses));

        // ??????????????????
        Message msg = new MimeMessage(session);
        try {
            //??????????????????????????????MimeBodyPart??????
            Multipart mp = new MimeMultipart();
            //MimeBodyPart????????????????????????????????????
            MimeBodyPart body = new MimeBodyPart();
            String msgContent = htmlMail(settledLogInfo,accountsInfo);
            //HTML??????
            body.setContent(msgContent, "text/html; charset=utf-8");
            mp.addBodyPart(body);
            msg.setSubject(MimeUtility.encodeText(subject,"utf-8",null));

            //????????????&??????
            body = new MimeBodyPart();
            body.attachFile(inputStream);
            mp.addBodyPart(body);
            log.info("?????????????????????{}", msgContent);
//            msg.setContent(msgContent, mailProperties.getContentPart());// ????????????????????????html??????
            //??????????????????
            msg.setContent(mp);
            // ???????????????
            msg.setFrom(new InternetAddress(mailProperties.getFrom()));// ??????????????????
            Transport transport = session.getTransport();
            // ?????????????????????
            transport.connect(mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), mailProperties.getPassword());
            //?????????
            ArrayList<Address> addresses = Lists.newArrayList();
            for (String address : toAddresses) {
                addresses.add(new InternetAddress(address));
            }
            //??????????????????
            //mimeMessage.setText(content);
            msg.saveChanges();
            /* ???????????? */
            transport.sendMessage(msg, addresses.toArray(new Address[addresses.size()]));
            // ????????????
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //?????????????????????
    private Session assembleSession(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.auth", mailProperties.isAuth());
        properties.put("mail.smtp.port", mailProperties.getPort());
        properties.put("mail.sender.username", mailProperties.getUsername());
        properties.put("mail.sender.password", mailProperties.getPassword());
        properties.put("mail.transport.protocol", mailProperties.getProtocol());
        log.info("properties???????????????:{}", JSON.toJSONString(properties));
        Session session = Session.getInstance(properties);
        return session;
    }

    private String htmlMail(List<SellerExcelVo> entitys, List<SellerExcelVo> accountsInfo) {
        StringBuilder mailText = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mailText.append("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????").
                append("<a href=").append("https://ssa.jd.com/sso/login?ReturnUrl=http://jh-pre2.jdl.cn/#/merchants").append(">").append("????????????????????????????????????????????????").append("</a>");
        mailText.append("<table style='text-align: center;border-collapse:collapse;' border='1' cellpadding='0' cellspacing='0'>");
        mailText.append("<tr bgcolor='#D9F0FE'><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>??????</span></b></td><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>????????????</span></b></td><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>????????????(ECP)</span></b></td><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>????????????</span></b></td><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>?????????(pin)</span></b></td><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>????????????</span></b></td><td><b><span style='font-size:9.0pt;font-family:'????????????',sans-serif;color:white'>????????????</span></b></td><tr>");
        for (int i = 0; i < entitys.size(); i++) {
            SellerExcelVo entity = entitys.get(i);
            String sellerName = entity.getSellerName();
            String sellerNo = entity.getSellerNo();
            //????????????
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
            //????????????
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
