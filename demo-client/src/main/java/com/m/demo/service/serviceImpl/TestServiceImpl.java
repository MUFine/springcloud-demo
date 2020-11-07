package com.m.demo.service.serviceImpl;

import com.m.demo.config.WXPayDataConfig;
import com.m.demo.entity.ResultPage;
import com.m.demo.entity.WorkerId;
import com.m.demo.mapper.TestMapper;
import com.m.demo.service.TestService;
import com.m.demo.utils.PageUtil;
import com.m.demo.wxpay.WXPay;
import com.m.demo.wxpay.WXPayUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private final WXPayDataConfig wxPayDataConfig;

    public TestServiceImpl(TestMapper testMapper, WorkerId workerId, WXPayDataConfig wxPayDataConfig) {
        this.testMapper = testMapper;
        this.workerId = workerId;
        this.wxPayDataConfig = wxPayDataConfig;
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

    @Override
    public String wxpay() {
        try {
            WXPay wxpay = new WXPay(wxPayDataConfig);
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "购买苹果10个");
            data.put("out_trade_no", "2016090910595900000012");//商户订单号
            data.put("total_fee", "1");//标价金额
            data.put("spbill_create_ip", "123.12.12.123");//终端IP(可填服务器ip)
            data.put("notify_url", "http://www.example.com/wxpay/notify");//异步接收微信支付结果通知的回调地址
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", "12");//商品ID
            Map<String, String> resp = wxpay.unifiedOrder(data);
            String returnCode = resp.get("return_code");
            if("SUCCESS".equals(returnCode)){
                String codeUrl = resp.get("code_url");
                return codeUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void wxpayNotify(HttpServletRequest request) {
        try {
            Map<String, String> notifyMap = WXPayUtil.getNotifyParameter(request);
            WXPay wxpay = new WXPay(wxPayDataConfig);
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                // 签名正确
                // 进行处理。
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
            }
            else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
