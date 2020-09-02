package com.m.demo.controller;

import com.m.demo.common.Code;
import com.m.demo.common.Message;
import com.m.demo.entity.ResultData;
import com.m.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/1 17:03
 */
@RestController
@RefreshScope
public class TestController {
    @Autowired
    private TestService testService;
    @Value("${test}")
    private String data;

    @GetMapping("/test")
    public String test(){
        return this.data;
    }

    @GetMapping("/getData")
    public ResultData getData(){
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS,testService.getData());
    }


}
