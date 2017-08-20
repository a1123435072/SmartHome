package com.njsyg.smarthomeapp.bll.socket_bll.models;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 2016/6/27.
 */
public class T_HServerAndUser implements Serializable {
    private int id = 0;
    private String user_phoneNumber;
    private String hserver_sn;
    private Date addtime;
    private String remark;
    private String hserver_nickName;
    private int isdefault;
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }
    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }
    public String getHserver_sn() {
        return hserver_sn;
    }
    public void setHserver_sn(String hserver_sn) {
        this.hserver_sn = hserver_sn;
    }
    public Date getAddtime() {
        return addtime;
    }
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
    public int getIsdefault() { return isdefault; }
    public void setIsdefault(int isdefault) {this.isdefault = isdefault; }
}
