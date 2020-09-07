package com.m.demo.common;

import com.m.demo.utils.MD5Util;

import java.util.UUID;

/**
 * author:M
 * describe:
 * date:2020/9/2 18:31
 */
public class Secret {
    public static final String TOKEN_SECRET = "606f561e-bdb4-47a3-980a-861b623e0c1e";
    public static final String GATEWAY_SECRET = "8561c669cf203b44adf2db0d553fd167";

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(MD5Util.getMd5(GATEWAY_SECRET,null));
    }

}
