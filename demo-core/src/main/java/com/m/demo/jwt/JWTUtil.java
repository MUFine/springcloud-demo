package com.m.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.m.demo.utils.MD5Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/2 18:37
 */
public class JWTUtil {
    private static final long VALID_TIME = 24 * 60 * 60 * 1000;//有效时长一天
    public static String getToken(Map<Object,String> map,String tokenSecret,Date loginTime) {
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        long time = loginTime.getTime();
        Date expireTime = new Date(VALID_TIME + time);
        String token = JWT.create().withHeader(header)
                .withClaim("userId",map.get("userId"))
                .withClaim("loginName",map.get("loginName"))
                .withClaim("userType",map.get("userType"))
                .withClaim("date", Long.toString(time))
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC256(tokenSecret));
        return token;
    }

    public static boolean verifyToken(String token,String secret){
        boolean flag = true;
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            flag = false;
        }
        return flag;
    }
}
