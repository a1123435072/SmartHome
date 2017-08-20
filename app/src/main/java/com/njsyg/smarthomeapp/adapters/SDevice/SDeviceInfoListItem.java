package com.njsyg.smarthomeapp.adapters.SDevice;

import android.graphics.drawable.Drawable;

/**
 * Created by zz on 2016/9/7.
 */
public class SDeviceInfoListItem {
    private String fserverSN;
    private String sdeviceSN;
    private Drawable image;
    private String nick;
    private String place;

    public String getFserverSN() {
        return fserverSN;
    }

    public void setFserverSN(String fserverSN) {
        this.fserverSN = fserverSN;
    }

    public String getSdeviceSN() {
        return sdeviceSN;
    }

    public void setSdeviceSN(String sdeviceSN) {
        this.sdeviceSN = sdeviceSN;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
