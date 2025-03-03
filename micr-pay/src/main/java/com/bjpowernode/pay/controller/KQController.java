package com.bjpowernode.pay.controller;

import com.bjpowernode.api.model.User;
import com.bjpowernode.pay.service.KQService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/kq")
public class KQController {

    @Resource
    private KQService kqService;

    @GetMapping("/race/recharge")
    public String RaceFrontRechargeKQ(Integer uid, BigDecimal money, Model model){
        String view = "err";
        if (uid != null && uid > 0 && money != null && money.doubleValue()>0){

            //检查用户是否存在
            User user = kqService.queryUser(uid);
            if (user != null){
                System.out.println(user.getName());
                //快钱支付接口需要的请求参数
                Map<String,String> data = kqService.generateDateFormDate(uid,user.getPhone(),money);
                model.addAllAttributes(data);
                kqService.addRecharge(uid,money,data.get("orderId"));
                //把订单号存入redis
                kqService.addOrderIdRedis(data.get("orderId"));
                //提交支付请求给块钱的form页面
                view = "kqFrom";
            }
        }
        return view;
    }

    @GetMapping("/rect/notify")
    public String payResultNotify(){
        return "OK";
    }
}
