package com.m.demo.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * author:M
 * describe:
 * date:2020/3/6 11:00
 */
@Component
public class GatewayFilter implements GlobalFilter, Ordered {
    private final String GATEWAY_SECRET = "8561c669cf203b44adf2db0d553fd167";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("过滤器开始启用！");
        ServerHttpRequest req = exchange.getRequest().mutate()
                .header("gateway", GATEWAY_SECRET).build();
        return chain.filter(exchange.mutate().request(req.mutate().build()).build());
    }
    @Override
    public int getOrder() {
        return 2020;
    }
}
