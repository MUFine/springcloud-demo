package com.m.demo.controller;

import com.m.demo.entity.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * author:M
 * describe:
 * date:2020/9/2 14:59
 */
@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getData")
    public ResultData getData(){
        return restTemplate.getForObject("http://demo-client/getData",ResultData.class);
    }
}
