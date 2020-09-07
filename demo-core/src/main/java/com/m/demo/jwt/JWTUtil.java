package com.m.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.m.demo.common.Secret;
import com.m.demo.utils.MD5Util;

import java.util.Date;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/2 18:37
 */
public class JWTUtil {
    public static String getToken(Map<Object,String> map) {
        Date date = new Date();
        Long time = date.getTime();
        String token = JWT.create().withClaim("userId",map.get("userId"))
                .withClaim("loginName",map.get("loginName"))
                .withClaim("userType",map.get("userType"))
                .withClaim("date",time.toString())
                .sign(Algorithm.HMAC256(MD5Util.getMd5(Secret.TOKEN_SECRET,null)));
        return token;
    }

    public static Boolean verifyToken(String token,String secret){
        Boolean flag = true;
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            flag = false;
        }
        return flag;
    }
}
