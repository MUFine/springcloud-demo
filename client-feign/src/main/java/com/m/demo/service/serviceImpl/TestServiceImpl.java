package com.m.demo.service.serviceImpl;

import com.m.demo.dao.TestDao;
import com.m.demo.entity.ResultData;
import com.m.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:M
 * describe:
 * date:2020/9/2 15:34
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    @Override
    public ResultData getData() {
        return testDao.getData();
    }
}
