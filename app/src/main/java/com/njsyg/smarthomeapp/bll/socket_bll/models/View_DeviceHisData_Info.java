package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.util.Date;

/**
 * Created by zz on 2016/9/7.
 */
public class View_DeviceHisData_Info {
    private String device_sn;
    private int device_state;
    private Float device_U;
    private Float device_I;
    private Float divice_P;
    private Float device_electricity;
    private Date updatetime;
    private String remark;
    private String hserver_sn;
    private String device_ip;
    private int device_type;
    private String device_installPlace;
    private String device_nickName;

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public int getDevice_state() {
        return device_state;
    }

    public void setDevice_state(int device_state) {
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

    public Float getDivice_P() {
        return divice_P;
    }

    public void setDivice_P(Float divice_P) {
        this.divice_P = divice_P;
    }

    public Float getDevice_electricity() {
        return device_electricity;
    }

    public void setDevice_electricity(Float device_electricity) {
        this.device_electricity = device_electricity;
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

    public String getHserver_sn() {
        return hserver_sn;
    }

    public void setHserver_sn(String hserver_sn) {
        this.hserver_sn = hserver_sn;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public String getDevice_installPlace() {
        return device_installPlace;
    }

    public void setDevice_installPlace(String device_installPlace) {
        this.device_installPlace = device_installPlace;
    }

    public String getDevice_nickName() {
        return device_nickName;
    }

    public void setDevice_nickName(String device_nickName) {
        this.device_nickName = device_nickName;
    }
}
