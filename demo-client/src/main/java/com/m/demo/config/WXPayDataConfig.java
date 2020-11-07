package com.m.demo.config;

import com.m.demo.wxpay.IWXPayDomain;
import com.m.demo.wxpay.WXPayConfig;
import com.m.demo.wxpay.WXPayConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * author:M
 * describe:微信支付配置类
 * date:2020/11/7 17:58
 */


@Component
@Data
@ConfigurationProperties(prefix="wxpay")
public class WXPayDataConfig extends WXPayConfig {
    private byte[] certData;
    private String starid;
    private String appid ;  //公众账号ID
    private String mchID ;  //商户号
    private String key ;    //为商户平台设置的密钥key 用作计算请求支付接口的签名 详见微信接口的签名算法要求
    private int httpConnectTimeoutMs ;
    private int httpReadTimeoutMs ;
    private String certPath ;//安全证书的路径
    public WXPayDataConfig() {
        File file = new File(certPath);
        try {
            InputStream certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getStarId() {
        return this.starid;
    }

    public String getAppID() {
        return this.appid;
    }

    public String getMchID() {
        return this.mchID;
    }

    public String getKey() {
        return this.key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return this.httpConnectTimeoutMs;
    }

    public int getHttpReadTimeoutMs() {
        return this.httpReadTimeoutMs;
    }

    @Override
    public IWXPayDomain getWXPayDomain() { // 这个方法需要这样实现, 否则无法正常初始化WXPay
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API2, true);
            }
        };
        return iwxPayDomain;
    }
}
