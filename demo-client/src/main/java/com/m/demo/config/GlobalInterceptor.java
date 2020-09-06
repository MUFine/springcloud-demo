package com.m.demo.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * author:M
 * describe:
 * date:2020/3/6 11:10
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println("拦截器开始启用！");
        String secretKey = request.getHeader("visit");
        if (StringUtils.isBlank(secretKey) || !"gateway".equals(secretKey)) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("url错误!");
            return false;
        }
        return true;
    }
}
