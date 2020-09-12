package com.m.demo.service.serviceImpl;

import com.m.demo.common.Queue;
import com.m.demo.service.MQProviderService;
import org.springframework.amqp.core.AmqpTemplate;
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
    private AmqpTemplate amqpTemplate;

    @Override
    public void send(long userId,long productId) {
        Map map = new HashMap();
        map.put("date",new Date());
        map.put("userId",userId);
        map.put("productId",productId);
        amqpTemplate.convertAndSend(Queue.BUY_QUEUE_NAME,map);
    }

}
