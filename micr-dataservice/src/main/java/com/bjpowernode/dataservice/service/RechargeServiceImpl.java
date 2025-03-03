package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.RechargeRecord;
import com.bjpowernode.api.service.RechargeService;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.dataservice.mapper.RechargeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = RechargeService.class,version = "1.0")
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public List<RechargeRecord> findByUid(Integer uid, Integer pageNo, Integer pageSize) {
        //分页查询
        List<RechargeRecord> records = new ArrayList<>();
        if (uid != null && uid > 0){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo-1)*pageSize;
            records = rechargeRecordMapper.selectByUid(uid,offset,pageSize);
        }
        return records;
    }

    @Override
    public int addRechargeRecord(RechargeRecord record) {
        return rechargeRecordMapper.insertSelective(record);
    }
}
