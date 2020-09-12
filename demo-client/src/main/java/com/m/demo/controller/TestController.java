package com.m.demo.controller;

import com.m.demo.annotation.Login;
import com.m.demo.annotation.Pass;
import com.m.demo.common.Code;
import com.m.demo.common.Message;
import com.m.demo.entity.ResultData;
import com.m.demo.service.MQProviderService;
import com.m.demo.service.TestService;
import com.m.demo.utils.IdUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @Autowired
    private MQProviderService mqProviderService;
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

    @ApiOperation("测试id接口")
    @GetMapping("/getId")
    public void getId(){
        System.out.println(IdUtil.getId());
    }

    @ApiOperation("添加商品接口")
    @PostMapping("/addProduct")
    public ResultData addProduct(@RequestBody Map map){
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS,testService.addProduct(map));
    }

    @ApiOperation("购买接口")
    @PostMapping("/buy/{userId}/{productId}")
    public ResultData addShopRecord(@PathVariable("userId") long userId,@PathVariable("productId") long productId){
        mqProviderService.send(userId,productId);
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS);
    }

}
