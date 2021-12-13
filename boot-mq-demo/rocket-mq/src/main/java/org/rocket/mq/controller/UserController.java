package org.rocket.mq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.rocket.mq.bean.JmsConfig;
import org.rocket.mq.bean.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Autowired
    Producer producer;

    @RequestMapping("/hello")
    public void hello()throws Exception{


        for(int i=1;i<10;i++){
            //创建生产信息
            Message message = new Message(JmsConfig.TOPIC, "rocketTag", ("Hello World mqll"+i).getBytes());
            //发送
            SendResult sendResult = producer.getProducer().send(message);
            logger.info("输出生产者信息={}",sendResult);
        }
    }
}
