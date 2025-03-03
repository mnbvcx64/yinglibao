package com.bjpowernode.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BidInfo implements Serializable {
    private Integer id;

    private Integer loanId;

    private Integer uid;

    private String result;

    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProdId() {
        return produId;
    }

    public void setProdId(Integer produId) {
        this.produId = produId;
    }

    private Integer produId;

    private BigDecimal bidMoney;

    private Date bidTime;

    private Integer bidStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
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



    public Integer getProduId() {
        return produId;
    }

    public void setProduId(Integer produId) {
        this.produId = produId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}