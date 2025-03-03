package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.IncomeRecord;
import com.bjpowernode.api.model.productInfo;
import com.bjpowernode.api.service.IncomeService;
import com.bjpowernode.common.constant.YLBConstant;
import com.bjpowernode.common.format.date;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.IncomeRecordMapper;
import com.bjpowernode.dataservice.mapper.productInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = IncomeService.class,version = "1.0")
public class IncomeServiceImpl implements IncomeService {

    @Resource
    private productInfoMapper productInfoMapper;

    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private IncomeRecordMapper incomeRecordMapper;

    @Resource
    private FinanceAccountMapper accountMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateIncomePlan() {
        Date now = new Date();
        Date beginTime = DateUtils.truncate(DateUtils.addDays(now,-1), Calendar.DATE);
        Date endTime = DateUtils.truncate(now,Calendar.DATE);
        //获取要处理的理财产品记录
        List<productInfo> productInfoList = productInfoMapper.selectFullTimeProducts(beginTime,endTime);
        int rows;

        BigDecimal dayRate;//利率
        BigDecimal cycle;//周期
        BigDecimal income;

        Date incomeDate;//到期时间

        //查询每个理财产品的多条投资记录
        for (productInfo product:productInfoList){
            //日利率
            dayRate = product.getRate().divide(new BigDecimal("360"),10, RoundingMode.HALF_UP)
                    .divide(new BigDecimal("100"),10,RoundingMode.HALF_UP);

            //产品类型不同，周期不同 天，月
            if (product.getProductType() == YLBConstant.PRODUCT_TYPE_XINSHOUBAO){ //天
                cycle = new BigDecimal(product.getCycle());
                incomeDate = DateUtils.addDays(product.getProductFullTime(),(1+product.getCycle()));
            }else { //月
                cycle = new BigDecimal(product.getCycle()*30);
                incomeDate = DateUtils.addDays(product.getProductFullTime(),(1+product.getCycle()*30));
            }

            List<BidInfo> bidList = bidInfoMapper.selectByProdId(product.getId());

            //计算每笔投资的利息和到期时间
            for (BidInfo bid:bidList){
                //利息等于本金*周期*利率
                income = bid.getBidMoney().multiply(cycle).multiply(dayRate);
                //创建每笔投资的收益记录
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setBidId(bid.getId());
                incomeRecord.setBidMoney(bid.getBidMoney());
                incomeRecord.setIncomeDate(incomeDate);
                incomeRecord.setIncomeStatus(YLBConstant.INCOME_STATUS_PLAN);
                incomeRecord.setProdId(product.getId());
                incomeRecord.setIncomeMoney(income);
                incomeRecord.setUid(bid.getUid());
                incomeRecord.setLoanId(bid.getLoanId());
                incomeRecordMapper.insertSelective(incomeRecord);
            }

            //更新产品状态
            rows = productInfoMapper.updateStatus(product.getId(),YLBConstant.PRODUCT_STATUS_PLAN);
            if (rows < 1){
                throw new RuntimeException("更新收益计划，更新产品状态为2失败");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void generateIncomeBack() {
        Date date = new Date();
        Date ExpiredDate = DateUtils.truncate(DateUtils.addDays(date,-1),Calendar.DATE);
        List<IncomeRecord> incomeRecordList = incomeRecordMapper.selectExpetionCome(ExpiredDate);

        int rows;
        for (IncomeRecord ir : incomeRecordList){
            rows = accountMapper.updateAvailab(ir.getUid(),ir.getBidMoney(),ir.getIncomeMoney());
            if (rows < 1){
                throw new RuntimeException("更新账户资金失败");
            }
            ir.setIncomeStatus(YLBConstant.INCOME_STATUS_BACK);
            rows = incomeRecordMapper.updateByPrimaryKey(ir);
            if (rows <1){
                throw new RuntimeException("更新状态失败");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int cleanRecord() {
        Date dates = new Date();
        Date income_date = DateUtils.truncate(DateUtils.addDays(dates,-3),Calendar.DATE);
        int rows = incomeRecordMapper.deleteredundantdata(income_date);
        if (rows < 0 ){
            throw new RuntimeException("删除数据失败");
        } else {
            System.out.println(date.dateformat(income_date)+"数据已被清除");
            System.out.println("一共清理掉"+rows+"条数据");
        }
        return rows;
    }
}
