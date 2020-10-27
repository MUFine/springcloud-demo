package com.m.demo.service.serviceImpl;

import com.m.demo.entity.ResultPage;
import com.m.demo.entity.WorkerId;
import com.m.demo.mapper.TestMapper;
import com.m.demo.service.TestService;
import com.m.demo.utils.PageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ResultPage getData(int pageNum, int pageSize) {
        return PageUtil.getPageInfo(()->testMapper.getData(),pageNum,pageSize);
    }
    @Override
    public ResultPage listToPage(int pageNum, int pageSize) {
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("1","1");
        list.add(map);
        map.put("2","2");
        list.add(map);
        map.put("3","3");
        list.add(map);
        map.put("4","4");
        list.add(map);
        return PageUtil.listToPage(list,pageNum,pageSize);
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
