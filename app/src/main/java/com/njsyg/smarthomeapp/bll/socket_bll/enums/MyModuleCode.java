package com.njsyg.smarthomeapp.bll.socket_bll.enums;

/**
 * Created by zz on 2016/10/21.
 */
public class MyModuleCode {
    public static final byte SessionModule = 0x01;
    /// <summary>
    ///用户管理功能模块
    /// </summary>
    public static final byte UserModule = 0x02;
    /// <summary>
    ///智能设备管理功能模块
    /// </summary>
    public static final byte SDeviceModule = 0x03;
    /// <summary>
    ///命令转发
    /// </summary>
    public static final byte TransferModule=0x04;
    /// <summary>
    ///定时任务管理功能模块
    /// </summary>
    public static final byte MissionsModule = 0x05;


    ////////////////////////////////////////////
    //与智能设备通信模块编码
    public static final byte ReadCode=0x01;//读取
    public static final byte ControlCode=0x02;//控制
    public static final byte SetCode=0x03;//配置
    public static final byte OthersCode=0x04;//其他
}
