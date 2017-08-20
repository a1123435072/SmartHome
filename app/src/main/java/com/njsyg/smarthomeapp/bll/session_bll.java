package com.njsyg.smarthomeapp.bll;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EDeviceType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyModuleCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.SessionInfo;
import com.njsyg.smarthomeapp.common.PublicInfo;

/**
 * Created by zz on 2016/8/17.
 */
public class session_bll {
    /// <summary>
    ///功能模块
    /// </summary>
    private static final byte ModuleCode = MyModuleCode.SessionModule;
    /**
     * 向公网服务器发送自身信息
     */
    public static void SendSessionByPublicServer(String phoneNum,Handler handler) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setSN(phoneNum);
        sessionInfo.setDeviceType(EDeviceType.Mobile);
        sessionInfo.setIpAddress("");
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(sessionInfo);
        byte[] data = jsonStr.getBytes();
        byte commandCode= MyCommandCode.SendSessionInfo;
        byte[] sendMsg=command_bll.PacketCommand(ModuleCode,commandCode,data);
        command_bll.SendDataToPublicServer(sendMsg,handler);
    }
    /**
     * 向家庭服务器发送自身信息
     */
    public static void SendSessionByHomeServer(String homeServerSN,Handler handler) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setSN(PublicInfo.currentLoginUser.getUser_phoneNumber());
        sessionInfo.setDeviceType(EDeviceType.Mobile);
        sessionInfo.setIpAddress("");
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(sessionInfo);
        byte[] data = jsonStr.getBytes();
        byte commandCode= MyCommandCode.SendSessionInfo;
        byte[] sendMsg=command_bll.PacketCommand(ModuleCode,commandCode,data);
        command_bll.SendDataToHomeServer(homeServerSN,sendMsg,handler);
    }
    /// <summary>
    ///获取自身信息byte[]
    /// </summary>
    public static byte[] GetSessionInfoByte()
    {
        SessionInfo sessionInfo = new SessionInfo();
        if(PublicInfo.currentLoginUser!=null) {
            sessionInfo.setSN(PublicInfo.currentLoginUser.getUser_phoneNumber());
        }
        sessionInfo.setDeviceType(EDeviceType.Mobile);
        sessionInfo.setIpAddress("");
        //将t_device装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(sessionInfo);
        byte[] data = jsonStr.getBytes();
        byte commandCode= MyCommandCode.SendSessionInfo;
        byte[] sendMsg=command_bll.PacketCommand(ModuleCode,commandCode,data);
        return sendMsg;
    }
}
