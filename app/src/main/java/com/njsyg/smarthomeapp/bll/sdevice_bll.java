package com.njsyg.smarthomeapp.bll;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyModuleCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_ControlDevice;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_Device;
import com.njsyg.smarthomeapp.common.Converts;


/**
 * Created by PCPC on 2016/6/2.
 */
public  class sdevice_bll {
    public static   final byte ModuleCode= MyModuleCode.SDeviceModule;
    /**
    *获取智能设备列表*
     */
    public static void GetDeviceListByPublicServer(Handler handler, T_Device t_device) {
        byte commandCode = MyCommandCode.GetDeviceList;//功能编码
        DealCommand(commandCode, t_device, handler);
    }
    /**
    *新增智能设备*
     */
    public static void AddDeviceByPublicServer(Handler handler,T_Device t_device) {
        byte commandCode = MyCommandCode.AddDevice;//功能编码
        DealCommand(commandCode, t_device, handler);
    }
    /**
    *修改智能设备信息*
     */
    public static void UpdDeviceByPublicServer(Handler handler,T_Device t_device) {
        byte commandCode = MyCommandCode.UpdDevice;//修改智能设备
        DealCommand(commandCode, t_device, handler);
    }
    /**
    *删除智能设备*
     */
    public static void DeleteDeviceByPublicServer(Handler handler,T_Device t_device) {
        byte commandCode = MyCommandCode.DeleteDevice;//删除智能设备
        DealCommand(commandCode, t_device, handler);
    }
    /***
     * 控制智能设备状态
     * **/
    public static void ControlSDeviceState(Handler handler, T_ControlDevice t_controlDevice)
    {
        byte commandCode = MyCommandCode.SetDeviceState;//控制智能设备
        DealCommand(commandCode, t_controlDevice, handler);
    }
    /// <summary>
    ///向家庭服务器发送 删除与设备的绑定关系
    /// </summary>
    public static void DeleteDeviceByHomeServer(Handler handler,T_Device t_device)
    {
        byte commandCode = MyCommandCode.DeleteDevice;//删除智能设备
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_device);
        byte[] data = jsonStr.getBytes();
        byte[] sendData= command_bll.PacketCommand(ModuleCode,commandCode,data);
         command_bll.SendDataToHomeServer(t_device.getHserver_sn(),sendData,handler);
    }
    /***
     *向公网服务器发送命令
     * 组包数据
     * commandCode：命令码
     * data：数据内容
     * **/
    private static void DealCommand(byte commandCode,T_Device t_device,Handler handler) {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(ModuleCode);//模块编码
        commandStruct.setCommandCode(commandCode);//功能编码
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_device);
        Log.d("test", "json:" + jsonStr);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        //PServer_DataExchange.sharedInstance().sendMsg(bytes, handler);
        SocketManager.SendPublicServerMsg(bytes,handler);
        Log.d("test", "打包数据bytes test:" + test);
    }
    /***
     *向家庭服务器发送命令
     * 组包数据
     * commandCode：命令码
     * data：数据内容
     * **/
    private static void DealCommand(byte commandCode,T_ControlDevice t_controlDevice,Handler handler) {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(ModuleCode);//模块编码
        commandStruct.setCommandCode( commandCode);//功能编码
        commandStruct.setParameterData(t_controlDevice.getDeviceSN().getBytes());//sn
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_controlDevice);
        Log.d("test", "json:" + jsonStr);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        //PServer_DataExchange.sharedInstance().sendMsg(bytes, handler);
        SocketManager.SendHomeServerMsg(t_controlDevice.getHomeServerSN(),bytes,handler);
        Log.d("test", "打包数据bytes test:" + test);
    }

    /**
     * 向家庭服务器发送命令  获取智能设备详情
     * @param t_device
     * @param handler
     */
    public static void GetOneDeviceInfoByHomeServer(T_Device t_device,Handler handler)
    {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(ModuleCode);//模块编码
        commandStruct.setCommandCode(MyCommandCode.GetOneSDeviceInfo);//功能编码
        commandStruct.setParameterData(t_device.getDevice_ip().getBytes());//IP
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_device);
        Log.d("test", "json:" + jsonStr);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        //PServer_DataExchange.sharedInstance().sendMsg(bytes, handler);
        SocketManager.SendHomeServerMsg(t_device.getHserver_sn(),bytes,handler);
    }
}
