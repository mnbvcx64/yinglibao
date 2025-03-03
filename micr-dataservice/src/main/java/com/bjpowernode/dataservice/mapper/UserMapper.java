package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //统计注册人数
    int selectCountUser();

    int insertReturnPrimaryKey(User user);

    User selectByUserPhone(@Param("phone") String phone);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //用户登录
    User selectLogin(@Param("phone") String phone,@Param("newPassword") String newPassword);

    //更新实名认证信息
     int updateRealName(@Param("phone")String phone,
                       @Param("name")String name,
                       @Param("idCard") String idCard);


    UserAccountInfo selectUserAccountById(@Param("uid") Integer uid);

    int updatePassword(@Param("phone") String phone,
                       @Param("passWord") String passWord);
}