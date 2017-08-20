package com.njsyg.smarthomeapp.bll;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyModuleCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_DeviceHisData;


/**
 * Created by zz on 2016/9/7.
 */
public class device_data_bll {
    /// <summary>
    ///功能模块
    /// </summary>
    private static final byte ModuleCode = MyModuleCode.SDeviceModule;

    /**
     * 向公网服务器发送转发控制智能设备命令
     */
    public static void GetDeviceHisDataByPublicServer(T_DeviceHisData t_DeviceHisData, Handler handler) {
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_DeviceHisData);
        byte[] data = jsonStr.getBytes();
        byte commandCode = MyCommandCode.GetDeviceHisData;
        byte[] sendMsg = command_bll.PacketCommand(ModuleCode, commandCode, data);
        command_bll.SendDataToPublicServer(sendMsg, handler);
    }
}
