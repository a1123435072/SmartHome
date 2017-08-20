package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.util.Date;

/**
 * Created by zz on 2016/9/14.
 */
public class T_DeviceOnlineRecord {
    private int id;
    private String device_sn;
    private boolean device_onlineState;
    private Date updatetime;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public boolean isDevice_onlineState() {
        return device_onlineState;
    }

    public void setDevice_onlineState(boolean device_onlineState) {
        this.device_onlineState = device_onlineState;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
