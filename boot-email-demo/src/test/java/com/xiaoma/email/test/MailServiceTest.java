package com.xiaoma.email.test;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoma.email.SpringBootDemoEmailApplicationTests;
import com.xiaoma.email.common.enums.ManageDimensionEnum;
import com.xiaoma.email.common.enums.RegisterRegionEnum;
import com.xiaoma.email.entity.BaseUserInfoEntity;
import com.xiaoma.email.entity.SellerSettledLogInfoEntity;
//import com.xiaoma.email.service.MailService;
import com.xiaoma.email.common.utils.StringUtils;
import com.xiaoma.email.entity.UserSellersErpEntity;
import com.xiaoma.email.mapper.BaseUserInfoMapper;
import com.xiaoma.email.mapper.UserSellersErpMapper;
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
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static sun.security.x509.X509CertInfo.SUBJECT;
@Configuration
//@EnableScheduling
@Slf4j
@PropertySource("classpath:application.yml")
public class MailServiceTest extends SpringBootDemoEmailApplicationTests {
//    @Autowired
//    MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ApplicationContext context;






   /* *//**
     * 测试简单邮件
     *//*
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("237497819@qq.com", "这是一封简单邮件", "这是一封普通的SpringBoot测试邮件");
    }

    *//**
     * 测试HTML邮件
     *
     * @throws MessagingException 邮件异常
     *//*
    @Test
    public void sendHtmlMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("project", "Spring Boot Demo");
        context.setVariable("author", "Yangkai.Shen");
        context.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("welcome", context);
        mailService.sendHtmlMail("237497819@qq.com", "这是一封模板HTML邮件", emailTemplate);
    }

    *//**
     * 测试HTML邮件，自定义模板目录
     *
     * @throws MessagingException 邮件异常
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
        mailService.sendHtmlMail("mrh100353@163.com", "这是一封模板HTML邮件", emailTemplate);
    }

    *//**
     * 测试附件邮件
     *
     * @throws MessagingException 邮件异常
     *//*
    @Test
    public void sendAttachmentsMail() throws MessagingException {
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendAttachmentsMail("mrh100353@163.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", resource.getPath());
    }

    *//**
     * 测试静态资源邮件
     *
     * @throws MessagingException 邮件异常
     *//*
    @Test
    public void sendResourceMail() throws MessagingException {
        String rscId = "xkcoding";
        String content = "<html><body>这是带静态资源的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
        URL resource = ResourceUtil.getResource("static/vfdv.jpg");
        mailService.sendResourceMail("mrh100353@163.com", "这是一封带静态资源的邮件", content, resource.getPath(), rscId);
    }*/
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
    @Value("${sync.user.erp.task.pageSize:20}")
    private Integer pageSize;



