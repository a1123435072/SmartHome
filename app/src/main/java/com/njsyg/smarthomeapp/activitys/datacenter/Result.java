package com.njsyg.smarthomeapp.activitys.datacenter;

import java.util.List;

/**
 * Created by fox on 2016/10/21.
 * 设备的json历史数据
 */
public class Result {
    private List<DeviceData> result;

    public List<DeviceData> getResult() {
        return result;
    }

    public void setResult(List<DeviceData> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                '}';
    }
}
