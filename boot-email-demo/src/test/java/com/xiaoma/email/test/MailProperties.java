package com.xiaoma.email.test;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Configuration
@Component
@PropertySource("classpath:application.properties")
@Data
public class MailProperties implements Serializable {
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.sender.username}")
    private String username;
    @Value("${mail.sender.password}")
    private String password;
    @Value("${mail.contentPart.type}")
    private String contentPart;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.to}")
    private String to;
    @Value("${mail.transport.protocol}")
    private String protocol;

}