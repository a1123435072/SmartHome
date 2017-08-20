package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.io.Serializable;

/**
 * Created by zz on 2016/8/31.
 */
public class T_DeviceRealData implements Serializable{
    private Long id;
    private String device_sn;
    private Integer device_state;
    private Float device_U;
    private Float device_I;
    private Float device_P;
    private Float device_electricity;
    private java.util.Date updatetime;
    private String remark;
    private String phoneNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public Integer getDevice_state() {
        return device_state;
    }

    public void setDevice_state(Integer device_state) {
        this.device_state = device_state;
    }

    public Float getDevice_U() {
        return device_U;
    }

    public void setDevice_U(Float device_U) {
        this.device_U = device_U;
    }

    public Float getDevice_I() {
        return device_I;
    }

    public void setDevice_I(Float device_I) {
        this.device_I = device_I;
    }

    public Float getDevice_P() {
        return device_P;
    }

    public void setDevice_P(Float device_P) {
        this.device_P = device_P;
    }

    public Float getDevice_electricity() {
        return device_electricity;
    }

    public void setDevice_electricity(Float device_electricity) {
        this.device_electricity = device_electricity;
    }

    public java.util.Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
