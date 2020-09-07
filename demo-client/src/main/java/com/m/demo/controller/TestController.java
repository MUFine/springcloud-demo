package com.m.demo.controller;

import com.m.demo.annotation.Login;
import com.m.demo.annotation.Pass;
import com.m.demo.common.Code;
import com.m.demo.common.Message;
import com.m.demo.entity.ResultData;
import com.m.demo.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * author:M
 * describe:
 * date:2020/9/1 17:03
 */
@RestController
@RefreshScope
@Api(tags = "测试相关接口")
public class TestController {
    @Autowired
    private TestService testService;
    @Value("${test}")
    private String data;

    @ApiOperation("测试配置自动刷新接口")
    @GetMapping("/test")
    public String test(){
        return this.data;
    }

    @ApiOperation("测试数据接口")
    @GetMapping("/getData")
    @Pass
    public ResultData getData(){
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS,testService.getData());
    }


}
