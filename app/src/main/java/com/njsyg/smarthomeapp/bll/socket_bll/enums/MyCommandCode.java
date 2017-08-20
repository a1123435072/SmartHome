package com.njsyg.smarthomeapp.bll.socket_bll.enums;

/**
 * Created by zz on 2016/10/21.
 */
public class MyCommandCode {
    public static final byte GetHServerListByPhoneNum = 0x01;
    public static final byte UserBindHServer = 0x02;
    public static final byte SetHServerKey = 0x03;
    public static final byte HServerKeyValidate = 0x04;
    public static final byte GetDeviceList = 0x05;
    public static final byte AddDevice = 0x06;
    public static final byte UpdDevice = 0x07;
    public static final byte DeleteDevice = 0x08;
    public static final byte AddHServer = 0x09;
    public static final byte UpdHServer = 0x0a;
    public static final byte DeleteHServer = 0x0b;
    public static final byte DeleteUserBindHServer = 0x0c;
    public static final byte GetHServerKeyList = 0x0d;
    public static final byte SetHServerDefault = 0x0e;//获取智能设备信息

    public static final byte GetOneHServerInfo = 0x0e;//获取家庭服务器信息
    public static final byte GetOneSDeviceInfo = 0x0f;//获取智能设备信息
    public static final byte GetDeviceRealData = 0x11;//智能设备实时数据
    public static final byte SetDeviceState = 0x12;// 控制智能设备
    public static final byte DisConnectHomeServer = 0x13;// 断开和家庭服务器的连接
    public static final  byte GetDeviceHisData=0x22;//获取智能设备历史数据
    public static final  byte DeviceRealonlineRecord=0x23;//智能设备在线状态

    public static final byte UserPhoneNumberValidate = 0x01;//用戶手机号码验证
    public static final byte UserReg = 0x02;//用戶注册
    public static final byte UserLogin=0x03;//用戶登录
    public static final byte UserUpdatePwd=0x04;//更新用户密码
    public static final byte UserEdit=0x05;//更新用户密码

    public static final byte SendSessionInfo=0x02;//向公网服务器发送自身设备信息
    public static final byte GetTaskListByPhoneNumAndDeviceSN=0x01;
    public static final byte AddTask=0x02;//添加任务
    public static final byte UpdTask=0x03;//更新任务
    public static final byte DeleteTask=0x04;//删除任务
    public static final byte UpdTaskState=0x05;//更新任务状态
    public static final byte GetTaskHisList=0x07;//获取任务历史执行状态


    //与智能设备相关命令码
    public static final byte ReadDeviceBasicInfo=0x01;//读取设备基本信息
    public static final byte ReadDeviceNetworkInfo=0x02;//读取设备网络信息
    public static final byte ReadDeviceConfigurationInfo=0x03;//读取设备配置信息
    public static final byte ReadDeviceSwitchStatus=0x04;//读取开关状态
    public static final byte ReadDeviceElectricityInfo=0x05;//读取用电情况
}
