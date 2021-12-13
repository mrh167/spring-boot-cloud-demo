package com.mq.startMq.core.producer;


import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.springframework.beans.factory.BeanNameAware;

/**
 *
 * @author jolly
 */
public interface NamedMessageQueueSelector extends MessageQueueSelector, BeanNameAware {

    String getBeanName();

}
