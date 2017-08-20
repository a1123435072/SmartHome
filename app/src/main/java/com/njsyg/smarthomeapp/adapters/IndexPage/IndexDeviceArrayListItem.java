package com.njsyg.smarthomeapp.adapters.IndexPage;

import android.graphics.drawable.Drawable;

/**
 * Created by HUAQING on 2016/10/24.
 */

public class IndexDeviceArrayListItem {
    //    private int deviceId;      //设备ID
    private String deviceSN;   //设备sn
    private String nickName;//设备别名
    private String installPlace;//安装位置
    private Drawable onlineState;//在线/不在线 图标
    private Drawable switchState;//开关/开、关：图标
    private int intOnlineState;//0 不在线     1 在线 状态： 传过来的值
    private int intSwitchState;//0 关         1 开关 状态穿过来的值
    private String dianLiu;//电流
    private String dianYa;//电压
    private String battery;//电量

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getInstallPlace() {
        return installPlace;
    }

    public void setInstallPlace(String installPlace) {
        this.installPlace = installPlace;
    }

    public Drawable getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(Drawable onlineState) {
        this.onlineState = onlineState;
    }

    public Drawable getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Drawable switchState) {
        this.switchState = switchState;
    }

    public int getIntOnlineState() {
        return intOnlineState;
    }

    public void setIntOnlineState(int intOnlineState) {
        this.intOnlineState = intOnlineState;
    }

    public int getIntSwitchState() {
        return intSwitchState;
    }

    public void setIntSwitchState(int intSwitchState) {
        this.intSwitchState = intSwitchState;
    }

    public String getDianLiu() {
        return dianLiu;
    }

    public void setDianLiu(String dianLiu) {
        this.dianLiu = dianLiu;
    }

    public String getDianYa() {
        return dianYa;
    }

    public void setDianYa(String dianYa) {
        this.dianYa = dianYa;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }
}
