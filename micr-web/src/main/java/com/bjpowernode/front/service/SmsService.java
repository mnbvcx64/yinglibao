package com.bjpowernode.front.service;

public interface SmsService {

    String sendSms(String phone);

    boolean checkSmsService(String phone, String code);
}
