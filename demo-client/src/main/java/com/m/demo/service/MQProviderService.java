package com.m.demo.service;

/**
 * author:M
 * describe:
 * date:2020/9/12 14:53
 */
public interface MQProviderService {
    void send(long userId,long productId);
}
