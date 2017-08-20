package com.njsyg.smarthomeapp.adapters.SDeviceM;

import android.graphics.drawable.Drawable;

/**
 * Created by HUAQING on 2016/10/31.
 */

public class SmartDeviceArrayListItem {
    private String sDeviceSN;
    private Drawable imgSmartDevice;
    private String smartName;
    public String getsDeviceSN() {
        return sDeviceSN;
    }
    public void setsDeviceSN(String sDeviceSN) {
        this.sDeviceSN = sDeviceSN;
    }

    public Drawable getImgSmartDevice() {
        return imgSmartDevice;
    }

    public void setImgSmartDevice(Drawable imgSmartDevice) {
        this.imgSmartDevice = imgSmartDevice;
    }

    public String getSmartName() {
        return smartName;
    }
    public void setSmartName(String smartName) {
        this.smartName = smartName;
    }
}
