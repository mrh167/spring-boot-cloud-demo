package com.mq.startMq.core.producer;


import com.mq.startMq.core.MqConfig;

/**
 * @author jolly
 */
public final class ProducerConfig extends MqConfig {

    private String producerGroup;


    private int timeOut = 3000;

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }


    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}