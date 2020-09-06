package com.m.demo.jwt;

import com.auth0.jwt.JWT;

import javax.servlet.http.HttpServletRequest;

/**
 * author:M
 * describe:
 * date:2020/3/16 14:46
 */
public class UserUtil {
    public static String getUserId(HttpServletRequest request){
        return getInfo(request,"userId");
    }
    public static String getLoginName(HttpServletRequest request){
        return getInfo(request,"loginName");
    }
    public static String getUserType(HttpServletRequest request){
        return getInfo(request,"userType");
    }
    public static String getDate(HttpServletRequest request){
        return getInfo(request,"date");
    }

    private static String getInfo(HttpServletRequest request,String info){
        String token = request.getHeader("authentication");// 从 http 请求头中取出 token
        return JWT.decode(token).getClaim(info).asString();
    }
}
