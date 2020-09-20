package com.m.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.m.demo.annotation.Login;
import com.m.demo.annotation.Pass;
import com.m.demo.common.Code;
import com.m.demo.common.HttpResult;
import com.m.demo.common.Message;
import com.m.demo.common.Secret;
import com.m.demo.entity.ResultData;
import com.m.demo.jwt.JWTUtil;
import com.m.demo.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.m.demo.common.HttpResult.result;

/**
 * author:M
 * describe:登录拦截
 * date:2020/3/13 11:51*/



public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler){
        if(!(handler instanceof HandlerMethod)){
            return true;// 如果不是映射到方法直接通过
        }
        String token = httpServletRequest.getHeader("authentication");// 从 http 请求头中取出 token
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有Pass注释，有则跳过认证
        if (method.isAnnotationPresent(Pass.class)) {
            Pass passToken = method.getAnnotation(Pass.class);
            if (passToken.required()) {
                return true;
            }
        } else if (method.isAnnotationPresent(Login.class)) {            //检查有没有需要用户权限的注解
            Login loginToken = method.getAnnotation(Login.class);
            if (loginToken.required()) {
                // 执行认证
                if (null == token) {
                    result(httpServletResponse,new ResultData(Code.WITHOUT_TOKEN_CODE, Message.WITHOUT_TOKEN));
                    return false;
                }
                if(!JWTUtil.verifyToken(token, MD5Util.getMd5(Secret.TOKEN_SECRET,null))){
                    result(httpServletResponse,new ResultData(Code.INVALID_TOKEN_CODE, Message.INVALID_TOKEN));
                    return false;
                }
                try {
                    // 获取token中的userId
                    String userId = JWT.decode(token).getClaim("userId").asString();
                    if(StringUtils.isBlank(userId)){
                        result(httpServletResponse,new ResultData(Code.WITHOUT_USER_CODE, Message.WITHOUT_USER));
                        return false;
                    }
                    if(null == redisTemplate.opsForValue().get(userId)){
                        result(httpServletResponse,new ResultData(Code.EXPIRY_TOKEN_CODE, Message.EXPIRY_TOKEN));
                        return false;
                    }else {
                        if(!token.equals(redisTemplate.opsForValue().get(userId))){
                            result(httpServletResponse,new ResultData(Code.ACCOUNT_OFFLINE_CODE, Message.ACCOUNT_OFFLINE));
                            return false;
                        }
                    }
                } catch (JWTDecodeException j) {
                    result(httpServletResponse,new ResultData(Code.SERVER_EXCEPTION_CODE, Message.SERVER_EXCEPTION));
                    j.printStackTrace();
                }
                return true;
            }
        }
        return true;
    }
}
