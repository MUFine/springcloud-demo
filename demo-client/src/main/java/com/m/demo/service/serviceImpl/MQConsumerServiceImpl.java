package com.m.demo.service.serviceImpl;

import com.m.demo.common.RabbitConstant;
import com.m.demo.service.MQConsumerService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/12 14:56
 */
@Component
@RabbitListener(queues = RabbitConstant.BUY_QUEUE_NAME)
public class MQConsumerServiceImpl implements MQConsumerService {
    @Override
    @RabbitHandler
    public void receive(Map<Object,Object> map, Channel channel, Message message) {
        System.out.println("---------------------");
        System.out.println("接收到的消息:" + map);
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            System.out.println("接收出错!");
            e.printStackTrace();
        }
    }
}
