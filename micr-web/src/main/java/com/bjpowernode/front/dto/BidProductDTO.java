package com.bjpowernode.front.dto;

import com.bjpowernode.api.model.BidProduct;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;

public class BidProductDTO {
    private Integer id;

    private String productName;

    private BigDecimal bidInfoMoney;

    private String bidInfoTime;

    public BidProductDTO(BidProduct record){
        this.id = record.getLoan_id();
        this.productName = record.getProductName();
        this.bidInfoMoney = record.getBidMoney();

        if (record.getBidTime() != null ){
            bidInfoTime = DateFormatUtils.format(record.getBidTime(),"yyyy-MM-dd");
        }
    }

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

    public BigDecimal getBidInfoMoney() {
        return bidInfoMoney;
    }

    public void setBidInfoMoney(BigDecimal bidInfoMoney) {
        this.bidInfoMoney = bidInfoMoney;
    }

    public String getBidInfoTime() {
        return bidInfoTime;
    }

    public void setBidInfoTime(String bidInfoTime) {
        this.bidInfoTime = bidInfoTime;
    }
}