package com.msc.fix.lisa.exception;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.ExceptionHandlerI;
import com.alibaba.cola.exception.framework.BaseException;
import com.alibaba.cola.exception.framework.BasicErrorCode;
import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.msc.fix.lisa.common.BusinessException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * MyProjectExceptionHandler
 *
 * @author lishudong
 * @version 1.0
 * @date 2020/4/16
 */
@Component
public class MyProjectExceptionHandler implements ExceptionHandlerI {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(MyProjectExceptionHandler.class);

    @Override
    public void handleException(Command cmd, Response response, Exception exception) {
        buildResponse(response, exception);
        printLog(cmd, response, exception);
    }

    /**
     * printLog
     *
     * @param cmd
     * @param response
     * @param exception
     */
    private void printLog(Command cmd, Response response, Exception exception) {
        if (exception instanceof BaseException) {
            //biz exception is expected, only warn it
            logger.warn(buildErrorMsg(cmd, response));
        } else {
            //sys exception should be monitored, and pay attention to it
            logger.error(buildErrorMsg(cmd, response), exception);
        }
    }

    /**
     * buildErrorMsg
     *
     * @param cmd
     * @param response
     * @return
     */
    private String buildErrorMsg(Command cmd, Response response) {
        return "Process [" + cmd + "] failed, errorCode: "
                + response.getErrCode() + " errorMsg:"
                + response.getErrMessage();
    }

    /**
     * buildResponse
     *
     * @param response
     * @param exception
     */
    private void buildResponse(Response response, Exception exception) {
        response.setErrMessage(exception.getMessage());
        if (exception instanceof BizException) {
            response.setErrCode(BasicErrorCode.BIZ_ERROR.getErrCode());
        } else if (exception instanceof BusinessException) {
            response.setErrCode(BasicErrorCode.BIZ_ERROR.getErrCode());
        } else if (exception instanceof AuthenticationException) {
            response.setErrCode(BasicErrorCode.BIZ_ERROR.getErrCode());
        }  else if(exception instanceof ExcelAnalysisException){
            response.setErrCode(BasicErrorCode.BIZ_ERROR.getErrCode());
        } else{
            response.setErrCode(BasicErrorCode.SYS_ERROR.getErrCode());
            response.setErrMessage(BasicErrorCode.SYS_ERROR.getErrDesc());
        }
        response.setSuccess(false);
    }
}
