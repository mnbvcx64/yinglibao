package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.FinanceAccount;
import com.bjpowernode.api.model.productInfo;
import com.bjpowernode.api.pojo.BidInfoProduct;
import com.bjpowernode.api.service.InvestService;
import com.bjpowernode.common.constant.YLBConstant;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.productInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = InvestService.class,version = "1.0")
public class InvestServeImpl implements InvestService {

    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private FinanceAccountMapper financeAccountMapper;

    @Resource
    private productInfoMapper productInfoMapper;

    /**某个产品的投资记录**/
    @Override
    public List<BidInfoProduct> queryBidListByProductId(Integer productId,
                                                        Integer pageNo,
                                                        Integer pageSize) {
        List<BidInfoProduct> bidList = new ArrayList<>();
        if (productId !=null && productId > 0){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            bidList = bidInfoMapper.selectByProductId(productId,offset,pageSize);
        }
        return bidList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int investProduct(Integer uid, Integer productId, BigDecimal money) {
        int result = 0;
        int rows;
        if ( (uid != null && uid > 0) && (productId != null && productId >= 0 ) &&
                (money != null && money.intValue() % 100 == 0 && money.intValue() >= 100)){
            //查询账号金额(根据uid)
            FinanceAccount account = financeAccountMapper.selectByUidForUpdate(uid);
            if (account != null){
                //资金是否满足购买要求
                if (CommonUtil.compare(account.getAvailableMoney(),money)){
                    //检查产品是否可以购买
                    productInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
                    if (productInfo != null){
                        if (CommonUtil.compare(productInfo.getLeftProductMoney(),money) &&
                                CommonUtil.compare(money,productInfo.getBidMinLimit()) &&
                                    CommonUtil.compare(productInfo.getBidMaxLimit(),money)){
                            //可以购买 扣除账户资金
                            rows = financeAccountMapper.updateAvalableMoneyByInvest(uid,money);
                            if (rows < 1 ){
                                throw new  RuntimeException("投资更新账户失败");
                            }
                            //扣除产品剩余
                            rows = productInfoMapper.updateLeftProductMoney(productId,money);
                            if (rows < 1){
                                throw new RuntimeException("投资更新产品剩余金额失败");
                            }

                            //创建投资记录
                            BidInfo bidInfo = new BidInfo();
                            bidInfo.setBidMoney(money);
                            bidInfo.setBidStatus(YLBConstant.INVEST_STATUS_SUCC);
                            bidInfo.setBidTime(new Date());
                            bidInfo.setLoanId(productId);
                            bidInfo.setUid(uid);
                            bidInfoMapper.insertSelective(bidInfo);

                            //判断产品是否卖完，更新产品是满标状态
                            productInfo dproductInfo = productInfoMapper.selectByPrimaryKey(productId);
                            if (dproductInfo.getLeftProductMoney().compareTo(new BigDecimal("0"))==0){
                                rows = productInfoMapper.updateSellde(productId);
                                if (rows < 1){
                                    throw new RuntimeException("更新产品满标失败");
                                }
                            }
                            result = 1;
                        }
                    } else {
                        result = 4;//产品不存在
                    }
                } else {
                    result = 3;
                }
            }else{
                result = 2;
            }
            //检查产品是否可以购买
        }
        return result;
    }
}