    @Test
    public void sendMail() {
        /**
         * 催办邮件配置类
         */
        log.info("MailScheduleTask.sendMail start");

//        List<UrgeLogInfoCo> list = new ArrayList<>();
//        list.add( new UrgeLogInfoCo(1L,"322332323","张三",1,new Date(),"华北"));
//        list.add( new UrgeLogInfoCo(2L,"5746465466","里斯",1,new Date(),"华东"));

        //pageCount = (totalCount -1) / pageSize + 1
        //pageCount = (totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1)
        //pageCount = (totalCount + pageSize -1) / pageSize



        String msgContent = "";
        Map<String, Object> pageParams = new HashMap<>();
            pageParams.put("id", 0);
            pageParams.put("pageSize", pageSize);
            pageParams.put("manageDimension", ManageDimensionEnum.SALES.getCode());
            Integer totalSize = baseUserInfoMapper.totalCountDimension(ManageDimensionEnum.SALES.getCode());
            log.info("user - total size: 【{}】", totalSize);

            if (totalSize > 0) {
                int pages = (int) (totalSize / pageSize + ((totalSize % pageSize == 0) ? 0 : 1));
                for (int i = 0; i < pages; i++) {
                    List<BaseUserInfoEntity> userList = baseUserInfoMapper.taskPageManageDimension(pageParams);
                    log.info("user - userList size: 【{}】, pages = {}", userList.size(), i + 1);

                    if (CollUtil.isNotEmpty(userList)) {
                        List<String> accountIds = userList.stream().map(BaseUserInfoEntity::getAccountId).distinct().collect(Collectors.toList());
                        List<UserSellersErpEntity> erpList = userSellersErpMapper.selectSellerNoListByAccounts(accountIds);
                        log.info("user - erpList size: 【{}】", erpList.size());

//                        Map<String, UserSellersErpDo> erpMap = erpList.stream().collect(Collectors.);
                        Map<String, List<UserSellersErpEntity>> erpMap = erpList.stream().distinct().collect(Collectors.groupingBy(UserSellersErpEntity::getAccountId));
                        erpMap.forEach((accountId, erpDoList) -> {
                            try {
                                List<SellerSettledLogInfoEntity> settledLogInfo = settledLogInfoService.listPendingSellers(erpDoList);
                                htmlMail(settledLogInfo);
                                //如果查不到账号申请的数据 则不用发送邮件
                                if (CollectionUtils.isEmpty(settledLogInfo)) {
                                    return;
                                }
//                                List<SellerSettledLogInfoEntity> accountsInfo = settledUserInfoService.listPendingAccounts(erpDoList, null);

                                //睡眠50毫秒
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        });

                        BaseUserInfoEntity lastUser = userList.get(userList.size() - 1);
                        pageParams.put("id", lastUser.getId());
                    }
                }
            }

        String[] toAddresses = mailProperties.getTo().split(",");
        log.info("收件人：{}", JSON.toJSONString(toAddresses));

        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.auth", mailProperties.isAuth());
        properties.put("mail.smtp.port", mailProperties.getPort());
        properties.put("mail.sender.username", mailProperties.getUsername());
        properties.put("mail.sender.password", mailProperties.getPassword());
        properties.put("mail.transport.protocol", mailProperties.getProtocol());
        properties.put("mail.smtp.ssl.enable", mailProperties.isSsl());
        properties.put("mail.smtp.starttls.enable", mailProperties.isStarttls());
        properties.put("mail.smtp.starttls.required", mailProperties.isRequired());

        log.info("properties配置信息为:{}", JSON.toJSONString(properties));
        Session session = Session.getInstance(properties);

        // 创建邮件对象
        Message msg = new MimeMessage(session);
        try {
            msg.setSubject(SUBJECT);
//            String msgContent = htmlMail(listSellerNo);
            log.info("发送邮件内容：{}", msgContent);
            msg.setContent(msgContent, mailProperties.getContentPart());// 设置邮件内容，为html格式
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
            /* 发送邮件 */
            transport.sendMessage(msg, addresses.toArray(new Address[addresses.size()]));
            // 关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String htmlMail( List<SellerSettledLogInfoEntity> entitys) {
        StringBuilder mailText = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mailText.append("<table style='text-align: center;border-collapse:collapse;' border='1' cellpadding='0' cellspacing='0'>");
        mailText.append("<tr bgcolor='#C00000'><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>序号</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>商家名称</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>商家编码(ECP)</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>签约区域</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>催办次数</span></b></td><td><b><span style='font-size:9.0pt;font-family:'微软雅黑',sans-serif;color:white'>最新催办时间</span></b></td><tr>");

        for (int i = 0; i < entitys.size(); i++) {
            SellerSettledLogInfoEntity entity = entitys.get(i);
            String sellerName = entity.getSellerName();
            String sellerNo = entity.getSellerNo();
            //签约区域
            String registerRegion = "";
            if (StringUtils.isNotBlank(entity.getRegisterRegion())) {
                registerRegion = RegisterRegionEnum.parseRegisterRegion(entity.getRegisterRegion());//code转换成枚举desc
            }
            //催办次数
            String urgeCount = "";
            if (entity.getUrgeCount() != null) {
                urgeCount = String.valueOf(entity.getUrgeCount());
            }
            //最后催办时间
            String lastUrgeTime = "";
            if (entity.getLastUrgeTime() != null) {
                lastUrgeTime = sdf.format(entity.getLastUrgeTime());
            }
            mailText.append("<tr><td>" + (i + 1) + "</td><td>" + sellerName + "</td><td>" + sellerNo + "</td><td>" + registerRegion + "</td><td>" + urgeCount + "</td><td>" + lastUrgeTime + "</td><tr>");
        }
        mailText.append("</table>");
        return mailText.toString();
    }





//        Map<String, Object> pageParams = new HashMap<>();
//        pageParams.put("pageSize", pageSize);
//        //分页userinfo
//        Integer totalSize = userSellersErpService.totalCount();
//        log.info("销售Erp的总记录数为：【{}】", totalSize);
////        ArrayList<BaseUserInfoEntity> acct = new ArrayList<>();
//        List<BaseUserInfoEntity> listSellerNo = null;
//        Map<List<String>, List<String>> map = null;
//        //修改
//        int pages = (totalSize / pageSize / pageSize + ((totalSize % pageSize == 0) ? 0 : 1));
//
//        for (int i = 0; i < pages; i++) {
//            //分页查询userinfo所有销售erp
//            listSellerNo = baseUserInfoMapper.listAll(pageParams);
////            for (BaseUserInfoEntity entity : listSellerNo) {
//                //判断该账号是否是销售账号
////                if (entity.getAccountId().contains("#ERP")) {
//                    List<UserSellersErpEntity> getErp = userSellersErpService.getSellerNo(entity.getAccountId());
//                    Map<String, List<UserSellersErpEntity>> collect = getErp.stream().distinct().collect(Collectors.groupingBy(UserSellersErpEntity::getAccountId));
//                    for (Map.Entry<String, List<UserSellersErpEntity>> map1 : collect.entrySet()) {
//                        try {
//                            List<SellerSettledLogInfoEntity> settledLogInfo = settledLogInfoService.listPendingSellers(map1.getValue(),20);
//                            List<SellerSettledLogInfoEntity> accountsInfo = settledUserInfoService.listPendingAccounts(map1.getValue(),20);
//                            //  String msgContent = htmlMail(listSellerNo);
//                            //睡眠50毫秒
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
//        //通过商家编号查询审核状态为待审核的商家
//
//        List<SellerSettledLogInfoEntity> finalSettledLogInfoEntityList = settledLogInfoEntityList;
//        //遍历销售所管理的商家
//        collect.entrySet().forEach(entry->{
//            entry.getValue().stream().forEach(v->{
//                //遍历所有状态为待审核的商家
//                for (SellerSettledLogInfoEntity entity : finalSettledLogInfoEntityList) {
//                    //通过sellerNo在账号配置表中
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
//        //匹配所有在账号申请表中的商家编号
//        settledLogInfoEntityList = settledLogInfoService.listAccountApp(sellerNo);
//        for (SellerSettledLogInfoEntity acc : settledLogInfoEntityList) {
//            listSellerNo.forEach(s->{
//                if (acc.getSellerNo().equals(s.getSellerNo())){
//                    s.setAccountId(s.getAccountId());
//                }
//            });
//        }
}

