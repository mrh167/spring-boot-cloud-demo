package org.consumer.log.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 消息消费监听
 */
@Component
public class RocketMsgListener {
    private static final Logger LOG = LoggerFactory.getLogger(RocketMsgListener.class) ;

//    public void fun1(){
//        LOG.info("消费者.........");
//        // 实例化消息生产者,指定组名
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("rocketGroup");
//        // 注册回调函数，处理消息
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
//                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf("%s Receive New Messages: %s %n",
//                        Thread.currentThread().getName(), msgs);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//    }




//    @Override
//    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
//        LOG.info("消费者!!!!!!");
//        if (CollectionUtils.isEmpty(list)){
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        }
//        MessageExt messageExt = list.get(0);
//        LOG.info("接受到的消息为："+new String(messageExt.getBody()));
//        int reConsume = messageExt.getReconsumeTimes();
//        // 消息已经重试了3次，如果不需要再次消费，则返回成功
//        if(reConsume ==3){
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        }
//        if(messageExt.getTopic().equals(paramConfigService.rocketTopic)){
//            String tags = messageExt.getTags() ;
//            switch (tags){
//                case "rocketTag":
//                    LOG.info("开户 tag == >>"+tags);
//                    break ;
//                default:
//                    LOG.info("未匹配到Tag == >>"+tags);
//                    break;
//            }
//        }
//        // 消息消费成功
//        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//    }
}