package com.m.demo.service.serviceImpl;

import com.m.demo.service.WebsoketService;
import com.m.demo.websocket.Websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:M
 * describe:
 * date:2020/9/20 15:22
 */
@Service
public class WebsoketServiceImpl implements WebsoketService {
    @Autowired
    private Websocket websocket;
    @Override
    public void sendMessage(String message) {
        websocket.sendMessage(message);
    }
}
