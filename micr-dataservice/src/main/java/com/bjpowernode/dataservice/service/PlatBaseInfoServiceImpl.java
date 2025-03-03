package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.pojo.BaseInfo;
import com.bjpowernode.api.service.PlatBaseInfoService;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.UserMapper;
import com.bjpowernode.dataservice.mapper.productInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;

@DubboService(interfaceClass = PlatBaseInfoService.class, version = "1.0")
public class PlatBaseInfoServiceImpl implements PlatBaseInfoService {

    //注入mapper
    @Resource
    private UserMapper userMapper;
    @Resource
    private productInfoMapper productInfoMapper;
    @Resource
    private BidInfoMapper bidInfoMapper;

    @Override
    public BaseInfo queryPlatBaseInfo() {
        //那三个数据
        int registerUser = userMapper.selectCountUser();
        BigDecimal avgRate = productInfoMapper.selectAvgRate();
        BigDecimal sumBidMoney = bidInfoMapper.selectSumBidMoney();
        BaseInfo baseInfo = new BaseInfo(avgRate,sumBidMoney,registerUser);
        return baseInfo;
    }
    //平台基本信息


}
