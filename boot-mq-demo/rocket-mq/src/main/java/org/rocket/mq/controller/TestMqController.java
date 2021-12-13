package org.rocket.mq.controller;

import org.apache.rocketmq.client.producer.SendResult;
import org.rocket.mq.bean.Mq;
import org.rocket.mq.utils.TestMq;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/12/9
 * Time: 11:44
 * Description: No Description
 */
@RestController
public class TestMqController {
    @Resource
    private TestMq testMq;



    @RequestMapping("/sysoTest")
    public void fun1(){
        Mq mq = new Mq("88880000088", 21, "这是我第10个提供的对象");
        SendResult nihaoMq = testMq.openAccountMsg(mq.toString());
        System.out.println("发送成功!!!!!"+nihaoMq.toString());
    }
}
