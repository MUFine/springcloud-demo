package com.m.demo.controller;

import com.m.demo.entity.ResultData;
import com.m.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:M
 * describe: feign带有负载均衡的效果
 * date:2020/9/2 15:35
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/getData")
    public ResultData getData(){
        return testService.getData();
    }
}
