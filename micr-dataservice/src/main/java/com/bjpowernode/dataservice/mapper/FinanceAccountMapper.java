package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.FinanceAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

    FinanceAccount selectByUidForUpdate(@Param("uid") Integer uid);

    int updateAvalableMoneyByInvest(Integer uid, BigDecimal money);

    int updateAvailab(@Param("uid") Integer uid,
                      @Param("bidMoney") BigDecimal bidMoney,
                      @Param("incomeMoney") BigDecimal incomeMoney);
}