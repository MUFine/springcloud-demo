package com.m.demo.config;

import com.m.demo.common.Code;
import com.m.demo.common.HttpResult;
import com.m.demo.common.Message;
import com.m.demo.common.Secret;
import com.m.demo.entity.ResultData;
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
        String gateway = request.getHeader("gateway");
        if (StringUtils.isBlank(gateway) || !Secret.GATEWAY_SECRET.equals(gateway)) {
            HttpResult.result(response,new ResultData(Code.URL_ERROR_CODE, Message.URL_ERROR));
            return false;
        }
        return true;
    }
}
