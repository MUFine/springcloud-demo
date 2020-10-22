package com.m.demo.service.serviceImpl;

import com.m.demo.entity.WorkerId;
import com.m.demo.mapper.TestMapper;
import com.m.demo.service.TestService;
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
    private final TestMapper testMapper;
    private final WorkerId workerId;

    public TestServiceImpl(TestMapper testMapper, WorkerId workerId) {
        this.testMapper = testMapper;
        this.workerId = workerId;
    }

    /*@Autowired
    private Redisson redisson;*/
    @Override
    public List<Map> getData() {
        return testMapper.getData();
    }

    @Override
    public int addProduct(Map map) {
        long id = workerId.nextId();
        map.put("id",id);
        return testMapper.addProduct(map);
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
