package com.m.demo.service.serviceImpl;

import com.m.demo.common.CommonQueue;
import com.m.demo.service.MQProviderService;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/12 14:56
 */
@Component
public class MQProviderServiceImpl implements MQProviderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(long userId,long productId) {
        Map<Object,Object> map = new HashMap<>();
        map.put("date",new Date());
        map.put("userId",userId);
        map.put("productId",productId);
        rabbitTemplate.convertAndSend(CommonQueue.CUSTOM_EXCHANGE,CommonQueue.ROUTE_KEY,map,messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            //给消息设置延迟毫秒值
            messagePostProcessor.getMessageProperties().setDelay(10000);
            return messagePostProcessor;
        });
    }

}
