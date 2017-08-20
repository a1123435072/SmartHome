package com.njsyg.smarthomeapp.activitys.datacenter;

/**
 * Created by fox on 2016/10/21.
 * 解析出来的设备历史数据
 */
public class DeviceData {
    private int id;
    private String device_sn;
    private String updatetime;
    private int device_state;
    private float device_U;
    private float device_I;
    private float device_P;
    private float device_electricity;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    public void setDevice_U(int device_U) {
        this.device_U = device_U;
    }

    public float getDevice_I() {
        return device_I;
    }

    public void setDevice_I(int device_I) {
        this.device_I = device_I;
    }

    public float getDevice_P() {
        return device_P;
    }

    public void setDevice_P(int device_P) {
        this.device_P = device_P;
    }

    public float getDevice_electricity() {
        return device_electricity;
    }

    public void setDevice_electricity(float device_electricity) {
        this.device_electricity = device_electricity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "id=" + id +
                ", device_sn='" + device_sn + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", device_state=" + device_state +
                ", device_U=" + device_U +
                ", device_I=" + device_I +
                ", device_P=" + device_P +
                ", device_electricity=" + device_electricity +
                ", remark='" + remark + '\'' +
                '}';
    }
}
