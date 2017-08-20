package com.njsyg.smarthomeapp.entity;

import java.util.Date;

/**
 * Created by zz on 2016/10/28.
 */
public class M_DeviceSwitchStatus {
    private String deviceSN;//设备sn
    private int deviceSwitchStatus;//设备开关状态
    private Date updateTime;//更新时间

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public int getDeviceSwitchStatus() {
        return deviceSwitchStatus;
    }

    public void setDeviceSwitchStatus(int deviceSwitchStatus) {
        this.deviceSwitchStatus = deviceSwitchStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public M_DeviceSwitchStatus(String deviceSN, int deviceSwitchStatus, Date updateTime) {
        this.deviceSN = deviceSN;
        this.deviceSwitchStatus = deviceSwitchStatus;
        this.updateTime = updateTime;
    }

    public M_DeviceSwitchStatus() {
    }
}
