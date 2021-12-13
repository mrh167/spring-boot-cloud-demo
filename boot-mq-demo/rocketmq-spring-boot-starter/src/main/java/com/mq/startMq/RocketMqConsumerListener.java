package com.mq.startMq;


import com.mq.startMq.core.consumer.ConsumerConfig;
import com.mq.startMq.core.consumer.MessageContext;
import com.mq.startMq.exception.ConsumeException;

/**
 * @author jolly
 */
public interface RocketMqConsumerListener<E> {

    void onMessage(E message, MessageContext messageContext) throws ConsumeException;

    ConsumerConfig getConsumerConfig();


}
