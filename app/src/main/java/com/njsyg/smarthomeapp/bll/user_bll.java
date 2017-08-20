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
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_User;
import com.njsyg.smarthomeapp.common.Converts;

/**
 * Created by PCPC on 2016/6/2.
 */
public class user_bll {

    public static final byte ModuleCode = MyModuleCode.UserModule;

    /***
     * 用户注册
     */
    public static void UserRegByPublicServer(Handler handler, T_User t_user) {
        byte commandCode = MyCommandCode.UserReg;
        DealCommand(commandCode, t_user, handler);
    }

    /*
    * 用户登录
    * */
    public static void UserLoginByPublicServer(Handler handler, T_User t_user) {
        byte commandCode = MyCommandCode.UserLogin;
        DealCommand(commandCode, t_user, handler);
    }

    /****
     * 验证手机号是否可以注册
     *****/
    public static void PhoneNumberValidateByPublicServer(Handler handler, T_User t_user) {
        byte commandCode = MyCommandCode.UserPhoneNumberValidate;
        DealCommand(commandCode, t_user, handler);
    }

    /*
    * 修改密码
    */
    public static void UpdatePasswordByPublicServer(Handler handler, T_User t_user) {
        byte commandCode = MyCommandCode.UserUpdatePwd;
        DealCommand(commandCode, t_user, handler);
    }

    /*
 * 修改用户信息
 */
    public static void UpdateUserByPublicServer(Handler handler, T_User t_user) {
        byte commandCode = MyCommandCode.UserEdit;
        DealCommand(commandCode, t_user, handler);
    }

    /***
     * 组包数据
     * commandCode：命令码
     * data：数据内容
     **/
    private static void DealCommand(byte commandCode, T_User t_user, Handler handler) {
        CommandStruct commandStruct = new CommandStruct();
        commandStruct.setModuleCode( ModuleCode);//模块编码
        commandStruct.setCommandCode(commandCode);//功能编码
        //将t_user装成json
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(t_user);
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
        SocketManager.SendPublicServerMsg(bytes, handler);
        Log.d("test", "打包数据bytes test:" + test);
    }
}
