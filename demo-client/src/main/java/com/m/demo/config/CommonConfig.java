package com.m.demo.config;

import com.m.demo.entity.Secret;
import com.m.demo.entity.WorkerId;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author:M
 * describe:获取yml配置的参数
 * date:2020/10/11 15:36
 */

@Data
@Component
@ConfigurationProperties(prefix = "common")
public class CommonConfig {
    private int workerId;
    private int datacenterId;
    private String tokenSecret;
    private String gatewaySecret;
    @Bean
    public WorkerId getWorkerId(){
        return new WorkerId(workerId,datacenterId);
    }

    @Bean
    public Secret getSecret(){
        return new Secret(tokenSecret,gatewaySecret);
    }

}
