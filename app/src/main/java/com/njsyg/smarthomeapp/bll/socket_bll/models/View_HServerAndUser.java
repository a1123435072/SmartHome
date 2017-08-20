package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2016/7/7.
 */
public class View_HServerAndUser implements Serializable{
    private int id = 0;
    private String user_phoneNumber;
    private int hserver_id = 0;
    private String hserver_sn;
    private String hserver_ip;
    private String hserver_nickName;
    private String remark;
    private int isdefault;
    private List<T_HServerKey> t_hserverKeyList;

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

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public List<T_HServerKey> getT_hserverKeyList() {
        return t_hserverKeyList;
    }

    public void setT_hserverKeyList(List<T_HServerKey> t_hserverKeyList) {
        this.t_hserverKeyList = t_hserverKeyList;
    }
}
