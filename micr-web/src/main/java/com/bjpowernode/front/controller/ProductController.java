package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.productInfo;
import com.bjpowernode.api.pojo.BidInfoProduct;
import com.bjpowernode.api.pojo.MultiProduct;
import com.bjpowernode.common.enums.RCode;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.front.view.PageInfo;
import com.bjpowernode.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "理财产品功能")
@RestController
@RequestMapping("/v1")
public class ProductController extends BaseController{

    @ApiOperation(value = "首页三类产品列表",notes = "新手包，三个优选，三个散标产品")
    @GetMapping("/product/index")
    public RespResult queryProductIndex(){
        RespResult result = RespResult.ok();
        MultiProduct multiProduct = productService.queryIndexPageProducts();
        result.setObj(multiProduct);
        return result;
    }

    //按产品类型查询
    @ApiOperation(value = "产品分页查询",notes = "一个新手宝，三个优选，三个散标产品")
    @GetMapping("/product/list")
    public RespResult queryProductByType(@RequestParam("ptype") Integer pType,
                                         @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                                         @RequestParam(value = "pageSize",required = false,defaultValue = "9")Integer pageSize) {
        RespResult result = RespResult.fail();
        if (pType != null && (pType == 0 || pType == 1 || pType == 2)){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            Integer recordNums = productService.queryRecordNumsByType(pType);
            if (recordNums>0){
                List<productInfo> productInfos = productService.queryByTypeLimit(pType,pageNo,pageSize);
                PageInfo page = new PageInfo(pageNo,pageSize,recordNums);

                result = RespResult.ok();
                result.setList(productInfos);
                result.setPage(page);
            }
        }else {
            result.setRCode(RCode.REQUEST_PRODUCT_TYPE_ERR);
        }
        return result;
    }

    @GetMapping("/product/info")
    @ApiOperation(value = "产品详情",notes = "查询某个产品的详细信息和五条记录")
    public RespResult queryProductDetail(@RequestParam("productId") Integer id){
        RespResult result = RespResult.fail();
        //调用产品查询
        if (id != null && id > 0){
            productInfo productInfo = productService.queryById(id);
            if (productInfo != null){
                //查询投资记录
                List<BidInfoProduct> bidInfoList = investService.queryBidListByProductId(id,1,5);
                //查询成功
                result = RespResult.ok();
                result.setObj(productInfo);
                result.setList(bidInfoList);
            } else {
                result.setCode(RCode.PRODUCT_OFFLINE);
            }
        }
        return result;
    }
}
