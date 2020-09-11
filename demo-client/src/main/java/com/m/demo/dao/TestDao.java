package com.m.demo.dao;

import com.m.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


/**
 * author:M
 * describe:
 * date:2020/9/1 17:00
 */
@Repository
public class TestDao {
    @Autowired
    private TestMapper testMapper;
    public List<Map> getData(){
        return testMapper.getData();
    }
    public int addProduct(Map map){
        return testMapper.addProduct(map);
    }
    public int addShopRecord(Map map){
        return testMapper.addShopRecord(map);
    }
}
