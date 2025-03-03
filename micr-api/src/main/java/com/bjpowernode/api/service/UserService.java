package com.bjpowernode.api.service;

import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;

public interface UserService {
    User queryBuPhone(String phone);

    /**用户注册**/
    int userRegister(String phone, String password);

    /**用户登录**/
    User userLogin(String phone,String password);

    /**用户实名认证**/
    boolean modifyRealName(String phone,String name,String idCord);

    /**用户详情页的用户信息和资金信息**/
    UserAccountInfo queryUserAllInfo(Integer uid);

    User queryById(Integer uid);

    int userUpdate(String phone, String sCode);
}
