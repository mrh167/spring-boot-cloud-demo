package com.msc.fix.lisa.colaevent;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventI;

import java.util.List;

/**
 * @author qiuchang
 * @Classname MuliEventBusI
 * @Description MuliEventBusI
 * @date 2020/8/14 14:21
 */
public interface CustomizeEventBusI {
    /**
     * 同步触发事件
     * @param event
     * @return
     */
    public Response fire(EventI event);

    /**
     * 同步触发所有事件
     * @param event
     * @return
     */
    public List<Response> fireAll(EventI event);

    /**
     * 异步触发事件
     * @param event
     */
    public void asyncFire(EventI event);

    /**
     * 异步触发所有事件
     * @param event
     */
    public void asyncFireAll(EventI event);



}
