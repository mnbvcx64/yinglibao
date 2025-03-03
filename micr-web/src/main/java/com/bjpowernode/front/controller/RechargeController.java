package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.RechargeRecord;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.front.view.recharge.ResultView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "充值功能")
@RestController
public class RechargeController extends BaseController{

    @GetMapping("/v1/recharge/records")
    @ApiOperation(value = "查询用户的充值记录")
    public RespResult queryRechargePage(@RequestHeader("uid") Integer uid,
                                        @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false,defaultValue = "6")Integer pageSize){
        RespResult result = RespResult.fail();
        if (uid != null && uid > 0){
            List<RechargeRecord> records = rechargeService.findByUid(uid, pageNo, pageSize);
            result = RespResult.ok();
            result.setList(toView(records));
            //result = RespResult.ok();
        }
        return result;
    }

    private List<ResultView> toView(List<RechargeRecord> src){
        List<ResultView> target = new ArrayList<>();
        src.forEach( record -> {
            target.add( new ResultView(record));
        });
        return target;
    }
}
