package com.mq.startMq.core.consumer;

import lombok.Data;

/**
 *
 * @author jolly
 */

@Data
public class OperationResult<T> {

    private Boolean success;

    private String message;

    private T data;

    public static OperationResult result(Boolean success,String message){
        OperationResult result = new OperationResult();
        result.setMessage(message);
        result.setSuccess(success);
        return result;
    }


}
