package com.m.demo.controller;

import com.m.demo.Entity.Product;
import com.m.demo.annotation.*;
import com.m.demo.common.Code;
import com.m.demo.common.Message;
import com.m.demo.entity.ResultData;
import com.m.demo.jwt.JWTUtil;
import com.m.demo.service.ElasticSearchService;
import com.m.demo.service.MQProviderService;
import com.m.demo.service.TestService;
import com.m.demo.service.WebsoketService;
import com.m.demo.utils.DateUtil;
import com.m.demo.utils.IdUtil;
import com.m.demo.utils.ImageCodeUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/1 17:03
 */
@RestController
@RefreshScope
@Slf4j
@Api(tags = "测试相关接口")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private MQProviderService mqProviderService;
    @Autowired
    private WebsoketService WebsoketService;
    @Autowired
    private ElasticSearchService elasticSearchService;
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

    @ApiOperation("websocket接口")
    @PostMapping("/websocket")
    public ResultData websocket(@RequestBody String message){
        WebsoketService.sendMessage(message);
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS);
    }

    @ApiOperation("登录测试接口")
    @PostMapping("/login")
    public ResultData login(){
        Map<Object,String> map = new HashMap<>();
        map.put("userId","1");
        map.put("loginName","loginName");
        map.put("userType","userType");
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS, JWTUtil.getToken(map));
    }

    @ApiOperation("自定义注解测试接口")
    @PostMapping("/userId")
    public void userId(@UserId String userId, @LoginDate Date date){
        System.out.println("userId的值为:" + userId);
        System.out.println("date的值为:" + date);
        System.out.println("登录时间为:" + DateUtil.dateToStr(date,1));
    }

    @ApiOperation("es的索引创建测试接口")
    @PutMapping("/createIndex/{index}")
    public ResultData createIndex(@PathVariable("index") String index) throws IOException {
        return new ResultData(Code.SUCCESS_CODE, elasticSearchService.createIndex(index));
    }

    @ApiOperation("es的添加测试接口")
    @PutMapping("/putESData")
    public ResultData putESData(@RequestBody Product product) throws IOException {
        return new ResultData(Code.SUCCESS_CODE, elasticSearchService.putData(product));
    }

    @ApiOperation("es的编辑测试接口")
    @PostMapping("/updateESData/{id}")
    public ResultData updateESData(@PathVariable("id") String id,@RequestBody Product product) throws IOException {
        return new ResultData(Code.SUCCESS_CODE, elasticSearchService.updateData(product,id));
    }

    @ApiOperation("es的删除测试接口")
    @DeleteMapping("/deleteESDataById/{id}")
    public ResultData deleteESDataById(@PathVariable("id") String id) throws IOException {
        return new ResultData(Code.SUCCESS_CODE, elasticSearchService.deleteDataById(id));
    }

    @ApiOperation("es的根据主题查询测试接口(分词查询)")
    @GetMapping("/getESDataByTitle/{page}/{size}")
    public ResultData getESDataByTitle(@PathVariable("page") int page,@PathVariable("size") int size,@RequestParam String title) throws IOException {
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS,elasticSearchService.getDataByTitle(title,page,size));
    }

    @ApiOperation("es的根据品牌查询测试接口(高亮精确查询)")
    @GetMapping("/getESDataByBrand/{page}/{size}")
    public ResultData getESDataByBrand(@PathVariable("page") int page,@PathVariable("size") int size,@RequestParam String brand) throws IOException {
        return new ResultData(Code.SUCCESS_CODE, Message.SUCCESS,elasticSearchService.getDataByBrand(brand,page,size));
    }

    @ApiOperation("获取图片验证码接口")
    @GetMapping("/getImageCode")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response) {
        ImageCodeUtil.getVerifyCode(request,response);
    }
}
