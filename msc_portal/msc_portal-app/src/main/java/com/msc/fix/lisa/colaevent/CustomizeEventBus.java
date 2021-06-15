package com.msc.fix.lisa.colaevent;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventBus;
import com.alibaba.cola.event.EventHandlerI;
import com.alibaba.cola.event.EventHub;
import com.alibaba.cola.event.EventI;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author qiuchang
 * @Classname MultiEventBus
 * @Description 基本cola框架进行改造的带事务，顺序的事件总线
 * @date 2020/8/14 14:22
 */
@Component
public class CustomizeEventBus implements CustomizeEventBusI {
    /**
     * 日志记录
     */
    Logger logger = LoggerFactory.getLogger(EventBus.class);


    @Autowired
    private EventHub eventHub;

    /**
     * 默认线程池
     * 如果处理器无定制线程池，则使用此默认
     */
    ExecutorService defaultExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() + 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1000), new ThreadFactoryBuilder().setNameFormat("event-bus-pool-%d").build());


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response fire(EventI event) {
        Response response = null;
        EventHandlerI eventHandlerI = null;
        List<EventHandlerI> eventHandlers = eventHub.getEventHandler(event.getClass());
        eventHandlers.sort((o1, o2) -> {
            int order1 = o1.getClass().getAnnotation(Order.class).value();
            int order2 = o2.getClass().getAnnotation(Order.class).value();
            return order1 > order2 ? -1 : 1;
        });
        response = eventHandlers.get(0).execute(event);
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Response> fireAll(EventI event) {
        List<EventHandlerI> eventHandlers = eventHub.getEventHandler(event.getClass());
        eventHandlers.sort((o1, o2) -> {
            int order1 = o1.getClass().getAnnotation(Order.class).value();
            int order2 = o2.getClass().getAnnotation(Order.class).value();
            return order1 > order2 ? -1 : 1;
        });
        List<Response> responses = eventHandlers.stream().map(p -> {
            Response response = p.execute(event);
            return response;
        }).collect(Collectors.toList());
        return responses;
    }

    @Override
    public void asyncFire(EventI event) {
        List<EventHandlerI> eventHandlers = eventHub.getEventHandler(event.getClass());
        eventHandlers.sort((o1, o2) -> {
            int order1 = o1.getClass().getAnnotation(Order.class).value();
            int order2 = o2.getClass().getAnnotation(Order.class).value();
            return order1 > order2 ? -1 : 1;
        });
        EventHandlerI eventHandlerI = eventHandlers.get(0);
        if (eventHandlerI != null) {
            if (null != eventHandlerI.getExecutor()) {
                eventHandlerI.getExecutor().submit(() -> eventHandlerI.execute(event));
            } else {
                defaultExecutor.submit(() -> eventHandlerI.execute(event));
            }
        }
    }

    @Override
    public void asyncFireAll(EventI event) {
        List<EventHandlerI> eventHandlers = eventHub.getEventHandler(event.getClass());
        eventHandlers.stream().map(p -> {
            if (null != p.getExecutor()) {
                p.getExecutor().submit(() -> p.execute(event));
            } else {
                defaultExecutor.submit(() -> p.execute(event));
            }
            return null;
        });

    }


}
