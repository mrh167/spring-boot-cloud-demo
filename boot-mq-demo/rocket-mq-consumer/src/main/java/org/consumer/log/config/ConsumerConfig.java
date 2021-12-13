package org.consumer.log.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.consumer.log.service.ParamConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * RocketMQ 消费者配置
 */


@Configuration
public class ConsumerConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerConfig.class) ;
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Value("${rocket.topic}")
    private String topic;

    @Resource
    private RocketMsgListener msgListener;


    @Resource
    private ParamConfigService paramConfigService ;


    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        // 设置nameServe地址
        consumer.setNamesrvAddr(namesrvAddr);
        //
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        // 订阅主题和 标签（ * 代表所有标签)下信息
        try {
            consumer.subscribe(topic, "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                for (MessageExt msg : msgs) {
                    //消费者获取消息 这里只输出 不做后面逻辑处理
                    try {
//                        body = new String(msg.getBody(), "utf-8");
                        paramConfigService.getMqMessage(msg);
//                        LOG.info("Consumer-获取消息-主题topic为={}, 消费消息为={}", msg.getTopic(), body);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            System.out.println("消费者 启动成功=======");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }
}