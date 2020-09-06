package com.m.demo.dao;

import com.m.demo.entity.ResultData;
import com.m.demo.vo.FeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * author:M
 * describe:
 * date:2020/9/2 15:33
 */
@FeignClient(value = "demo-client",fallback = FeignFallBack.class)
public interface TestDao {
    @RequestMapping(value = "/getData", method= RequestMethod.GET)
    ResultData getData() ;
}
