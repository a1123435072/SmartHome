package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.io.Serializable;

/**
 * Created by user on 2016/7/7.
 */
public class T_HServerKey implements Serializable {
    private int id;
    private String hserver_sn;
    private int keytype;
    private String keyvalue;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHserver_sn() {
        return hserver_sn;
    }

    public void setHserver_sn(String hserver_sn) {
        this.hserver_sn = hserver_sn;
    }

    public int getKeytype() {
        return keytype;
    }

    public void setKeytype(int keytype) {
        this.keytype = keytype;
    }

    public String getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
