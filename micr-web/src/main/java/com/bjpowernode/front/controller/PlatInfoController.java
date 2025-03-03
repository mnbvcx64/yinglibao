package com.bjpowernode.front.controller;

import com.bjpowernode.api.pojo.BaseInfo;
import com.bjpowernode.common.enums.RCode;
import com.bjpowernode.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@Api(tags = "平台信息功能")
@RestController
@RequestMapping("/v1")
public class PlatInfoController extends BaseController {

    //平台基本信息
    @ApiOperation(value = "平台三项基本信息",notes = "注册人数，平均利率，总投资金额")
    @GetMapping("/plat/info")
    public RespResult queryPlatBaseInfo(){
        BaseInfo baseInfo = platBaseInfoService.queryPlatBaseInfo();//调用远程服务

        RespResult respResult = new RespResult();
        respResult.setCode(RCode.SUCC);
        respResult.setMsg("查询平台信息成功");
        respResult.setObj(baseInfo);

        return respResult;
    }
}
