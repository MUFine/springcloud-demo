package com.m.demo.mapper;

import com.m.demo.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/28 20:25
 */

@Mapper
public interface TestMapper {
    List<Map> getData();
    int addProduct(Map map);
    int addShopRecord(Map map);
    List<User> getUser();
}
