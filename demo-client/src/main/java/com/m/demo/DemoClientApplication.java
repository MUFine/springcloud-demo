package com.m.demo;

import com.m.demo.utils.WorkerIdUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.m.demo.mapper")
public class DemoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoClientApplication.class, args);
    }

    @Bean
    public WorkerIdUtil getWorkerId(){
        WorkerIdUtil workerIdUtil = new WorkerIdUtil(1,10);
        return workerIdUtil;
    }

    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        return (Redisson)Redisson.create(config);
    }
}
