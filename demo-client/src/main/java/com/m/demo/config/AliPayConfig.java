package com.m.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author:M
 * describe:支付宝支付参数配置类
 * date:2020/11/8 10:36
 */
@Component
@Data
@ConfigurationProperties(prefix="alipay")
public class AliPayConfig {
    private String appId;
    private String merchantPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;
    private String returnUrl;
    private String signType;
    private String charset;
    private String gatewayUrl;
    private String format;
}
