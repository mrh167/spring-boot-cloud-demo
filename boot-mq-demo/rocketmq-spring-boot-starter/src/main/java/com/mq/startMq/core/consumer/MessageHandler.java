package com.mq.startMq.core.consumer;


import com.alibaba.fastjson.JSON;
import com.mq.startMq.RocketMqConsumerListener;
import com.mq.startMq.exception.ConsumeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 消息处理器
 *
 * @author jolly
 */
@Slf4j
public class MessageHandler {


    @SuppressWarnings("unchecked")
    public static ConsumeStatus handleMessage(final RocketMqConsumerListener listener,
                                              final List<MessageExt> msgs, final MessageQueue messageQueue) {
        try {
            for (MessageExt msg : msgs) {
                byte[] body = msg.getBody();
                final MessageContext messageContext = new MessageContext();
                messageContext.setMessageExt(msg);
                messageContext.setMessageQueue(messageQueue);
                if (log.isDebugEnabled()) {
                    log.debug("开始消费，msg={}", msg);
                }
                listener.onMessage(JSON.parseObject(new String(body, "UTF-8"),
                        listener.getConsumerConfig().getMessageClass()), messageContext);
                if (log.isDebugEnabled()) {
                    log.debug("消费完成");
                }
            }
        } catch (Exception e) {
            return handleException(e);
        }
        return ConsumeStatus.SUCCESS;
    }

    /**
     * 异常处理
     *
     * @param e 捕获的异常
     * @return 消息消费结果
     */
    private static ConsumeStatus handleException(final Exception e) {
        Class exceptionClass = e.getClass();
        if (exceptionClass.equals(UnsupportedEncodingException.class)) {
            log.error(e.getMessage());
        } else if (exceptionClass.equals(ConsumeException.class)) {
            log.error(e.getMessage());
        }
        return ConsumeStatus.RETRY;
    }


}
