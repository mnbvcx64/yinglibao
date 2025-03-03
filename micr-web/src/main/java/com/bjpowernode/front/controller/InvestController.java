package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.User;
import com.bjpowernode.common.constant.RedisKey;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.front.view.invest.RankView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//@CrossOrigin
@Api(tags = "投资理财产品")
@RestController
public class InvestController extends BaseController{

    private final StringRedisTemplate stringRedisTemplate;

    public InvestController(StringRedisTemplate stringRedisTemplate) {
        super();
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @ApiOperation(value = "投资排行榜",notes = "显式投资金额最高的3位用户信息")
    @GetMapping("/v1/invest/rank")
    public RespResult showInvestRank(){
        //从redis查询数据
        Set<ZSetOperations.TypedTuple<String>> sets = stringRedisTemplate
                .boundZSetOps(RedisKey.KEY_INVEST_RANK)
                .reverseRangeWithScores(0, 2);
        List<RankView> rankList = new ArrayList<>();
        //遍历set集合
        assert sets != null;
        sets.forEach(Tuple -> {
            rankList.add(new RankView(CommonUtil.desensitization(Tuple.getValue()), Tuple.getScore()));
        });

        RespResult result = RespResult.ok();
        result.setList(rankList);
        return result;
    }

    @ApiOperation(value = "投资理财产品")
    @PostMapping("/v1/invest/product")
    public RespResult InvestmentProducts(@RequestHeader("uid") Integer uid,
                                         @RequestParam("productId") Integer productId,
                                         @RequestParam("money") BigDecimal money){
        RespResult result = RespResult.fail();
        if ( (uid != null && uid > 0) && (productId != null && productId >= 0 ) &&
                (money != null && money.intValue() % 100 == 0 && money.intValue() >= 100)){
            int investResult = investService.investProduct(uid,productId,money);
            switch (investResult){
                case 0:
                    result.setMsg("投资数据不正确");
                    break;
                case 1:
                    RespResult.ok();
                    modifyInvestRank(uid, money);
                    break;
                case 2:
                    result.setMsg("资金账号不存在");
                    break;
                case 3:
                    result.setMsg("资金不足");
                    break;
                case 4:
                    result.setMsg("产品不存在");
                    break;
            }
        }
        return result;
    }

    private void modifyInvestRank(Integer uid,BigDecimal money){
        User user = userService.queryById(uid);
        if (user != null){
            //更新redis中的投资排行榜
            String key = RedisKey.KEY_INVEST_RANK;
            stringRedisTemplate.boundZSetOps(key).incrementScore(
                    user.getPhone(),money.doubleValue());
        }
    }
}
