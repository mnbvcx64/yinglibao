package com.bjpowernode.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IncomeRecord implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer loanId;

    private Integer bidId;

    private BigDecimal bidMoney;

    private Date incomeDate;

    private BigDecimal incomeMoney;

    private Integer incomeStatus;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    private Integer prodId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Integer getIncomeStatus() {
        return incomeStatus;
    }

    public void setIncomeStatus(Integer incomeStatus) {
        this.incomeStatus = incomeStatus;
    }

    @Override
    public String toString() {
        return "IncomeRecord{" +
                "id=" + id +
                ", uid=" + uid +
                ", loanId=" + loanId +
                ", bidId=" + bidId +
                ", bidMoney=" + bidMoney +
                ", incomeDate=" + incomeDate +
                ", incomeMoney=" + incomeMoney +
                ", incomeStatus=" + incomeStatus +
                ", prodId=" + prodId +
                '}';
    }
}