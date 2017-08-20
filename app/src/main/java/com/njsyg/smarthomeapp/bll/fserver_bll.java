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
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerAndUser;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerKey;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HomeServer;
import com.njsyg.smarthomeapp.common.Converts;
import com.njsyg.smarthomeapp.common.PublicInfo;


/**
 * Created by PCPC on 2016/6/2.
 */
public class fserver_bll {
    private static final byte ModuleCode = MyModuleCode.SDeviceModule;


    /**
     * 获取当前用户所拥有的所有家庭服务器列表
     * @param handler
     */
    public void GetFserverListFromServer(Handler handler) {

        T_HServerAndUser t_hServerAndUser = new T_HServerAndUser();
        t_hServerAndUser.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
        byte CommandCode = MyCommandCode.GetHServerListByPhoneNum;
        PacketDealHServerAndUserCommand(CommandCode, t_hServerAndUser, handler);
    }


    /**
     * 删除家庭服务器
     * @param handler
     * @param t_hServerAndUser
     */
    public static void DeleteFServerByPublicServer(Handler handler, T_HServerAndUser t_hServerAndUser) {
        byte CommandCode = MyCommandCode.DeleteHServer;
        PacketDealHServerAndUserCommand(CommandCode, t_hServerAndUser, handler);
    }

    /**
     * 删除用户与家庭服务器之间的绑定关系
     * @param handler
     * @param t_hServerAndUser
     */
    public static void DeleteUserBindHServerByPublicServer(Handler handler, T_HServerAndUser t_hServerAndUser) {
        byte CommandCode = MyCommandCode.    DeleteUserBindHServer;
        PacketDealHServerAndUserCommand(CommandCode, t_hServerAndUser, handler);
    }

    /**
     * 根据用户手机号码获取家庭服务器列表*
     */
    public static void GetFServerListByPublicServer(Handler handler, T_HServerAndUser t_hServerAndUser) {
        byte CommandCode =MyCommandCode.GetHServerListByPhoneNum;//功能编码
        PacketDealHServerAndUserCommand(CommandCode, t_hServerAndUser, handler);
    }

    /**
     * 用户绑定家庭服务器*
     */
    public static void UserBindFServerByPublicServer(Handler handler, T_HServerAndUser t_hServerAndUser) {
        byte CommandCode =MyCommandCode.UserBindHServer;//功能编码
        PacketDealHServerAndUserCommand(CommandCode, t_hServerAndUser, handler);
    }

    /**
     * 设置秘钥
     */
    public static void SetHServerKeyBySetHServerKey(Handler handler, T_HServerKey t_HServerKey) {
        byte CommandCode = MyCommandCode.SetHServerKey;//功能编码
        PacketDealHServerKeyCommand(CommandCode, t_HServerKey, handler);
    }

    /**
     * 家庭服务器秘钥验证*
     */
    public static void FServerKeyValidateByPublicServer(Handler handler, T_HServerKey t_HServerKey) {
        byte CommandCode = MyCommandCode.HServerKeyValidate;//功能编码
        PacketDealHServerKeyCommand(CommandCode, t_HServerKey, handler);
    }

    /**
     * 修改家庭服务器*
     */
    public static void UpdFServerByPublicServer(Handler handler, T_HomeServer t_HomeServer) {
        byte CommandCode = MyCommandCode.UpdHServer;//功能编码
        PacketDealHomeServerCommand(CommandCode, t_HomeServer, handler);
    }

