package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.BidProduct;
import com.bjpowernode.front.dto.BidProductDTO;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.front.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "投资功能")
@RestController
public class BidInfoController extends BaseController{

    @GetMapping("/v1/bid/info")
    @ApiOperation("根据uid查询用户的投资记录")
    public RespResult queryBidInfoPage(@RequestHeader("uid") Integer uid,
                                       @RequestParam(required = false,defaultValue = "1")Integer PageNo,
                                       @RequestParam(required = false,defaultValue = "6")Integer PageSize){
        RespResult result = RespResult.fail();
        if (uid != null && uid > 0 ){
            List<BidInfo> records = bidInfoService.findByUid(uid,PageNo,PageSize);
            result = RespResult.ok();
            result.setData(toView(records));
        }
        return result;
    }

    private List<ResultVO> toView(List<BidInfo> src){
        List<ResultVO> target = new ArrayList<>();
        src.forEach( record ->{
            target.add(new ResultVO(record));
        });
        return target;
    }

}
