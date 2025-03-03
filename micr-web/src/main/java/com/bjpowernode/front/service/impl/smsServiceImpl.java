package com.bjpowernode.front.service.impl;

import com.bjpowernode.common.constant.RedisKey;
import com.bjpowernode.common.format.date;
import com.bjpowernode.front.service.SmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service(value = "smsServiceImpl")
public class smsServiceImpl implements SmsService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String sendSms(String phone) {
        String random = RandomStringUtils.randomNumeric(6);
        System.out.println("短信验证码为："+random+" "+date.dateformat(new Date())+" "+phone);
        //将验证码存进redis
        String key = RedisKey.KEY_SMS_CODE_REG + phone;
        stringRedisTemplate.boundValueOps(key).set(random,3, TimeUnit.MINUTES);
        return random;
    }

    @Override
    public boolean checkSmsService(String phone, String code) {
        String key = RedisKey.KEY_SMS_CODE_REG + phone;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))){ // hasKey方法检查phone和code是否存在
            String querySmsCode = stringRedisTemplate.boundValueOps(key).get();
            return code.equals(querySmsCode);
        }
        return false;
    }
}
