package com.njsyg.smarthomeapp.bll.socket_bll;

import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;

/**
 * Created by zz on 2016/10/21.
 */
public class SocketServerModel {
    private EServerType serverType;
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    private int port;

    public int getPort() {
        return port;
    }

    private int connectExMaxCount = 3;

    public int getconnectExMaxCount() {
        return connectExMaxCount;
    }

    /*
    连接异常次数
     */
    private int connectExCount = 0;
    /*
    判断连接是否正常
     */
    private boolean isConnectNor = false;

    public boolean getIsConnectNor() {
        return isConnectNor;
    }

    public SocketServerModel(EServerType serverType, String ipAddress, int port) {
        this.serverType = serverType;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public SocketServerModel(EServerType serverType, String ipAddress, int port, int outLMaxCount) {
        this.serverType = serverType;
        this.ipAddress = ipAddress;
        this.port = port;
        this.connectExMaxCount = outLMaxCount;
    }

    /*
    连接正常
     */
    public void connectNor() {
        if (!isConnectNor) {
            isConnectNor = true;
        }
        connectExCount = 0;
    }

    /*
    连接异常，无法确定的连接异常
     */
    public void connectEx() {
        connectExCount++;
        if (connectExCount > connectExMaxCount) {
            connectExCount = connectExMaxCount;
            isConnectNor = false;
        }
    }

    /**
     * 连接中断，可确定的连接中断，例如读取到数据流的结尾
     */
    public void connnectInterrupt() {
        if (isConnectNor) {
            isConnectNor = false;
        }
        connectExCount = 0;
    }
}
