package com.bjpowernode.front.controller;

import com.bjpowernode.common.enums.RCode;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.front.service.SmsService;
import com.bjpowernode.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "短信业务")
@RestController
@RequestMapping("/v1/sms")
public class SmsController extends BaseController{

    @Resource
    private SmsService smsService;

    @GetMapping("/code/register")
    @ApiOperation(value = "发送注册验证码短信")
    public RespResult sendCodeRegister(@RequestParam String phone){
        RespResult result = RespResult.fail();
        if (CommonUtil.checkPhone(phone)){ //检查电话格式
            result = RespResult.ok();
            String sCode = smsService.sendSms(phone);
            result.setObj(sCode);
        } else {
            result.setCode(RCode.PHONE_FORMAT_ERR);
        }
        return result;
    }
}
