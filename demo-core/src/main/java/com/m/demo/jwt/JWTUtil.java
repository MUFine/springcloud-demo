package com.m.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/2 18:37
 */
public class JWTUtil {
    public static String getToken(Map<Object,String> map,String secret,Long date) {
        String token = JWT.create().withClaim("userId",map.get("userId"))
                .withClaim("loginName",map.get("loginName"))
                .withClaim("userType",map.get("userType"))
                .withClaim("date",date.toString())
                .sign(Algorithm.HMAC256(secret));
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
