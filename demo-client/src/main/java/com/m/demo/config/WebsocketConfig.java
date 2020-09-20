package com.m.demo.config;

import org.springframework.context.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * author:M
 * describe:
 * date:2020/9/15 13:12
 */

@Configuration
public class WebsocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
