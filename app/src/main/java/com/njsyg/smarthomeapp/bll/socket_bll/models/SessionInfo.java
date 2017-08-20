package com.njsyg.smarthomeapp.bll.socket_bll.models;

import com.njsyg.smarthomeapp.bll.socket_bll.enums.EDeviceType;

/**
 * Created by zz on 2016/8/17.
 */
public class SessionInfo {

    /// <summary>
    /// 唯一编码
    /// </summary>
    private String SN;;
    /// <summary>
    /// 设备类型
    /// </summary>
    private EDeviceType DeviceType;
    /// <summary>
    /// IP地址
    /// </summary>
    private String IpAddress;

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public EDeviceType getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(EDeviceType deviceType) {
        DeviceType = deviceType;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }
}
