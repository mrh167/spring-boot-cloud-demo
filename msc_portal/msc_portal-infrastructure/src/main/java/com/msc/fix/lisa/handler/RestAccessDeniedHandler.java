package com.msc.fix.lisa.handler;


import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author by cdh
 * @description:当访问接口没有权限时，自定义返回的结果
 * @Date: Created in 19:13 on 2021/4/18
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        SingleResponse repo =new SingleResponse<>();
        repo.setErrMessage("权限不足，请联系管理员！");
        repo.setErrCode("403");
        out.write(new ObjectMapper().writeValueAsString(repo));
        out.flush();
        out.close();
    }
}
