package com.m.demo.entity;

/**
 * author:M
 * describe:秘钥实体
 * date:2020/10/11 16:11
 */
public class Secret {
    private String tokenSecret;
    private String gatewaySecret;

    public Secret(String tokenSecret, String gatewaySecret) {
        this.tokenSecret = tokenSecret;
        this.gatewaySecret = gatewaySecret;
    }
    public String getTokenSecret(){
        return this.tokenSecret;
    }
    public String getGatewaySecret(){
        return this.gatewaySecret;
    }
}
