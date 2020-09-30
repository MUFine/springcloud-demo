package com.m.demo.mapper;

import java.util.List;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/28 20:25
 */

public interface TestMapper {
    List<Map> getData();
    int addProduct(Map map);
    int addShopRecord(Map map);
}
