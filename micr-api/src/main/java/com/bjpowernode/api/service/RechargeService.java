package com.bjpowernode.api.service;

import com.bjpowernode.api.model.RechargeRecord;

import java.util.List;

public interface RechargeService {
    List<RechargeRecord> findByUid(Integer uid, Integer pageNo, Integer pageSize);

    int addRechargeRecord(RechargeRecord record);
}
