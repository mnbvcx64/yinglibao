package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;
import com.bjpowernode.api.service.UserService;
import com.bjpowernode.common.enums.RCode;
import com.bjpowernode.common.util.CommonUtil;
import com.bjpowernode.common.util.JwtUtil;
import com.bjpowernode.front.service.SmsService;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.front.dto.realNameDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "用户功能")
@RestController
@RequestMapping("v1/user")
public class UserController extends BaseController {

    @Resource
    private SmsService smsService;

    @Resource
    private UserService userService;

    @Resource
    private JwtUtil jwtUtil;

    @GetMapping("/phone/exists")
    @ApiImplicitParam(name = "phone", value = "手机号")
    @ApiOperation(value = "手机号是否注册过", notes = "注册功能，判断手机号是否被注册")
    public RespResult userController(@RequestParam("phone") String phone) {
        RespResult result = new RespResult();
        result.setCode(RCode.PHONE_EXISTS);
        if (CommonUtil.checkPhone(phone)) {
            User user = userService.queryBuPhone(phone);
            if (user == null) {
                result = RespResult.ok();
            }
        } else {
            result.setCode(RCode.PHONE_FORMAT_ERR);
        }
        return result;
    }

    @PostMapping("/register")
    @ApiOperation(value = "手机号注册")
    public RespResult userRegister(@RequestParam String phone,
                                   @RequestParam String password,
                                   @RequestParam String sCode) {
        RespResult result = RespResult.fail();
        if (CommonUtil.checkPhone(phone)) {
            if (password != null && password.length() == 32) {
                if (smsService.checkSmsService(phone, sCode)) { //检查短信验证码
                    //可以注册
                    int regedit = userService.userRegister(phone, password);
                    if (regedit == 1) {
                        result = RespResult.ok();
                    } else if (regedit == 2) {
                        result.setCode(RCode.PHONE_EXISTS);
                    } else {
                        result.setCode(RCode.PHONE_FORMAT_ERR);
                    }
                } else {
                    result.setCode(RCode.SMS_CODE_INVALID); //短信验证码无效
                }
            } else {
                result.setCode(RCode.REQUEST_PARAM_TYPE_ERR);
            }
        } else {
            result.setCode(RCode.PHONE_FORMAT_ERR); //手机号格式错误
        }
        return result;
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录-获取访问token")
    public RespResult userLogin(@RequestParam String phone,
                                @RequestParam String password,
                                @RequestParam String sCode) throws Exception {
        RespResult result = RespResult.fail();
        if (CommonUtil.checkPhone(phone) && (password != null && password.length() == 32)) {
                if (smsService.checkSmsService(phone, sCode)) {
                    User user = userService.userLogin(phone, password);
                    //System.out.println("user = " + user);
                    if (user != null) {
                        //用户查询成功，生成token
                        Map<String, Object> data = new HashMap<>();
                        data.put("uid", user.getId());
                        String jwtToken = jwtUtil.createJwt(data, 120);

                        result = RespResult.ok();
                        result.setAccessToken(jwtToken);

                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("uid", user.getId());
                        userInfo.put("phone", user.getPhone());
                        userInfo.put("name", user.getName());
                        result.setData(userInfo);
                    } else {
                        result.setCode(RCode.PHONE_LOGIN_PASSWORD_INVALID);
                    }
                } else {
                    result.setCode(RCode.SMS_CODE_INVALID);
                }
        } else {
            result.setCode(RCode.REQUEST_PARAM_TYPE_ERR);
        }
        return result;
    }

    @PostMapping("/realName")
    @ApiOperation(value = "用户实名认证")
    public RespResult userRealName(@RequestBody realNameDto realNamedto) throws Exception {
        RespResult result = RespResult.fail();
        result.setCode(RCode.REQUEST_PARAM_TYPE_ERR);
        if (CommonUtil.checkPhone(realNamedto.getPhone())) {
            if (smsService.checkSmsService(realNamedto.getPhone(), realNamedto.getCode())) {
                if (StringUtils.isNoneBlank(realNamedto.getName()) &&
                        StringUtils.isNoneBlank(realNamedto.getIdCard())) {
                    User user = userService.queryBuPhone(realNamedto.getPhone());
                    if (user != null) {
                        if (StringUtils.isNotBlank(user.getName())) {
                            result.setCode(RCode.REALNAME_RETRY);/*已经通过实名认证*/
                        } else {
                            boolean realNameResult = userService.modifyRealName(
                                    realNamedto.getPhone(),
                                    realNamedto.getName(),
                                    realNamedto.getIdCard());
                            if (realNameResult) {
                                result = RespResult.ok();
                            } else {
                                result.setCode(RCode.REALNAME_FAIL);
                            }
                        }
                    } else {
                        result.setCode(RCode.REALNAME_ACCOUNT_EMPTY);
                    }
                }
            } else {
                result.setCode(RCode.SMS_CODE_INVALID);
            }
        }
        return result;
    }

    @GetMapping("/userCenter")
    @ApiOperation(value = "用户中心")
    public RespResult userCenter(@RequestHeader("uid") Integer uid){
        RespResult result = RespResult.fail();

        Date nowData = new Date();
        // 创建一个SimpleDateFormat对象来格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为字符串并打印
        String date = simpleDateFormat.format(nowData);

        if (uid != null && uid > 0){
            UserAccountInfo userAccountInfo = userService.queryUserAllInfo(uid);
            if (userAccountInfo!=null){
                result = RespResult.ok();
                Map<String, Object> data = getStringObjectMap(userAccountInfo, date);
                result.setData(data);
            }
        }
        return result;
    }

    @Transactional
    @PostMapping("/update")
    @ApiOperation("根据账号(手机号)修改密码")
    public RespResult userUpdate(@RequestParam String password,
                                 @RequestParam String passwords,
                                 @RequestParam String phone,
                                 @RequestParam String sCode){
        RespResult result = RespResult.fail();
        if (password.equals(passwords)){
            if (CommonUtil.checkPhone(phone)){
                User user = userService.queryBuPhone(phone);
                if (user != null) {
                    if (smsService.checkSmsService(phone, sCode)) {
                        int rows = userService.userUpdate(phone, password);
                        if (rows == 1) {
                            result = RespResult.ok();
                        } else if (rows == 0) {
                            result.setMsg("新密码和当前密码一样");
                        } else {
                            result.setCode(RCode.REQUEST_PARAM_TYPE_ERR);
                        }
                    } else {
                        result.setCode(RCode.SMS_CODE_INVALID);
                    }
                } else {
                    result.setCode(RCode.REALNAME_ACCOUNT_EMPTY);
                }
            } else {
                result.setCode(RCode.PHONE_FORMAT_ERR);
            }
        } else {
            result.setMsg("密码输入不一致");
        }
        return result;
    }

    @GetMapping("/userString")
    public String UserString(Integer uid){
        String views = "参数错误";
        if (uid != null ){
            User user = userService.queryById(uid);
            if (user != null){
                views = "用户存在";
            } else {
                views = "用户不存在";
            }
        }
        return views;
    }

    private static Map<String, Object> getStringObjectMap(UserAccountInfo userAccountInfo, String date) {
        Map<String,Object> data = new HashMap<>();
        data.put("name", userAccountInfo.getName());
        data.put("phone", userAccountInfo.getPhone());
        data.put("money", userAccountInfo.getAvailableMoney());
        if ( userAccountInfo.getLastLoginTime()!=null){
            data.put("loginTime", date);
        } else {
            data.put("loginTime","");
        }
        return data;
    }
}
