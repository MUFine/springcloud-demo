package com.m.demo.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/12 14:55
 */
public interface MQConsumerService {
    void receive(Map<Object,Object> map, Channel channel, Message message);
}
