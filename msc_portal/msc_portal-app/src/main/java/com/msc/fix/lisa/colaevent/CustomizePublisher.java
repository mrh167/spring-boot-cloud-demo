package com.msc.fix.lisa.colaevent;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.DomainEventI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qiuchang
 * @Classname MultiFirePublisher
 * @Description MultiFirePublisher
 * @date 2020/8/14 11:36
 */
@Component
public class CustomizePublisher {

    @Autowired
    private CustomizeEventBusI eventBusI;

    /**
     * 默认同步调用order最小的eventhandler
     * @param domainEvent domainEvent
     */
    public Response publish(DomainEventI domainEvent) {
        return eventBusI.fire(domainEvent);
    }

    /**
     * 同步调用所有eventhandler
     * @param domainEvent domainEvent
     */
    public List<Response> publishAll(DomainEventI domainEvent){
        List<Response> responses = eventBusI.fireAll(domainEvent);
        return responses;
    }

    /**
     *异步调用order最小的handler
     * @param domainEventI
     */
    public void asyncPublish(DomainEventI domainEventI){
        eventBusI.asyncFire(domainEventI);
    }

    /**
     * 异步
     * @param domainEventI
     */
    public void asyncPublishAll(DomainEventI domainEventI){
        eventBusI.asyncFireAll(domainEventI);
    }
}
