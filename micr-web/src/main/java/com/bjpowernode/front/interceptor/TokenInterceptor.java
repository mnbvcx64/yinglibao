package com.bjpowernode.front.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.common.enums.RCode;
import com.bjpowernode.common.util.JwtUtil;
import com.bjpowernode.front.view.RespResult;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static java.lang.System.out;

public class TokenInterceptor implements HandlerInterceptor {

    public TokenInterceptor(String secret) {
        this.secret = secret;
    }

    private String secret = "";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是OPTIONS，放行n
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }

        boolean requestSend = false;
        try {
            //获取token的值
            String headerUid = request.getHeader("uid");
            String headerToken = request.getHeader("accessToken");
            try {
                //进行验证
                if (StringUtils.isNotBlank(headerToken)) {
                    String Token = headerToken.substring(7);
                    //读取jwt
                    JwtUtil jwtUtil = new JwtUtil(secret);
                    Claims claims = jwtUtil.readJwt(Token);

                    //读取jwt中的数据 uid
                    Integer jwtUid = claims.get("uid", Integer.class);
                    if (headerUid.equals(String.valueOf(jwtUid))) {
                        requestSend = true;
                    }
                }
            } catch (Exception e) {
                requestSend = false;
                e.printStackTrace();
            }
            //token没有通过验证，需要给前端提示
            if (requestSend == false) {
                //返回json数据给前端
                RespResult result = RespResult.fail();
                result.setCode(RCode.TOKEN_INVALID);
                //使用HttpServletResponse输出 json
                String respJson = JSONObject.toJSONString(result);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print(respJson);
                out.flush();
                out.close();
            }
        }catch (Exception e){
            requestSend = false;
            e.printStackTrace();
        }
        return requestSend;
    }
}
