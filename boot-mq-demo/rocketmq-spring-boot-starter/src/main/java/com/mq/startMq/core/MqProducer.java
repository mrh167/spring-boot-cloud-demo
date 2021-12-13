package com.mq.startMq.core;


import com.mq.startMq.core.producer.MessageProxy;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;


/**
 * @author jolly
 */
public interface MqProducer<M> {

    void send(MessageProxy<M> messageProxy) throws MQClientException, InterruptedException, RemotingException, RemotingException;


    void start() throws MQClientException, MQClientException;

    void shutdown();

}
