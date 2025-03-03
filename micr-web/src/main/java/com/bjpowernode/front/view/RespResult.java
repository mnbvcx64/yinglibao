package com.bjpowernode.front.view;

import com.bjpowernode.common.enums.RCode;

import java.util.List;

/**
 * 统一的应答结果类 controller方法的返回值都是它
 */
public class RespResult {
    private int code;  //应答码
    private String msg;
    private Object obj;  //单个数据
    private List list;
    private PageInfo page;
    private String accessToken;
    private Object data;

    //表示成功的result对象
    public static RespResult ok(){
        RespResult result = new RespResult();
        result.setRCode(RCode.SUCC);
        return result;
    }

    //表示失败的result对象
    public static RespResult fail(){
        RespResult result = new RespResult();
        result.setRCode(RCode.UNKNOW);
        return result;
    }

    public void setRCode(RCode rCode){
        this.code = rCode.getCode();
        this.msg = rCode.getText();
    }

    public int getCode() {
        return code;
    }

    public void setCode(RCode rcode) {
        this.code = rcode.getCode();
        this.msg = rcode.getText();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
