package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.IncomeRecord;
import com.bjpowernode.api.service.IncomeRecordService;
import com.bjpowernode.dataservice.mapper.IncomeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = IncomeRecordService.class,version = "1.0")
public class IncomeRecordServiceImpl implements IncomeRecordService {

    @Resource
    private IncomeRecordMapper incomeRecordMapper;

    @Override
    public List<IncomeRecord> findByUid(Integer uid, Integer pageNo, Integer pageSize) {

        List<IncomeRecord> records = new ArrayList<>();

        if (uid != null && uid > 0){
            records = incomeRecordMapper.selectIncomeRecord(uid,pageNo,pageSize);
        }
        return records;
    }
}
