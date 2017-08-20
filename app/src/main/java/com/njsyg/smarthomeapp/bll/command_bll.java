package com.njsyg.smarthomeapp.bll;

import android.os.Handler;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.common.Converts;


/**
 * Created by zz on 2016/8/18.
 */
public class command_bll {
    /***
     * 组包数据
     * commandCode：命令码
     * data：数据内容
     **/
    public static byte[] PacketCommand(byte moduleCode,byte commandCode, byte[] data) {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(moduleCode); //模块编码
        commandStruct.setCommandCode(commandCode);  //功能编码
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        return bytes ;

    }
    /***
     * 组包数据
     * commandCode：命令码
     * data：数据内容
     **/
    public static byte[] PacketCommand(byte moduleCode,byte commandCode,byte[] paramterData, byte[] data) {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(moduleCode); //模块编码
        commandStruct.setCommandCode(commandCode);  //功能编码
        commandStruct.setParameterData(paramterData);//参数
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        return bytes ;

    }
    /***
     * 向公网服务器发送数据
     * handler：
     * sendData：消息内容
     **/
    public static void SendDataToPublicServer(byte[] sendMsg, Handler handler)
    {
        String test = Converts.byte2HexStr(sendMsg);//测试显示
        Log.d("command_bll", "打包数据bytes:byteslength:" + sendMsg.length + ":" + test);
        SocketManager.SendPublicServerMsg(sendMsg, handler);
    }
    /***
     * 向家庭服务器发送数据
     * handler：
     * sendData：消息内容
     **/
    public static void SendDataToHomeServer(String homeServerSN, byte[] sendData, Handler handler)
    {
        SocketManager.SendHomeServerMsg(homeServerSN,sendData, handler);
    }

    /**
     * 将对象转成JsonString字符串
     * @param
     * @return json字符串
     */
    public static String SerializeObj(Object o)
    {
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(o);
        return  jsonStr;
    }
}
