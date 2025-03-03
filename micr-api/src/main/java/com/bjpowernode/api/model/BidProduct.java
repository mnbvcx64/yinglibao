package com.bjpowernode.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BidProduct implements Serializable {
    private Integer id;

    private Integer loan_id;

    private Integer uid;

    private BigDecimal bidMoney;

    private Date bidTime;

    private Integer bidStatus;

    private String productName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(Integer loan_id) {
        this.loan_id = loan_id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public Integer getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(Integer bidStatus) {
        this.bidStatus = bidStatus;
    }
}
