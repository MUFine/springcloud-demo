package com.m.demo.service;


import com.alipay.api.AlipayApiException;
import com.m.demo.entity.ResultPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * author:M
 * describe:
 * date:2020/9/1 16:52
 */
public interface TestService {
    ResultPage getData(int pageNum, int pageSize);
    ResultPage listToPage(int pageNum, int pageSize);
    int addProduct(Map map);
    int buyProduct(long productId);
    String wxpay();
    void wxpayNotify(HttpServletRequest request);
    void alipay(HttpServletResponse httpResponse) throws AlipayApiException, Exception;
    void alipayNotify(HttpServletRequest request) throws Exception;
}
