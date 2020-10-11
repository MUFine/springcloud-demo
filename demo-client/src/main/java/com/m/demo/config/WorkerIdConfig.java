package com.m.demo.config;

import com.m.demo.utils.WorkerIdUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * author:M
 * describe:
 * date:2020/10/11 15:36
 */

@Data
@Component
@ConfigurationProperties(prefix = "id")
public class WorkerIdConfig {
    private int workerId;
    private int datacenterId;
    @Bean
    public WorkerIdUtil getWorkerId(){
        System.out.println("workerId = " + workerId);
        System.out.println("datacenterId = " + datacenterId);
        return new WorkerIdUtil(workerId,datacenterId);
    }
}
