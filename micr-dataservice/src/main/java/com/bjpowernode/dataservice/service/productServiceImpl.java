package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.productInfo;
import com.bjpowernode.api.pojo.MultiProduct;
import com.bjpowernode.api.service.ProductService;
import com.bjpowernode.common.constant.YLBConstant;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.dataservice.mapper.productInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = ProductService.class, version = "1.0")
public class productServiceImpl implements ProductService {

    @Resource
    private productInfoMapper productInfoMapper;

    @Override
    public List<productInfo> queryByTypeLimit(Integer pType, Integer pageNo, Integer pageSize) {
        List<productInfo> productInfos = new ArrayList<>();
        if (pType==0 || pType==1 || pType==2){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo-1)*pageSize;
            productInfos = productInfoMapper.selectByTypeLimit(pType,offset,pageSize);
        }
        return productInfos;
    }

    //某个产品类型的记录总数
    @Override
    public Integer queryRecordNumsByType(Integer pType) {
        Integer counts = 0;
        if (pType==0 || pType==1 || pType==2){
            counts = productInfoMapper.selectCountByType(pType);
        }
        return counts;
    }

    @Override
    public MultiProduct queryIndexPageProducts() {
        MultiProduct result = new MultiProduct();
        List<productInfo> xinShouBaolist = productInfoMapper.selectByTypeLimit(
                YLBConstant.PRODUCT_TYPE_XINSHOUBAO,0,1);
        //查询优选（1）
        List<productInfo> youXuanList = productInfoMapper.selectByTypeLimit(
                YLBConstant.PRODUCT_TYPE_YOUXUAN,0,3);
        //散标
        List<productInfo> sanBaoList = productInfoMapper.selectByTypeLimit(
                YLBConstant.PRODUCT_TYPE_SANBIAO,0,3);
        //封装对象
        result.setXinShouBao(xinShouBaolist);
        result.setYouXuan(youXuanList);
        result.setSanBao(sanBaoList);
        return result;
    }

    @Override
    public productInfo queryById(Integer id) {
        productInfo productInfo = null;
        if ( id!= null && id > 0){
            productInfo = productInfoMapper.selectByPrimaryKey(id);
        }
        return productInfo;
    }

    @Override
    public productInfo queryProductName(Integer id) {
        return productInfoMapper.selectProductName(id);
    }
}
