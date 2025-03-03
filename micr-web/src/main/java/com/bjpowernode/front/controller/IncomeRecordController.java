package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.IncomeRecord;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.front.view.recharge.IncomeView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "收益功能")
@RestController
public class IncomeRecordController extends BaseController {

    @ApiOperation("根据uid查询用户收益表")
    @GetMapping("/v1/bid/incomeRecord")
    public RespResult queryByIR(@RequestHeader("uid") Integer uid,
                                @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false,defaultValue = "6") Integer pageSize){
        RespResult result = RespResult.fail();
        if (uid != null && uid > 0){
           List<IncomeRecord> records = incomeRecordService.findByUid(uid,pageNo,pageSize);
            result = RespResult.ok();
            result.setData(toView(records));
        }
        return result;
    }

    private List<IncomeView> toView(List<IncomeRecord> src){
        List<IncomeView> target = new ArrayList<>();
        src.forEach( record ->{
            target.add(new IncomeView(record));
        });
        return target;
    }
}
