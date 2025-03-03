package com.bjpowernode.api.service;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.BidProduct;

import java.util.List;

public interface BidInfoService {
    List<BidInfo> findByUid(Integer uid, Integer pageNo, Integer pageSize);
}
