package com.m.demo.service.serviceImpl;

import com.m.demo.dao.TestDao;
import com.m.demo.entity.WorkerId;
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
    @Autowired
    private WorkerId workerId;
    /*@Autowired
    private Redisson redisson;*/
    @Override
    public List<Map> getData() {
        return testDao.getData();
    }

    @Override
    public int addProduct(Map map) {
        long id = workerId.nextId();
        map.put("id",id);
        return testDao.addProduct(map);
    }

    @Override
    public int buyProduct(long productId) {
        /*RLock rLock = redisson.getLock(String.valueOf(productId));
        try {
            rLock.lock(30, TimeUnit.SECONDS);
        }finally {
            rLock.unlock();
        }*/
//        long id = IdUtil.getId();
//        map.put("id",id);
//        map.put("createTime",new Date());
        return 0;
    }

}
