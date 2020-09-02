package com.m.demo.service.serviceImpl;

import com.m.demo.common.Code;
import com.m.demo.common.Message;
import com.m.demo.dao.TestDao;
import com.m.demo.entity.ResultData;
import com.m.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/1 16:58
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    @Override
    public List<Map> getData() {
        return testDao.getData();
    }
}
