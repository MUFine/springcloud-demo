package com.m.demo.vo;

import com.m.demo.common.Code;
import com.m.demo.common.Message;
import com.m.demo.dao.TestDao;
import com.m.demo.entity.ResultData;
import org.springframework.stereotype.Component;

/**
 * author:M
 * describe:
 * date:2020/9/2 15:47
 */
@Component
public class FeignFallBack implements TestDao {
    @Override
    public ResultData getData() {
        return new ResultData(Code.FAIL_CODE, Message.FAIL,"服务器发生异常！");
    }
}
