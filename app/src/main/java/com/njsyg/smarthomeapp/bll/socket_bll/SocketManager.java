package com.njsyg.smarthomeapp.bll.socket_bll;

import android.os.Handler;

import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.common.Const;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by zz on 2016/10/21.
 */
public class SocketManager {
    private static SocketClient socketClientPublicServer;//公网服务器
    private static HashMap<String, SocketClient> socketHomeServerClientHashMap = new HashMap<>();//家庭服务器列表

    public SocketManager() {

    }
    //初始化公网服务器socket
    public static void initializePublicServer() {
        socketClientPublicServer = new SocketClient(EServerType.PublicServer, Const.PServerSOCKET_IP, Const.PServerSOCKET_PORT);
        socketClientPublicServer.initialize();
        socketClientPublicServer.connect();
        socketClientPublicServer.startHeartTimer();
    }

    //停止公网服务器socket
    public static void stopPublicServer() {
        if(socketClientPublicServer!=null)
        {
            socketClientPublicServer.dispose();
            socketClientPublicServer.stopHeartTimer();
        }
    }
    //发送数据
    public static void SendPublicServerMsg(byte[] bytes, Handler sendHandler) {
        if (socketClientPublicServer!=null) {
            socketClientPublicServer.asySendMsg(bytes, sendHandler);
        }
    }
    //设置读取公网服务器数据委托
    public static void setReadPublicServerDataDelegate(SocketClient.ReadDataDelegate _delegate) {
        if (socketClientPublicServer!=null) {
            socketClientPublicServer.SetReadDataDelegate(_delegate);
        }
    }
    /// <summary>
    ///获取公网服务器链接状态
    /// </summary>
    public static boolean getPublicServerConnectState(String sn) {
        boolean isConnected = socketClientPublicServer.serverModel.getIsConnectNor();
        return isConnected;
    }

    //初始化家庭服务器socket
    public static void initializeHomeServer(String sn,SocketClient socketClient) {
        if (socketClient != null) {
            socketClient.initialize();
            socketClient.connect();
            socketClient.startHeartTimer();

            socketHomeServerClientHashMap.put(sn,socketClient);

        }
    }
    //向家庭服务器发送数据
    public static void SendHomeServerMsg(String sn, byte[] bytes, Handler sendHandler) {
        if (socketHomeServerClientHashMap.containsKey(sn)) {
            socketHomeServerClientHashMap.get(sn).asySendMsg(bytes, sendHandler);
        }
    }

    //停止家庭服务器服务
    public static void stopHomeServer() {
        Iterator iter = socketHomeServerClientHashMap.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            SocketClient socketClient = socketHomeServerClientHashMap.get(key);
            socketClient.stopHeartTimer();
            socketClient.dispose();
        }
    }
    //停止指定家庭服务器服务
    public static void stopHomeServer(String sn) {
        if (socketHomeServerClientHashMap.containsKey(sn)) {
            SocketClient socketClient = socketHomeServerClientHashMap.get(sn);
            socketClient.stopHeartTimer();
            socketClient.SetReadDataDelegate(null);
            socketClient.dispose();
        }
    }

    //设置读取家庭服务器数据委托
    public static void setReadHomeServerDataDelegate(String sn, SocketClient.ReadDataDelegate _delegate) {
        if (socketHomeServerClientHashMap.containsKey(sn)) {
            socketHomeServerClientHashMap.get(sn).SetReadDataDelegate(_delegate);
        }
    }
    /// <summary>
    ///获取家庭服务器连接状态
    /// </summary>
    public static boolean getHomeServerConnectState(String sn)
    {
        boolean isConnected=false;
        if (socketHomeServerClientHashMap.containsKey(sn)) {
            SocketClient socketClient=   socketHomeServerClientHashMap.get(sn);
            isConnected=socketClient.serverModel.getIsConnectNor() ;
        }
        return  isConnected;
    }
}
