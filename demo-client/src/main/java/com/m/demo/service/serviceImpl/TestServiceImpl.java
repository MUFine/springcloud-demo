package com.m.demo.service.serviceImpl;

import com.m.demo.dao.TestDao;
import com.m.demo.service.TestService;
import com.m.demo.utils.IdUtil;
import com.m.demo.utils.WorkerIdUtil;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private WorkerIdUtil workerIdUtil;
    /*@Autowired
    private Redisson redisson;*/
    @Override
    public List<Map> getData() {
        return testDao.getData();
    }

    @Override
    public int addProduct(Map map) {
        long id = workerIdUtil.nextId();
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
