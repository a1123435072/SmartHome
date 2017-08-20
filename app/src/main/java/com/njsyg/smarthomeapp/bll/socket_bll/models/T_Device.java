package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.io.Serializable;

/**
 * Created by user on 2016/7/11.
 */
public class T_Device implements Serializable{
    private int device_id;
    private String device_sn;
    private String hserver_sn;
    private String device_ip;
    private int device_type;
    private String device_installPlace;
    private String device_nickName;
    private String remark;
    private String fromPhoneNum;
    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
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
