package com.m.demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoClientApplicationTests {

    @Autowired
    private AmqpTemplate template;
    @Test
    void contextLoads() {
    }

}
