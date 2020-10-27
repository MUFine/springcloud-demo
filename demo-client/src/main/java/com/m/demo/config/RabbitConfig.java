package com.m.demo.config;


import com.m.demo.common.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author:M
 * describe:rabbitmq配置类
 * date:2020/9/25 16:30
 */
@Configuration
public class RabbitConfig {

    //定义队列
    @Bean
    public Queue myQueue() {
        return new Queue(RabbitConstant.BUY_QUEUE_NAME,true);
    }

    //给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息,延时交换机
    @Bean
    public CustomExchange customExchange() {
        CustomExchange customExchange = new CustomExchange(RabbitConstant.CUSTOM_EXCHANGE, "direct",true,false);
        customExchange.setDelayed(true);
        return customExchange;
    }

    //队列绑定交换机
    @Bean
    public Binding bindingExchange() {
        return BindingBuilder.bind(myQueue()).to(customExchange()).with(RabbitConstant.ROUTE_KEY).noargs();
    }

    /**
     * 定制amqp模版
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调即消息发送不到任何一个队列中  ack
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> System.out.println("消息:" + message + ";应答码:" + replyCode + ";原因:" + replyText + ";交换机:" + exchange + ";路由键:" + routingKey));
        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("消息发送失败" + cause + correlationData);
            } else {
                System.out.println("消息发送成功 ");
            }
        });
        return rabbitTemplate;
    }

}
