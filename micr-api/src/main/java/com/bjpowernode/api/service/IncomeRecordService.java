package com.bjpowernode.api.service;

import com.bjpowernode.api.model.IncomeRecord;

import java.util.List;

public interface IncomeRecordService {
    List<IncomeRecord> findByUid(Integer uid,Integer pageNo,Integer pageSize);
}
