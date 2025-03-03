package com.bjpowernode.front.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RealNameServiceImpl {

    public boolean handleRealName(String phone,String name,String idCard){
        boolean realName = false;
        Map<String,String> params = new HashMap<>();
        params.put("cardNo",idCard);
        params.put("realName",name);

        return realName;
    }
}
