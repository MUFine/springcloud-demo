package com.m.demo.config;

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

@Component
@Data
@ConfigurationProperties(prefix = "common")
public class WorkerIdConfig {
    private int workerId;
    private int datacenterId;
    @Bean
    public WorkerId getWorkerId(){
        return new WorkerId(workerId,datacenterId);
    }

}
