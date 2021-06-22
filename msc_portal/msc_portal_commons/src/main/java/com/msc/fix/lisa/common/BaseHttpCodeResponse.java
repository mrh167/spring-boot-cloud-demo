package com.msc.fix.lisa.common;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.framework.BasicErrorCode;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class BaseHttpCodeResponse extends SingleResponse {
    /**
     * httpCode
     */
    private String httpCode;


    public static SingleResponse error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static SingleResponse error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static SingleResponse error(int code, String msg) {
        HashMap<Object, Object> r = new HashMap<>();
        r.put("code", code);
        r.put("msg", msg);
        return SingleResponse.of(r);
    }



    /**
     * 构造方法 代表错误
     *
     * @param httpCode httpCode
     */
    public BaseHttpCodeResponse(String httpCode, String msg) {
        this.httpCode = httpCode;
        super.setSuccess(false);
        super.setErrCode(BasicErrorCode.SYS_ERROR.getErrCode());
        if (msg == null) {
            super.setErrMessage(httpCode + " 错误");
        } else {
            super.setErrMessage(msg);
        }
    }


    public BaseHttpCodeResponse() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "success");
    }

    /**
     * 打印错误码
     *
     * @param response response
     * @param httpCode httpCode
     * @param msg      msg
     */
    public static void printErrorHttpCode(HttpServletResponse response, String httpCode, String msg) {
        BaseHttpCodeResponse httpCodeResponse = new BaseHttpCodeResponse(httpCode, msg);
        //返回json格式
        response.setStatus(200);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(JSON.toJSON(httpCodeResponse));
            pw.flush();
        } catch (Exception e) {
            log.error("printErrorHttpCode异常处理 error", e);
        } finally {
            IOUtils.closeQuietly(pw);
        }
    }
}