    /**
     * 设置默认家庭服务器
     */
    public static void SetHServerDefaultByPublicServer(Handler handler, T_HServerAndUser t_hServerAndUser)
    {
        byte CommandCode = MyCommandCode.SetHServerDefault;//功能编码
        PacketDealHServerAndUserCommand(CommandCode, t_hServerAndUser, handler);
    }
    /**
    *获取家庭服务器  （家服sn*）
     */
    public static void GetHServerKeyListByPublicServer(Handler handler, T_HServerKey t_HServerKey) {
        byte CommandCode = MyCommandCode.GetHServerKeyList;//功能编码
        PacketDealHServerKeyCommand(CommandCode, t_HServerKey, handler);
    }
    /// <summary>
    ///向家庭服务器 发送断开连接命令
    /// </summary>
    public static byte[] DisConnectHomeServerByte(T_HomeServer t_HomeServer) {
        byte CommandCode = MyCommandCode.DisConnectHomeServer;//功能编码
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_HomeServer);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(ModuleCode);//模块编码
        commandStruct.setCommandCode(CommandCode);//功能编码
        commandStruct.setData(data);
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        return bytes;
    }


    /***
     * 组包数据  处理用户与家庭服务器的绑定关系
     * commandCode：命令码
     * data：数据内容
     **/
    private static void PacketDealHServerAndUserCommand(byte commandCode, T_HServerAndUser t_hServerAndUser, Handler handler) {
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_hServerAndUser);
        Log.d("test", "json:" + jsonStr);
        byte[] data = jsonStr.getBytes();
        PacketCommand(commandCode, data, handler);
    }

    /***
     * 组包数据 处理家庭服务器秘钥
     * commandCode：命令码
     * T_HServerKey：T_HServerKey
     **/
    private static void PacketDealHServerKeyCommand(byte commandCode, T_HServerKey t_HServerKey, Handler handler) {
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_HServerKey);
        Log.d("test", "json:" + jsonStr);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        PacketCommand(commandCode, data, handler);
    }

    /***
     * 组包数据
     * commandCode：命令码
     * T_HomeServer：T_HomeServer
     **/
    private static void PacketDealHomeServerCommand(byte commandCode, T_HomeServer t_HomeServer, Handler handler) {
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_HomeServer);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        PacketCommand(commandCode, data, handler);
    }

    /***
     * 组包数据
     * commandCode：命令码
     * data：数据内容
     **/
    private static void PacketCommand(byte commandCode, byte[] data, Handler handler) {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode( ModuleCode);//模块编码
        commandStruct.setCommandCode(commandCode);//功能编码
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        SocketManager.SendPublicServerMsg(bytes, handler);
    }

    /**
     * 获取家庭服务器详情----向家庭服务器发送命令
     * @param handler
     * @param t_HomeServer
     */
    public static void GetOneInfoByHomeServer(Handler handler, T_HomeServer t_HomeServer) {
        byte CommandCode = MyCommandCode.GetOneHServerInfo;
        byte[] bytes= PacketSendToHomeServerCommand(CommandCode, t_HomeServer, handler);
        SendCommandToHomeServer(bytes, handler);
    }
    /**
     * 获取家庭服务器详情----向家庭服务器发送命令
     * @param handler
     * @param t_HomeServer
     */
    public static byte[] GetOneInfoByHomeServerByte(Handler handler, T_HomeServer t_HomeServer) {
        byte CommandCode = MyCommandCode.GetOneHServerInfo;
        byte[] bytes= PacketSendToHomeServerCommand(CommandCode, t_HomeServer, handler);
        return bytes;
    }

    /**
     * 组包命令
     * @param commandCode
     * @param t_HomeServer
     * @param handler
     */
    private static byte[] PacketSendToHomeServerCommand(byte commandCode,T_HomeServer t_HomeServer, Handler handler)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_HomeServer);
        byte[] data = jsonStr.getBytes();    //将string转成byte数组
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode(ModuleCode);//模块编码
        commandStruct.setCommandCode(commandCode);//功能编码
        commandStruct.setData(data);//数据
        //commandStruct.CheckCode= MyCRC.CRC16(byte);//校验码
        byte[] versionbyte = {(byte) 0x00, (byte) 0x00};
        commandStruct.setProtocolVersion(versionbyte);//协议版本//
        Log.d("test", "打包数据bytes前");
        byte[] bytes = CommandHelper.PackageCommand(commandStruct);
        String test = Converts.byte2HexStr(bytes);//测试显示
        Log.d("test", "打包数据bytes:byteslength:" + bytes.length + ":" + test);
        return bytes;
    }

    /**
     * 向家庭服务器发送命令
     * @param bytes
     * @param handler
     */
    private static void SendCommandToHomeServer(byte[] bytes,Handler handler)
    {
        SocketManager.SendPublicServerMsg(bytes, handler);
    }
}
