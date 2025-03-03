package com.bjpowernode.front.vo;

import com.bjpowernode.api.model.BidInfo;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

public class ResultVO {
    private Integer id;

    public String getProductName() {
        return productName;
    }

    public void setProduct_name(String productName) {
        this.productName = productName;
    }

    private String productName;
    private String rechargeDate = "-";
    private BigDecimal rechargeMoney;

    public ResultVO(BidInfo bidProduct){

        this.id = bidProduct.getId();
        this.rechargeMoney = bidProduct.getBidMoney();
        this.productName = bidProduct.getProductName();

        if (bidProduct.getBidTime() != null){
            rechargeDate = DateFormatUtils.format(bidProduct.getBidTime(),"yyyy-MM-dd");
        }
    }

    public Integer getId() {
        return id;
    }



    public String getRechargeDate() {
        return rechargeDate;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }
}
