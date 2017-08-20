package com.njsyg.smarthomeapp.bll.socket_bll.models;

/**
 * Created by user on 2016/7/14.
 */
public class T_HomeServer {
    private int hserver_id;
    private String hserver_sn;
    private String hserver_ip;
    private String hserver_nickName;
    private String remark;
    private String fromPhoneNum;

    public int getHserver_id() {
        return hserver_id;
    }

    public void setHserver_id(int hserver_id) {
        this.hserver_id = hserver_id;
    }

    public String getHserver_sn() {
        return hserver_sn;
    }

    public void setHserver_sn(String hserver_sn) {
        this.hserver_sn = hserver_sn;
    }

    public String getHserver_ip() {
        return hserver_ip;
    }

    public void setHserver_ip(String hserver_ip) {
        this.hserver_ip = hserver_ip;
    }

    public String getHserver_nickName() {
        return hserver_nickName;
    }

    public void setHserver_nickName(String hserver_nickName) {
        this.hserver_nickName = hserver_nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFromPhoneNum() {
        return fromPhoneNum;
    }

    public void setFromPhoneNum(String fromPhoneNum) {
        this.fromPhoneNum = fromPhoneNum;
    }
}
