package com.xiaoma.email.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@Slf4j
@Service
public class EmailService {

    private final String USER_NAME = "ext.maruihua1@jd.com";


    public void sendMsgFileDs(List<String> acceptAddressList, String title, String text, String affixName, InputStream inputStream) throws Exception {
        Session session = assembleSession();
        Message msg = new MimeMessage(session);
//        try {
            msg.setFrom(new InternetAddress(USER_NAME));
            msg.setSubject(title);
 
            Address[] addressArr = acceptAddressList(acceptAddressList);
            msg.setRecipients(Message.RecipientType.TO, addressArr);
            MimeBodyPart contentPart = (MimeBodyPart) createContent(text, inputStream, affixName);//参数为正文内容和附件流
            MimeMultipart mime = new MimeMultipart("mixed");
            mime.addBodyPart(contentPart);
            msg.setContent(mime);
            Transport.send(msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    static Part createContent(String content, InputStream inputStream, String affixName) {
        MimeBodyPart contentPart = null;
        try {
            contentPart = new MimeBodyPart();
            MimeMultipart contentMultipart = new MimeMultipart("related");
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(content, "text/html;charset=gbk");
            contentMultipart.addBodyPart(htmlPart);
            //附件部分
            MimeBodyPart excelBodyPart = new MimeBodyPart();
            DataSource dataSource = new ByteArrayDataSource(inputStream, "application/excel");
            DataHandler dataHandler = new DataHandler(dataSource);
            excelBodyPart.setDataHandler(dataHandler);
            excelBodyPart.setFileName(MimeUtility.encodeText(affixName));
            contentMultipart.addBodyPart(excelBodyPart);
            contentPart.setContent(contentMultipart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentPart;
    }
 
    public Address[] acceptAddressList(List<String> acceptAddressList) {
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address[] tos = new InternetAddress[acceptAddressList.size()];
        try {
            for (int i = 0; i < acceptAddressList.size(); i++) {
                tos[i] = new InternetAddress(acceptAddressList.get(i));
            }
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return tos;
    }
    public Session assembleSession() {
        String host = "smtp.126.com";
        String mailStoreType = "smtp";
        String popPort = "25";
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.store.protocol", mailStoreType);
        props.put("mail.smtp.port", popPort);
        //开启SSL
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.socketFactory.port", popPort);
//        props.put("mail.smtp.socketFactory.fallback", "false");
        String PASSWORD = "";
        Session session = Session.getDefaultInstance(props, new MyAuthenricator(USER_NAME, PASSWORD));
        return session;
    }

    //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    static class MyAuthenricator extends Authenticator {
        String u = null;
        String p = null;
        public MyAuthenricator(String u, String p) {
            this.u = u;
            this.p = p;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u, p);
        }
    }
}