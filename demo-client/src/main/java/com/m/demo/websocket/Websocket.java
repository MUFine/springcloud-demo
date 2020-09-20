package com.m.demo.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.*;

/**
 * author:M
 * describe:
 * date:2020/9/15 13:17
 */
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class Websocket {
    private Session session;
    private String userId;
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId){
        this.session = session;
        this.userId = userId;
        System.out.println("开启websocket!");
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("关闭websocket!");
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("收到的消息:" + message);
        sendMessage(message);
    }

    @OnError
    public void onError(Throwable error){
        System.out.println("websocket异常!");
    }

    public void sendMessage(String message){
        this.session.getAsyncRemote().sendObject(message);
    }
}
