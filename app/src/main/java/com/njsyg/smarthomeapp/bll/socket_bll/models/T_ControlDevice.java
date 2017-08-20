package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.io.Serializable;

/**
 * Created by zz on 2016/8/13.
 */
public class T_ControlDevice implements Serializable{
    private String homeServerSN;
    private String deviceSN;
    private int setState;
    private String remark;
    private String fromPhoneNum;
    public String getHomeServerSN() {
        return homeServerSN;
    }

    public void setHomeServerSN(String homeServerSN) {
        this.homeServerSN = homeServerSN;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public int getSetState() {
        return setState;
    }

    public void setSetState(int setState) {
        this.setState = setState;
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
