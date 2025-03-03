package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.BidProduct;
import com.bjpowernode.api.service.BidInfoService;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = BidInfoService.class,version = "1.0")
public class BidInfoServiceImpl implements BidInfoService {

    @Resource
    private BidInfoMapper bidInfoMapper;

    @Override
    public List<BidInfo> findByUid(Integer uid, Integer pageNo, Integer pageSize) {

        List<BidInfo> records = new ArrayList<>();

        if (uid != null && uid > 0){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            records = bidInfoMapper.selectById(uid,offset,pageSize);
        }
        return records;
    }
}
