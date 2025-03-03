package com.bjpowernode.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseInfo implements Serializable {
    //收益率平均值
    private BigDecimal historyAvgBate;
    //累计成交金额
    private BigDecimal sumBidMoney;
    //注册人数
    private Integer registerUsers;

    public BaseInfo() {
    }

    public BaseInfo(BigDecimal historyAvgBate, BigDecimal sumBidMoney, Integer registerUsers) {
        this.historyAvgBate = historyAvgBate;
        this.sumBidMoney = sumBidMoney;
        this.registerUsers = registerUsers;
    }

    public BigDecimal getHistoryAvgBate() {
        return historyAvgBate;
    }

    public void setHistoryAvgBate(BigDecimal historyAvgBate) {
        this.historyAvgBate = historyAvgBate;
    }

    public BigDecimal getSumBidMoney() {
        return sumBidMoney;
    }

    public void setSumBidMoney(BigDecimal sumBidMoney) {
        this.sumBidMoney = sumBidMoney;
    }

    public Integer getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(Integer registerUsers) {
        this.registerUsers = registerUsers;
    }
}
