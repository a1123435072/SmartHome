package com.njsyg.smarthomeapp.common;

/**
 * Created by zz on 2016/10/21.
 * 系统的变量
 */
public class Const {
    public static String PServerSOCKET_IP = "192.168.1.135";
    //public static String PServerSOCKET_IP="183.62.138.17";
    public static int PServerSOCKET_PORT = 9990;
    public static int HomeServerSOCKET_PORT = 9995;
    // 默认timeout 时间 60s
    public final static int SOCKET_TIMOUT = 60 * 1000;
    public final static int SOCKET_READ_TIMOUT = 15 * 1000;
    //如果没有连接无服务器。读线程的sleep时间
    public final static int SOCKET_SLEEP_SECOND = 3;
    //心跳包发送间隔时间
    public final static int SOCKET_HEART_SECOND = 5;
    public final static String BC = "BC";

    //设备历史记录的URL
   
    public final static String HIS_DATA_URL = "http://192.168.1.119:3000/tools/gethisData/getHis?";

}
