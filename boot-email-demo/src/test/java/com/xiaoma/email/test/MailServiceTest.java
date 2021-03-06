package com.xiaoma.email.test;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoma.email.SpringBootDemoEmailApplicationTests;
import com.xiaoma.email.common.enums.ManageDimensionEnum;
import com.xiaoma.email.common.utils.EasyExcelUtil;
import com.xiaoma.email.common.utils.EmailUtil;
import com.xiaoma.email.common.utils.api.IFileManagerService;
import com.xiaoma.email.config.MailProperties;
import com.xiaoma.email.entity.BaseUserInfoEntity;
//import com.xiaoma.email.service.MailService;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.entity.excelvo.SellerExcelVo;
import com.xiaoma.email.mapper.BaseUserInfoMapper;
import com.xiaoma.email.mapper.UserSellersErpMapper;
import com.xiaoma.email.service.EmailService;
import com.xiaoma.email.service.SellerSettledLogInfoService;
import com.xiaoma.email.service.SellerSettledUserInfoService;
import com.xiaoma.email.service.UserSellersErpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.thymeleaf.TemplateEngine;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
//@EnableScheduling
@Slf4j
@PropertySource("classpath:application.yml")
public class MailServiceTest extends SpringBootDemoEmailApplicationTests {

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






    List<SellerExcelVo> settledLogInfo = null;
    List<SellerExcelVo> accountsInfo = null;
    String emails = "";



