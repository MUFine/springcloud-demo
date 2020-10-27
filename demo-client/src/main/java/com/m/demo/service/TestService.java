package com.m.demo.service;


import com.m.demo.entity.ResultPage;

import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/1 16:52
 */
public interface TestService {
    ResultPage getData(int pageNum, int pageSize);
    ResultPage listToPage(int pageNum, int pageSize);
    int addProduct(Map map);
    int buyProduct(long productId);
}
