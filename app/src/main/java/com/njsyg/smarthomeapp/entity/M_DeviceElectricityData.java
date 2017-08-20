package com.njsyg.smarthomeapp.entity;

import java.util.Date;

/**
 * Created by zz on 2016/10/28.
 */
public class M_DeviceElectricityData {
    private String deviceSN;
    private Date updateTime;
    private Float deviceU;
    private Float deviceI;
    private Float deviceP;
    private Float deviceElectricity;

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Float getDeviceU() {
        return deviceU;
    }

    public void setDeviceU(Float deviceU) {
        this.deviceU = deviceU;
    }

    public Float getDeviceI() {
        return deviceI;
    }

    public void setDeviceI(Float deviceI) {
        this.deviceI = deviceI;
    }

    public Float getDeviceP() {
        return deviceP;
    }

    public void setDeviceP(Float deviceP) {
        this.deviceP = deviceP;
    }

    public Float getDeviceElectricity() {
        return deviceElectricity;
    }

    public void setDeviceElectricity(Float deviceElectricity) {
        this.deviceElectricity = deviceElectricity;
    }

    public M_DeviceElectricityData(String deviceSN, Date updateTime, Float deviceU, Float deviceI, Float deviceP, Float deviceElectricity) {
        this.deviceSN = deviceSN;
        this.updateTime = updateTime;
        this.deviceU = deviceU;
        this.deviceI = deviceI;
        this.deviceP = deviceP;
        this.deviceElectricity = deviceElectricity;
    }
}
