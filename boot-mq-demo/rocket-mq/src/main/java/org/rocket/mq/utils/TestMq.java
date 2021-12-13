package org.rocket.mq.utils;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.rocket.mq.bean.Producer;
import org.rocket.mq.service.ParamConfigService;
import org.rocket.mq.service.RocketMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/12/9
 * Time: 11:32
 * Description: No Description
 */
@Service
public class TestMq implements RocketMqService {
    //    @Resource
//    private DefaultMQProducer defaultMQProducer;

    @Autowired
    Producer producer;

    @Resource
    private ParamConfigService paramConfigService;

    @Override
    public SendResult openAccountMsg(String msgInfo) {
        // 可以不使用Config中的Group
//        defaultMQProducer.setProducerGroup(paramConfigService.rocketGroup);
        SendResult sendResult = null;
        try {
            Message sendMsg = new Message(paramConfigService.rocketTopic,
                    paramConfigService.rocketTag,
                    "open_account_key", msgInfo.getBytes());
            sendResult = producer.getProducer().send(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult;
    }

}
