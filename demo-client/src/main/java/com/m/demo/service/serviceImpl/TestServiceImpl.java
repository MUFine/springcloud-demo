package com.m.demo.service.serviceImpl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.m.demo.config.AliPayConfig;
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
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private final AliPayConfig aliPayConfig;

    public TestServiceImpl(TestMapper testMapper, WorkerId workerId, WXPayDataConfig wxPayDataConfig, AliPayConfig aliPayConfig) {
        this.testMapper = testMapper;
        this.workerId = workerId;
        this.wxPayDataConfig = wxPayDataConfig;
        this.aliPayConfig = aliPayConfig;
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
                return resp.get("code_url");
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

    @Override
    public void alipay(HttpServletResponse httpResponse) throws Exception {
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = "商户订单号";
        //付款金额，必填
        String totalAmount = "付款金额";
        //订单名称，必填
        String subject = "订单名称";
        //商品描述，可空
        String body = "商品描述";
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeoutExpress = "30m";
        // (必填) 商户门店编号
        String storeId = "123";
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getGatewayUrl(), aliPayConfig.getAppId(), aliPayConfig.getMerchantPrivateKey(), aliPayConfig.getFormat(), aliPayConfig.getCharset(), aliPayConfig.getAlipayPublicKey(), aliPayConfig.getSignType());
        //设置请求参数
        AlipayTradePrecreateModel alipayModel = new AlipayTradePrecreateModel();
        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
        alipayModel.setOutTradeNo(outTradeNo);
        alipayModel.setTotalAmount(totalAmount);
        alipayModel.setSubject(subject);
        alipayModel.setBody(body);
        alipayModel.setTimeoutExpress(timeoutExpress);
        alipayModel.setAlipayStoreId(storeId);
        alipayRequest.setReturnUrl(aliPayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());
        alipayRequest.setBizModel(alipayModel);
        AlipayTradePrecreateResponse alipayResponse=alipayClient.execute(alipayRequest);
        String getQrCode=alipayResponse.getQrCode();
        makeQRCode(getQrCode,httpResponse.getOutputStream(),200,200);
    }

    @Override
    public void alipayNotify(HttpServletRequest request) throws Exception{
        if (signCheck(request.getParameterMap())){
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        }
    }

    //生成二维码
    private void makeQRCode(String content, OutputStream outputStream,int width,int height) throws Exception{
        Map<EncodeHintType,Object> map=new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        map.put(EncodeHintType.MARGIN,2);
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bitMatrix=qrCodeWriter.encode(content, BarcodeFormat.QR_CODE,width,height,map);
        MatrixToImageWriter.writeToStream(bitMatrix,"jpeg",outputStream);
    }

    //对notify参数进行验签
    private boolean signCheck(Map<String,String[]> requestParams) throws Exception{
        Map<String,String> params = new HashMap<>();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
            System.out.println(name+" ==> "+valueStr);
        }
        //调用SDK验证签名
        return AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlipayPublicKey(),
                aliPayConfig.getCharset(), aliPayConfig.getSignType());
    }
}
