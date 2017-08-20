package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.util.Date;

/**
 * Created by zz on 2016/9/7.
 */
public class T_DeviceHisData {
    public int id;
    public String device_sn;
    public int device_state;
    public float device_U;
    public float device_I;
    public float divice_P;
    public float device_electricity;
    public Date updatetime;
    public String remark;
    public Date begintime;
    public Date endtime;
    public String homeserver_sn;

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

    public int getDevice_state() {
        return device_state;
    }

    public void setDevice_state(int device_state) {
        this.device_state = device_state;
    }

    public float getDevice_U() {
        return device_U;
    }

    public void setDevice_U(float device_U) {
        this.device_U = device_U;
    }

    public float getDevice_I() {
        return device_I;
    }

    public void setDevice_I(float device_I) {
        this.device_I = device_I;
    }

    public float getDivice_P() {
        return divice_P;
    }

    public void setDivice_P(float divice_P) {
        this.divice_P = divice_P;
    }

    public float getDevice_electricity() {
        return device_electricity;
    }

    public void setDevice_electricity(float device_electricity) {
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

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getHomeserver_sn() {
        return homeserver_sn;
    }

    public void setHomeserver_sn(String homeserver_sn) {
        this.homeserver_sn = homeserver_sn;
    }
}
