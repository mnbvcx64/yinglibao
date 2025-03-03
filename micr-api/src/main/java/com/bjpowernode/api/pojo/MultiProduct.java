package com.bjpowernode.api.pojo;

import com.bjpowernode.api.model.productInfo;

import java.io.Serializable;
import java.util.List;

public class MultiProduct implements Serializable {
    private List<productInfo> xinShouBao;
    private List<productInfo> youXuan;
    private List<productInfo> sanBao;

    public List<productInfo> getXinShouBao() {
        return xinShouBao;
    }

    public void setXinShouBao(List<productInfo> xinShouBao) {
        this.xinShouBao = xinShouBao;
    }

    public List<productInfo> getYouXuan() {
        return youXuan;
    }

    public void setYouXuan(List<productInfo> youXuan) {
        this.youXuan = youXuan;
    }

    public List<productInfo> getSanBao() {
        return sanBao;
    }

    public void setSanBao(List<productInfo> sanBao) {
        this.sanBao = sanBao;
    }
}
