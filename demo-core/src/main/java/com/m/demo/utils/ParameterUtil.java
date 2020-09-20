package com.m.demo.utils;

import com.auth0.jwt.JWT;
import com.m.demo.annotation.*;
import org.springframework.core.MethodParameter;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * author:M
 * describe:给注解的参数赋值
 * date:2020/9/20 16:28
 */
public class ParameterUtil {
    public static Object getValue(MethodParameter methodParameter, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("authentication");// 从 http 请求头中取出 token
        long date = Long.valueOf(JWT.decode(token).getClaim("date").asString());
        if(methodParameter.hasParameterAnnotation(UserId.class)){
            return JWT.decode(token).getClaim("userId").asString();//获取用户id
        }else if (methodParameter.hasParameterAnnotation(LoginName.class)){
            return JWT.decode(token).getClaim("loginName").asString();//获取登录名
        }else if (methodParameter.hasParameterAnnotation(LoginDate.class)){
            return new Date(date);//获取登录时间
        }else if (methodParameter.hasParameterAnnotation(UserType.class)){
            return JWT.decode(token).getClaim("userType").asString();//获取用户类型
        }
        return null;
    }
}
