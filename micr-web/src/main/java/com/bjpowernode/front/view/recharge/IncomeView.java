package com.bjpowernode.front.view.recharge;

import com.bjpowernode.api.model.IncomeRecord;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

public class IncomeView {
    private Integer id;

    private String ProductName;

    private String IncomeDate;

    private BigDecimal IncomeMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getIncomeDate() {
        return IncomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        IncomeDate = incomeDate;
    }

    public BigDecimal getIncomeMoney() {
        return IncomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        IncomeMoney = incomeMoney;
    }

    public IncomeView(IncomeRecord incomeRecord){
        this.id = incomeRecord.getId();
        this.ProductName = incomeRecord.getProductName();
        this.IncomeMoney = incomeRecord.getIncomeMoney();

        this.IncomeDate = DateFormatUtils.format(incomeRecord.getIncomeDate(),"yyyy-MM-dd");
    }
}
