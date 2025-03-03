package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    //查询充值记录
    List<RechargeRecord> selectByUid(@Param("uid") Integer uid,
                                     @Param("offset") int offset,
                                     @Param("rows") Integer rows);
}