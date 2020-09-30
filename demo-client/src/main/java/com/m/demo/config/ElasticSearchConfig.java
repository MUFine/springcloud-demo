package com.m.demo.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author:M
 * describe:elasticsearch的配置类
 * date:2020/9/27 20:40
 */
@Data
@Component
@ConfigurationProperties(prefix = "es.config")
public class ElasticSearchConfig {
    private boolean open;
    private String host;
    private int port;
    private String scheme;
    private int connTimeout;
    private int socketTimeout;
    private int connectionRequestTimeout;
    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(makeHttpHost());
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder(new HttpHost(host, port, scheme))
                .setRequestConfigCallback(restClientBuilder -> restClientBuilder
                        .setConnectTimeout(connTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout))
                .build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }

    private HttpHost makeHttpHost() {
        return new HttpHost(host, port, scheme);
    }

}
