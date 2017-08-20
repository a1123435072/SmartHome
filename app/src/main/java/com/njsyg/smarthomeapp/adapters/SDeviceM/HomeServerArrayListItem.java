package com.njsyg.smarthomeapp.adapters.SDeviceM;

import android.graphics.drawable.Drawable;

/**
 * Created by HUAQING on 2016/10/28.
 */

public class HomeServerArrayListItem {
    private String hserverSN;//sn
    private Drawable imgHServer;//图标
    private String hserverName;
    private String hserverIp;
    private int intIsDefault;//是否是默认
    private Drawable hserverIsDefault;

    public String getHserverSN() {
        return hserverSN;
    }

    public void setHserverSN(String hserverSN) {
        this.hserverSN = hserverSN;
    }

    public Drawable getImgHServer() {
        return imgHServer;
    }

    public void setImgHServer(Drawable imgHServer) {
        this.imgHServer = imgHServer;
    }

    public String getHserverName() {
        return hserverName;
    }

    public void setHserverName(String hserverName) {
        this.hserverName = hserverName;
    }

    public String getHserverIp() {
        return hserverIp;
    }

    public void setHserverIp(String hserverIp) {
        this.hserverIp = hserverIp;
    }

    public int getIntIsDefault() {
        return intIsDefault;
    }

    public void setIntIsDefault(int intIsDefault) {
        this.intIsDefault = intIsDefault;
    }

    public Drawable getHserverIsDefault() {
        return hserverIsDefault;
    }

    public void setHserverIsDefault(Drawable hserverIsDefault) {
        this.hserverIsDefault = hserverIsDefault;
    }
}
