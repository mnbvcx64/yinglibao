package com.bjpowernode.common.enums;

public enum RCode {
    UNKNOW(0,"请稍后重试"),
    SUCC(1000,"请求成功"),
    REQUEST_PRODUCT_TYPE_ERR(1001,"产品类型不正确"),
    REQUEST_PARAM_TYPE_ERR(1002,"请求参数有误"),
    PRODUCT_OFFLINE(1003,"产品已下线"),
    PHONE_FORMAT_ERR(1004,"手机号格式错误"),
    PHONE_EXISTS(1005,"手机号已被注册"),
    SMS_CODE_CAN_USE(1006,"验证码可以继续使用"),
    SMS_CODE_INVALID(1007,"验证码错误"),
    PHONE_LOGIN_PASSWORD_INVALID(1008,"手机号或密码错误"),
    REALNAME_FAIL(1009,"实名认证无效"),
    REALNAME_RETRY(1010,"该账户已通过实名认证"),
    REALNAME_ACCOUNT_EMPTY(1011,"账号不存在"),
    TOKEN_INVALID(3000,"token无效")
    ;


    RCode(int c,String t){
        this.code = c;
        this.text = t;
    }
    /**
     * 应答码
     * 0：默认
     * 1000-2000请求参数有误，逻辑问题
     * 2000-3000服务器请求错误
     * 3000-4000访问dubbo的应答结果
     */
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
