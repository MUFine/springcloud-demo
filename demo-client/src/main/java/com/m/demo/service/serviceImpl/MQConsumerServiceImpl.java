package com.m.demo.service.serviceImpl;

import com.m.demo.common.Queue;
import com.m.demo.service.MQConsumerService;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/12 14:56
 */
@Component
@RabbitListener(queues = Queue.BUY_QUEUE_NAME)
public class MQConsumerServiceImpl implements MQConsumerService {
    @Override
    @RabbitHandler
    public void receive(Map map) {
        System.out.println("接收到的消息:" + map);
        System.out.println("---------------------");
    }
}
