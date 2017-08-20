package com.njsyg.smarthomeapp.adapters.MissionsPage;

import android.graphics.drawable.Drawable;

/**
 * 定时任务  ArrayList
 * Created by HUAQING on 2016/10/24.
 */
public class smartDeviceTimeTaskArrayListItem {
    private Drawable smartDeviceImage;/** 定时任务设备图片 */
    private String deviceName;/** 定时任务 设备名称 */
    private String timeTask;/** 定时任务 定时时间 */
    private String openClose;/** 定时任务 显示打开关闭状态 */
    private String everyDay;/** 定时任务 显示执行 频率*/
    private Drawable switchState;/** 定时任务 打开或者关闭按钮 */
    private int intSwitchState;//0 关         1 开关 状态穿过来的值


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(String timeTask) {
        this.timeTask = timeTask;
    }

    public String getOpenClose() {
        return openClose;
    }

    public void setOpenClose(String openClose) {
        this.openClose = openClose;
    }

    public String getEveryDay() {
        return everyDay;
    }

    public void setEveryDay(String everyDay) {
        this.everyDay = everyDay;
    }

    public Drawable getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Drawable switchState) {
        this.switchState = switchState;
    }

    public int getIntSwitchState() {
        return intSwitchState;
    }

    public void setIntSwitchState(int intSwitchState) {
        this.intSwitchState = intSwitchState;
    }

    public Drawable getSmartDeviceImage() {
        return smartDeviceImage;
    }

    public void setSmartDeviceImage(Drawable smartDeviceImage) {
        this.smartDeviceImage = smartDeviceImage;
    }
}