    @Test
    public void sendMail() {
        /**
         * ?????????????????????
         */
        log.info("MailScheduleTask.sendMail start");

//        List<UrgeLogInfoCo> list = new ArrayList<>();
//        list.add( new UrgeLogInfoCo(1L,"322332323","??????",1,new Date(),"??????"));
//        list.add( new UrgeLogInfoCo(2L,"5746465466","??????",1,new Date(),"??????"));

        //pageCount = (totalCount -1) / pageSize + 1
        //pageCount = (totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1)
        //pageCount = (totalCount + pageSize -1) / pageSize




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




//                        Map<String, UserSellersErpDo> erpMap = erpList.stream().collect(Collectors.);
                    /**
                     * ????????????
                     * key???????????????    value:??????????????????
                     */
                    Map<String, List<UserSellersErpEntity>> erpMap = erpList.stream().distinct().collect(Collectors.groupingBy(UserSellersErpEntity::getAccountId));
//


                    erpMap.forEach((accountId, sellerList) -> {
//                        try {

                            settledLogInfo = settledLogInfoService.listPendingSellers(sellerList);

                            accountsInfo = settledUserInfoService.listPendingAccounts(sellerList);

                            emails = baseUserInfoMapper.getEmail(accountId);
                            generateExcelSendEmail(settledLogInfo,accountsInfo,emails);
//                            buildMailConpent(emails,settledLogInfo);
                            //???????????????????????????????????? ?????????????????????
//                            if (CollectionUtils.isEmpty(settledLogInfo)) {
//                                return;
//                            }
//

                            //??????50??????
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
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

        // ????????????
//        emailService.sendMsgFileDs(fileName, new FileInputStream(fileName));
//            String attr_path = this.getClass().getResource(fileName).getPath();
//            log.info("???????????????{}",attr_path);
            buildMailConpent(emails,dataList,fileName, accountsInfo,new File(fileName).getPath());
            delAllFile(filePath);
        } catch (Exception e) {
            log.error("????????????????????????{}", e);
        }
    }

    //??????????????????excel???????????????????????????????????????????????????
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//?????????????????????????????????
                delFolder(path + "/" + tempList[i]);//?????????????????????
                flag = true;
            }
        }
        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //???????????????????????????
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //??????????????????
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildMailConpent(String emails, List<SellerExcelVo> settledLogInfo,String fileName, List<SellerExcelVo> accountsInfo, String inputStream){
//        String[] toAddresses = mailProperties.getTo().split(",");
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
//            log.info("??????????????????{}",subject);

//            MimeBodyPart content =(MimeBodyPart) createContent(inputStream, fileName);
//            log.info("???????????????{}",content);
//            MimeMultipart mixed = new MimeMultipart("mixed");
//            mixed.addBodyPart(content);
//            msg.setContent(content);



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
//        properties.put("mail.smtp.ssl.enable", mailProperties.isSsl());
//        properties.put("mail.smtp.starttls.enable", mailProperties.isStarttls());
//        properties.put("mail.smtp.starttls.required", mailProperties.isRequired());

        log.info("properties???????????????:{}", JSON.toJSONString(properties));
        Session session = Session.getInstance(properties);
        return session;
    }

    private String htmlMail(List<SellerExcelVo> entitys, List<SellerExcelVo> accountsInfo) {
        StringBuilder mailText = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String reminds = "";
//        String mailUrls = "";
//        String mailLinks = "";
//        try {
//            reminds = new String(remind.getBytes(),"ISO8859-1");
//            mailUrls = new String(mailUrl.getBytes(),"ISO8859-1");
//            mailLinks = new String(remind.getBytes(),"ISO8859-1");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
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
//            if (StringUtils.isNotBlank(entity.getRegisterRegion())) {
//                registerRegion = RegisterRegionEnum.parseRegisterRegion(entity.getRegisterRegion());//code???????????????desc
//            }
            String applyAccount = entity.getApplyAccount();
            String applyTime = "";
            if (entity.getApplyTime() != null) {
                applyTime = sdf.format(entity.getApplyTime());
            }
            String regionAccount = entity.getRegionAccount();

            //????????????
//            String urgeCount = "";
//            if (entity.getUrgeCount() != null) {
//                urgeCount = String.valueOf(entity.getUrgeCount());
//            }
            //??????????????????
//            String lastUrgeTime = "";
//            if (entity.getLastUrgeTime() != null) {
//                lastUrgeTime = sdf.format(entity.getLastUrgeTime());
//            }
            mailText.append("<tr style='background:#99CC00'><td>" + (i + 1) + "</td><td>" + sellerName + "</td><td>" + sellerNo + "</td><td>" + registerRegion + "</td><td>" + applyAccount + "</td><td>" + applyTime + "</td><td>"+regionAccount +"</td><tr>");
        }
        for (int j = 0; j < accountsInfo.size(); j++) {                                                                                                                                             //</td>"++"<td>
            SellerExcelVo sellerExcelVo = accountsInfo.get(j);
            //????????????
            String registerRegion = sellerExcelVo.getRegisterRegion();
//            if (StringUtils.isNotBlank(sellerExcelVo.getRegisterRegion())) {
//                registerRegion = RegisterRegionEnum.parseRegisterRegion(sellerExcelVo.getRegisterRegion());//code???????????????desc
//            }
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
//    Part createContent(String inputStream, String affixName) {
//        MimeBodyPart contentPart = null;
//        try {
//            contentPart = new MimeBodyPart();
//            MimeMultipart contentMultipart = new MimeMultipart("related");
////            MimeBodyPart htmlPart = new MimeBodyPart();
////            htmlPart.setContent(content, "text/html;charset=gbk");
////            contentMultipart.addBodyPart(htmlPart);
//            //????????????
//            MimeBodyPart excelBodyPart = new MimeBodyPart();
//            System.out.println(inputStream);
//
//            DataSource dataSource = new ByteArrayDataSource(inputStream, "application/excel");
////            DataSource dataSource = new FileDataSource(new File(attr_path));
//            DataHandler dataHandler = new DataHandler(dataSource);
//            excelBodyPart.setDataHandler(dataHandler);
//            excelBodyPart.setFileName(MimeUtility.encodeText(affixName));
//            contentMultipart.addBodyPart(excelBodyPart);
//            contentPart.setContent(contentMultipart);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return contentPart;
//    }



    /* *//**
     * ??????????????????
     *//*
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("237497819@qq.com", "????????????????????????", "?????????????????????SpringBoot????????????");
    }

    *//**
     * ??????HTML??????
     *
     * @throws MessagingException ????????????
     *//*
    @Test
    public void sendHtmlMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("project", "Spring Boot Demo");
        context.setVariable("author", "Yangkai.Shen");
        context.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("welcome", context);
        mailService.sendHtmlMail("237497819@qq.com", "??????????????????HTML??????", emailTemplate);
    }

    *//**
     * ??????HTML??????????????????????????????
     *
     * @throws MessagingException ????????????
     *//*
    @Test
    public void sendHtmlMail2() throws MessagingException {

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(context);
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/email/");
        templateResolver.setSuffix(".html");

        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("project", "Spring Boot Demo");
        context.setVariable("author", "Yangkai.Shen");
        context.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("test", context);
        mailService.sendHtmlMail("mrh100353@163.com", "??????????????????HTML??????", emailTemplate);
    }

    *//**
     * ??????????????????
     *
     * @throws MessagingException ????????????
     *//*
    @Test
    public void sendAttachmentsMail() throws MessagingException {
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendAttachmentsMail("mrh100353@163.com", "??????????????????????????????", "???????????????????????????????????????", resource.getPath());
    }

    *//**
     * ????????????????????????
     *
     * @throws MessagingException ????????????
     *//*
    @Test
    public void sendResourceMail() throws MessagingException {
        String rscId = "xkcoding";
        String content = "<html><body>??????????????????????????????<br/><img src=\'cid:" + rscId + "\' ></body></html>";
        URL resource = ResourceUtil.getResource("static/vfdv.jpg");
        mailService.sendResourceMail("mrh100353@163.com", "????????????????????????????????????", content, resource.getPath(), rscId);
    }*/


//        Map<String, Object> pageParams = new HashMap<>();
//        pageParams.put("pageSize", pageSize);
//        //??????userinfo
//        Integer totalSize = userSellersErpService.totalCount();
//        log.info("??????Erp????????????????????????{}???", totalSize);
////        ArrayList<BaseUserInfoEntity> acct = new ArrayList<>();
//        List<BaseUserInfoEntity> listSellerNo = null;
//        Map<List<String>, List<String>> map = null;
//        //??????
//        int pages = (totalSize / pageSize / pageSize + ((totalSize % pageSize == 0) ? 0 : 1));
//
//        for (int i = 0; i < pages; i++) {
//            //????????????userinfo????????????erp
//            listSellerNo = baseUserInfoMapper.listAll(pageParams);
////            for (BaseUserInfoEntity entity : listSellerNo) {
//                //????????????????????????????????????
////                if (entity.getAccountId().contains("#ERP")) {
//                    List<UserSellersErpEntity> getErp = userSellersErpService.getSellerNo(entity.getAccountId());
//                    Map<String, List<UserSellersErpEntity>> collect = getErp.stream().distinct().collect(Collectors.groupingBy(UserSellersErpEntity::getAccountId));
//                    for (Map.Entry<String, List<UserSellersErpEntity>> map1 : collect.entrySet()) {
//                        try {
//                            List<SellerSettledLogInfoEntity> settledLogInfo = settledLogInfoService.listPendingSellers(map1.getValue(),20);
//                            List<SellerSettledLogInfoEntity> accountsInfo = settledUserInfoService.listPendingAccounts(map1.getValue(),20);
//                            //  String msgContent = htmlMail(listSellerNo);
//                            //??????50??????
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                    collect.forEach((key, value) -> value.forEach(v -> {
//
//                        System.out.println(key + "=====" + settledLogInfo);
//                    }));
//                }
//            }
//        }
//        List<SellerSettledLogInfoEntity> list = new ArrayList<>();

//            for (BaseUserInfoEntity entity : listSellerNo) {
//                for (int i1 = 0;i1<listSellerNo.size();i1++){
//                    map = new HashMap<>();
//                    String sellerNo = entity.getSellerNo();
//                    String[] split = sellerNo.split(",");
//
//                    List<String> asList = Arrays.asList(split);

//        List<String> sellerNo = listSellerNo.stream().map(BaseUserInfoEntity::getSellerNo).collect(Collectors.toList());
//
//
//
//
//
//
//
//        Map<String, List<BaseUserInfoEntity>> collect = listSellerNo.stream().distinct().collect(Collectors.groupingBy(BaseUserInfoEntity::getAccountId));
//        //?????????????????????????????????????????????????????????
//
//        List<SellerSettledLogInfoEntity> finalSettledLogInfoEntityList = settledLogInfoEntityList;
//        //??????????????????????????????
//        collect.entrySet().forEach(entry->{
//            entry.getValue().stream().forEach(v->{
//                //???????????????????????????????????????
//                for (SellerSettledLogInfoEntity entity : finalSettledLogInfoEntityList) {
//                    //??????sellerNo?????????????????????
//                    List<BaseUserInfoEntity> ount = baseUserInfoMapper.getAccountId(entity.getSellerNo());
//
//                }
//            });
//        });
//        System.out.println(collect);

//        List<String> accountId = listSellerNo.stream().map(BaseUserInfoEntity::getAccountId).collect(Collectors.toList());
//        map.put(accountId, sellerNo);
////            }
////        }
//        if (!CollectionUtils.isEmpty(map)){
//            map.entrySet().forEach(entry->{
//                entry.getValue().forEach(sell->{
//                    System.out.println(entry.getKey()+"======"+sell.toString());
//                });
//            });
//        }

//
//        //????????????????????????????????????????????????
//        settledLogInfoEntityList = settledLogInfoService.listAccountApp(sellerNo);
//        for (SellerSettledLogInfoEntity acc : settledLogInfoEntityList) {
//            listSellerNo.forEach(s->{
//                if (acc.getSellerNo().equals(s.getSellerNo())){
//                    s.setAccountId(s.getAccountId());
//                }
//            });
//        }


