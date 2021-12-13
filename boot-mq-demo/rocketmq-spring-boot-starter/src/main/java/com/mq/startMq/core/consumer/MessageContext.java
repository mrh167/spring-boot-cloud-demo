package com.mq.startMq.core.consumer;


import lombok.Data;
import lombok.ToString;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * 消费时，当前所消费的消息的上下文信息
 *
 * @author jolly
 */
@ToString
@Data
public final class MessageContext {

    /**
     * 所消费消息所在的消息队列
     *
     * @see MessageQueue
     */
    private MessageQueue messageQueue;

    /**
     * 所消费的消息的扩展属性
     *
     * @see MessageExt
     */
    private MessageExt messageExt;


}
