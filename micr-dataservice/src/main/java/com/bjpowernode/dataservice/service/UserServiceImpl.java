package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.FinanceAccount;
import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;
import com.bjpowernode.api.service.UserService;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@DubboService(interfaceClass = UserService.class,version = "1.0")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Value("${ylb.config.password-salt}")
    private String passWordSalt;

    @Resource
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public User queryBuPhone(String phone) {
        return userMapper.selectByUserPhone(phone);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized int userRegister(String phone, String password) {
        int result = 0; //默认参数不正确
        if (CommonUtil.checkPhone(phone) && password !=null && password.length() == 32){
            User queryUser = userMapper.selectByUserPhone(phone); //检查手机号是否已存在
            if ( queryUser ==null ){
                String newPassword = DigestUtils.md5Hex(password+passWordSalt);
                System.out.println("注册："+newPassword);
                //注册user
                User user = new User();
                user.setPhone(phone);
                user.setLoginPassword(password);
                user.setAddTime(new Date());
                userMapper.insertReturnPrimaryKey(user);

                //获取主键user_getId()
                FinanceAccount account = new FinanceAccount();
                account.setUid(user.getId());
                account.setAvailableMoney(new BigDecimal("1"));
                financeAccountMapper.insertSelective(account);
                result = 1; //1就表示成功
            } else {
                result = 2;
            }
        }
        return result;
    }

    //@Override
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User userLogin(String phone, String password) {
        User user = null;
        if (CommonUtil.checkPhone(phone) && (password != null && password.length() == 32)){
            String newPassword = DigestUtils.md5Hex(password + passWordSalt);
            System.out.println("newPassword为："+newPassword);
            user = userMapper.selectLogin(phone,password);
            //更新最后登陆时间
            if (user != null){
                user.setLastLoginTime(new Date());
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
         return user;
    }

    @Override
    public boolean modifyRealName(String phone, String name, String idCord) {
        int rows = 0;
        if (!StringUtils.isAnyBlank(phone,name,idCord)){
            rows = userMapper.updateRealName(phone,name,idCord);
        }
        return rows >0;
    }

    @Override
    public UserAccountInfo queryUserAllInfo(Integer uid) {
        UserAccountInfo info = null;
        if (uid != null && uid>0){
            info = userMapper.selectUserAccountById(uid);
        }
        return info;
    }

    @Override
    public User queryById(Integer uid) {
        User user = null;
        if (uid != null && uid >0){
            user = userMapper.selectByPrimaryKey(uid);
        }
        return user;
    }

    @Override
    public int userUpdate(String phone, String passWord) {
        String newPassWord = DigestUtils.md5Hex(passWord);
        return userMapper.updatePassword(phone,newPassWord);
    }
}
