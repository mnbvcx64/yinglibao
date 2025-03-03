package com.bjpowernode.pay.service;

import com.bjpowernode.api.model.RechargeRecord;
import com.bjpowernode.api.model.User;
import com.bjpowernode.api.service.RechargeService;
import com.bjpowernode.api.service.UserService;
import com.bjpowernode.common.constant.RedisKey;
import com.bjpowernode.common.constant.YLBConstant;
import com.bjpowernode.util.Pkipair;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.jute.Record;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class KQService {

    private final StringRedisTemplate stringRedisTemplate;

    @DubboReference(interfaceClass = UserService.class,version = "1.0")
    private UserService userService;

    @DubboReference(interfaceClass = RechargeService.class,version = "1.0")
    private RechargeService rechargeService;

    public KQService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public User queryUser(Integer uid){
        return userService.queryById(uid);
    }
    //生成块钱支付的接口数据,Map是发送给块钱的所有请求参数
    public Map<String,String> generateDateFormDate(Integer uid, String phone, BigDecimal money){
        Map<String,String> data = new HashMap<>();
        //人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
        String merchantAcctId = "1001214035601";//
        //编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
        String inputCharset = "1";
        //接收支付结果的页面地址，该参数一般置为空即可。
        String pageUrl = "";
        //服务器接收支付结果的后台地址，该参数务必填写，不能为空。
        String bgUrl = "http://localhost:9000/pay/kq/rect/notify";
        //网关版本，固定值：v2.0,该参数必填。
        String version =  "v2.0";
        //语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
        String language =  "1";
        //签名类型,该值为4，代表PKI加密方式,该参数必填。
        String signType =  "4";
        //支付人姓名,可以为空。
        String payerName= "";
        //支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
        String payerContactType =  "2";
        //支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
        String payerContact =  phone;
        //指定付款人，可以为空
        String payerIdType =  "3";
        //付款人标识，可以为空
        String payerId =  "kq33151000";//String.valueOf(uid);
        //付款人IP，可以为空
        String payerIP =  "";
        //商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
        String orderId = "KQ"+generateProductId();
        //订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填。
        String orderAmount = money.multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
        //订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
        String orderTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        //快钱时间戳，格式：yyyyMMddHHmmss，如：20071117020101， 可以为空
        String orderTimestamp= new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());;
        //商品名称，可以为空。
        String productName= "动力理财产品";
        //商品数量，可以为空。
        String productNum = "1";
        //商品代码，可以为空。
        String productId = "10000";
        //商品描述，可以为空。
        String productDesc = "购买产品";
        //扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext1 = "";
        //扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext2 = "";
        //支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10-1或10-2，必填。
        String payType = "00";
        //银行代码，如果payType为00，该值可以为空；如果payType为10-1或10-2，该值必须填写，具体请参考银行列表。
        String bankId = "";
        //同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
        String redoFlag = "0";
        //快钱合作伙伴的帐户号，即商户编号，可为空。
        String pid = "";

        // signMsg 签名字符串 不可空，生成加密签名串
        String signMsgVal = "";
        signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset,data);
        signMsgVal = appendParam(signMsgVal, "pageUrl", pageUrl,data);
        signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl,data);
        signMsgVal = appendParam(signMsgVal, "version", version,data);
        signMsgVal = appendParam(signMsgVal, "language", language,data);
        signMsgVal = appendParam(signMsgVal, "signType", signType,data);
        //signMsgVal = appendParam(signMsgVal,"signMsg",signMsg,data);
        signMsgVal = appendParam(signMsgVal, "merchantAcctId",merchantAcctId,data);
        signMsgVal = appendParam(signMsgVal, "payerName", payerName,data);
        signMsgVal = appendParam(signMsgVal, "payerContactType",payerContactType,data);
        signMsgVal = appendParam(signMsgVal, "payerContact", payerContact,data);
        signMsgVal = appendParam(signMsgVal, "payerIdType", payerIdType,data);
        signMsgVal = appendParam(signMsgVal, "payerId", payerId,data);
        signMsgVal = appendParam(signMsgVal, "payerIP", payerIP,data);
        signMsgVal = appendParam(signMsgVal, "orderId", orderId,data);
        signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount,data);
        signMsgVal = appendParam(signMsgVal, "orderTime", orderTime,data);
        signMsgVal = appendParam(signMsgVal, "orderTimestamp", orderTimestamp,data);
        signMsgVal = appendParam(signMsgVal, "productName", productName,data);
        signMsgVal = appendParam(signMsgVal, "productNum", productNum,data);
        signMsgVal = appendParam(signMsgVal, "productId", productId,data);
        signMsgVal = appendParam(signMsgVal, "productDesc", productDesc,data);
        signMsgVal = appendParam(signMsgVal, "ext1", ext1,data);
        signMsgVal = appendParam(signMsgVal, "ext2", ext2,data);
        signMsgVal = appendParam(signMsgVal, "payType", payType,data);
        signMsgVal = appendParam(signMsgVal, "bankId", bankId,data);
        signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag,data);
        signMsgVal = appendParam(signMsgVal, "pid", pid,data);



        System.out.println(signMsgVal);
        Pkipair pki = new Pkipair();
        //生成签名
        String signMsg = pki.signMsg(signMsgVal);
        //需要signMsg
        data.put("signMsg",signMsg);
        return data;
    }

    private String appendParam(String returns,String paramId, String paramValue, Map<String,String> data){
        if (returns != "") {
            if (paramValue != "" && paramValue != null) {

                returns += "&" + paramId + "=" + paramValue;
            }

        } else {

            if (paramValue != "" && paramValue != null) {
                returns = paramId + "=" + paramValue;
            }
        }
        if (data != null){
            data.put(paramId,paramValue);
        }

        return returns;
    }

    //生成orderId
    public String generateProductId(){
        String key = RedisKey.KEY_RECHARGE_ORDERID;
        Long incr = stringRedisTemplate.boundValueOps(key).increment();
        return DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS") + incr;
    }

    public boolean addRecharge(Integer uid, BigDecimal money, String orderId) {
        RechargeRecord record = new RechargeRecord();
        record.setChannel("KQ");
        record.setRechargeDesc("块钱充值");
        record.setRechargeNo(orderId);
        record.setUid(uid);
        record.setRechargeMoney(money);
        record.setRechargeTime(new Date());
        record.setRechargeStatus(YLBConstant.INCOME_STATUS_BACK);

        int rows = rechargeService.addRechargeRecord(record);

        return rows > 0;
    }

    public void addOrderIdRedis(String orderId){
        String key = RedisKey.KEY_ORDERID_SET;
        stringRedisTemplate.boundZSetOps(key).add(orderId,new Date().getTime());
    }
}
