package com.mq.startMq.operation;


import com.mq.startMq.ConsumerOperator;
import com.mq.startMq.core.consumer.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author jolly
 */

@RestController
public class ContainerOperatorController {

    private final ConsumerOperator operator;

    @Autowired
    public ContainerOperatorController(ConsumerOperator operator){
        this.operator = operator;
    }

    @GetMapping("/consumer/resume/{topic}")
    private OperationResult resume(@PathVariable("topic") String topic){
        return operator.resumeConsumer(topic);
    }


    @GetMapping("/consumer/suspend/{topic}")
    private OperationResult suspend(@PathVariable("topic") String topic){
        return operator.suspendConsumer(topic);
    }

}
