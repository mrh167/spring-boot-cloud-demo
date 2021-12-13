package org.consumer.log.service;

import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ParamConfigService {
    @Value("${rocket.group}")
    public String rocketGroup ;
    @Value("${rocket.topic}")
    public String rocketTopic ;
    @Value("${rocket.tag}")
    public String rocketTag ;


    public void getMqMessage(MessageExt msg) throws UnsupportedEncodingException {
        String body = null;
        body = new String(msg.getBody(), "utf-8");
        System.out.println(msg.getTopic()+"================"+body);
    }
}