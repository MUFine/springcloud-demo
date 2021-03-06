package com.m.demo.config;

import com.m.demo.wxpay.IWXPayDomain;
import com.m.demo.wxpay.WXPayConfig;
import com.m.demo.wxpay.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * author:M
 * describe:微信支付配置类
 * date:2020/11/7 17:58
 */



@Component
public class WXPayDataConfig extends WXPayConfig {
    private byte[] certData;
    @Value("${wxpay.appId}")
    private String appId ;  //公众账号ID
    @Value("${wxpay.mchId}")
    private String mchId ;  //商户号
    @Value("${wxpay.key}")
    private String key ;    //为商户平台设置的密钥key 用作计算请求支付接口的签名 详见微信接口的签名算法要求
    @Value("${wxpay.httpConnectTimeoutMs}")
    private int httpConnectTimeoutMs ;
    @Value("${wxpay.httpReadTimeoutMs}")
    private int httpReadTimeoutMs ;
    @Value("${wxpay.certPath}")
    private String certPath ;//商户证书的路径
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

    public String getAppID() {
        return this.appId;
    }

    public String getMchID() {
        return this.mchId;
    }

    public String getKey() { return this.key; }

    public InputStream getCertStream() { return new ByteArrayInputStream(this.certData); }

    // 这个方法需要这样实现, 否则无法正常初始化WXPay
    @Override
    public IWXPayDomain getWXPayDomain() {
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
