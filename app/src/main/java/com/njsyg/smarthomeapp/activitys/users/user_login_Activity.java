package com.njsyg.smarthomeapp.activitys.users;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.MainActivity;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_Device_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServerAndUser_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServerKey_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServer_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_User_BLL;
import com.njsyg.smarthomeapp.bll.sdevice_bll;
import com.njsyg.smarthomeapp.bll.session_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_Device;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerKey;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_User;
import com.njsyg.smarthomeapp.bll.socket_bll.models.View_HServerAndUser;
import com.njsyg.smarthomeapp.bll.user_bll;
import com.njsyg.smarthomeapp.common.Const;
import com.njsyg.smarthomeapp.common.MyKeyType;
import com.njsyg.smarthomeapp.common.PublicInfo;
import com.njsyg.smarthomeapp.common.utils.DialogTool;
import com.njsyg.smarthomeapp.common.utils.FormatValidateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import njsyg.greendao.db.model.DB_Device;
import njsyg.greendao.db.model.DB_HServerAndUser;
import njsyg.greendao.db.model.DB_HServerKey;
import njsyg.greendao.db.model.DB_HomeServer;
import njsyg.greendao.db.model.DB_User;

public class user_login_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate{

    private final String Tag = "user_login_Activity";
    private TextView editTextFrogotPwd;
    private TextView editTextReg;
    private EditText editTextPhoneNum;
    private EditText editTextPwd;
    private Button buttonLogin;
    Handler handler = null;
    Dialog mydialog;
    List<DB_HomeServer> needKeyValidate = new ArrayList<DB_HomeServer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_);
        InitView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag, "取消委托对象");
        SocketManager.setReadPublicServerDataDelegate(null);;//取消委托对象
    }


    /**
     *初始化View
     * */
    private void InitView() {
        editTextPhoneNum = (EditText) findViewById(R.id.userNameText);
        editTextPwd = (EditText) findViewById(R.id.passwdText);
        buttonLogin = (Button) findViewById(R.id.bnLogin);
        editTextFrogotPwd = (TextView) findViewById(R.id.login_txtForgotPwd);
        editTextReg = (TextView) findViewById(R.id.login_txtReg);
        buttonLogin.setOnClickListener(this);
        editTextFrogotPwd.setOnClickListener(this);
        editTextReg.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this);
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d(Tag, "发送socket失败");
                        Toast.makeText(user_login_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        //progressDialog.show();
                        break;
                    case 2:
                        Toast.makeText(user_login_Activity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        List<View_HServerAndUser> list = (List<View_HServerAndUser>) msg.obj;
                        SendSessioninfoToPublicServer();
                        startHomeServerService(list);
                        Deal(list);
                        break;
                    case 3:
                        Toast.makeText(user_login_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bnLogin:
                String phone = editTextPhoneNum.getText().toString().trim();
                String pwd = editTextPwd.getText().toString().trim();
                if ("".equals(phone) || "".equals(pwd)) {
                    Toast.makeText(this, "亲,别闹,赶快登录吧....", Toast.LENGTH_SHORT).show();
                    break;
                }
                //是否是手机号
                if (FormatValidateUtils.isMobileNO(phone)) {
                    T_User t_user = new T_User();
                    t_user.setUser_phoneNumber(phone);
                    t_user.setUser_pwd(pwd);
                    user_bll.UserLoginByPublicServer(handler, t_user);
                } else {
                    Toast.makeText(this, "手机号格式有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_txtForgotPwd:
                Intent intentFindPwd = new Intent(this, user_findpwd_Activity.class);
                startActivity(intentFindPwd);
                break;
            case R.id.login_txtReg:
                Intent intentReg = new Intent(this, user_register_Activity.class);
                startActivity(intentReg);
                break;
        }
    }

    /**
     * 向公网服务器发送自身信息
     */
   private void  SendSessioninfoToPublicServer()
   {
       session_bll.SendSessionByPublicServer(PublicInfo.currentLoginUser.getUser_phoneNumber(),handler);
   }


    /**
     * 启动家庭家庭服务器socket服务
     * @param list
     */
    private void startHomeServerService(final List<View_HServerAndUser> list) {
        if (list != null) {
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (View_HServerAndUser x : list) {
                        SocketClient socketClient = new SocketClient(EServerType.HomeServer, x.getHserver_ip(), Const.HomeServerSOCKET_PORT);
                        SocketManager.initializeHomeServer(x.getHserver_sn(), socketClient);
                    }
                }
            });
            one.start();
        }
    }
    /**
     *删除家庭服务器下所有智能设备*
     */
    private void LoacalDB_DeleteAllDeviceByFServerSN(String fserverSN)
    {
        DB_Device_BLL.Delete(fserverSN);
    }
    /**
     *本地保存智能设备*
     */
    private void SaveDeviceToLoacalDB(List<T_Device> deviceList)
    {
        Log.d("test","SaveDeviceToLoacalDB List<T_Device> deviceList");
        if(deviceList!=null) {
            for (T_Device x : deviceList) {
                DB_Device one=new DB_Device();
                one.setHserver_sn(x.getHserver_sn());
                one.setDevice_ip(x.getDevice_ip());
                one.setDevice_type(x.getDevice_type());
                one.setDevice_installPlace(x.getDevice_installPlace());
                one.setDevice_nickName(x.getDevice_nickName());
                one.setDevice_sn(x.getDevice_sn());
                one.setRemark(x.getRemark());
                DB_Device_BLL.Add(one);
            }
        }
        Intent intent = new Intent(user_login_Activity.this, MainActivity.class);
        intent.putExtra("selectTapIndex",0);
        startActivity(intent);
        finish();
    }
    /**
     *从公网服务器获取智能设备列表*
     */
    private void GetDeviceListByPublicServer(String fserverSN) {
        LoacalDB_DeleteAllDeviceByFServerSN(fserverSN);
        T_Device t_device = new T_Device();
        t_device.setHserver_sn(fserverSN);
        sdevice_bll.GetDeviceListByPublicServer(handler, t_device);
    }
    private void  DealGetDeviceList(CommandStruct commandStruct)
    {
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("DealGetDeviceList", dataStr);
        //再将string转成对象
        ResultInfo<List<T_Device>> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<List<T_Device>>>() {
        }.getType());
        boolean resultInfoState = resultInfo.getState();
        if (resultInfoState) {
            SaveDeviceToLoacalDB(resultInfo.getData());
        }else {
            Intent intent = new Intent(user_login_Activity.this, MainActivity.class);
            intent.putExtra("selectTapIndex",0);
            startActivity(intent);
            finish();
        }
    }

    /**
    *验证秘钥*
     */
    private void  Deal( List<View_HServerAndUser> list ) {
        PublicInfo.currentUserHServerList = list;
        if (list.size() == 0) {
            //弹出框提示 该用户没有绑定家庭服务器是否现在绑定
            mydialog = DialogTool.createConfirmDialog(user_login_Activity.this, "标题", "没有绑定家庭服务器是否现在绑定？", "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            //打开绑定页面
//                            Intent intent = new Intent(user_login_Activity.this, device_main_Activity.class);
//                            startActivity(intent);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(user_login_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, DialogTool.NO_ICON);
            mydialog.show();
        } else {
            //判断秘钥是否正确
            for (View_HServerAndUser x : list) {
                GetDeviceListByPublicServer(x.getHserver_sn());
                List<T_HServerKey> publicKeyList = x.getT_hserverKeyList();
                if (publicKeyList != null && publicKeyList.size() > 0) {
                    int ret = ValidateKey(publicKeyList, x.getHserver_sn());
                    if (ret == MyKeyType.Default) {
                        //输入秘钥进行验证
                        DB_HomeServer db_homeServer = new DB_HomeServer();
                        db_homeServer.setHserver_sn(x.getHserver_sn());
                        db_homeServer.setHserver_nickName(x.getHserver_nickName());
                        db_homeServer.setHserver_ip(x.getHserver_ip());
                        needKeyValidate.add(db_homeServer);
                    }
                } else if (publicKeyList.size() == 0) {
                    //没有秘钥，需要设置秘钥
                }
                SaveUserAndHomeServerToLoaclDB(x.getHserver_sn(), x.getHserver_nickName(), x.getRemark(),x.getIsdefault());
                SaveHomeServerToLoaclDB(x.getHserver_sn(), x.getHserver_ip(), x.getHserver_nickName(), x.getRemark());
            }
            if (needKeyValidate.size() > 0) {
                //弹出框提示 该用户没有绑定家庭服务器是否现在绑定
                mydialog = DialogTool.createConfirmDialog(user_login_Activity.this, "秘钥验证", "需要验证家庭服务器秘钥？", "确定", "",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                //需要验证家庭服务器秘钥
//                                Intent intent = new Intent(user_login_Activity.this, fserver_validateallkey_Activity.class);
//                                intent.putExtra("NeedKeyValidateFServerList", (Serializable) needKeyValidate);
//                                startActivity(intent);
                            }
                        }, null, DialogTool.NO_ICON);
                mydialog.show();
            } else {
//                Intent intent = new Intent(user_login_Activity.this, MainActivity.class);
//                startActivity(intent);
            }
        }
    }

    /**
     * 将用户绑定的家庭服务器保存到本地*
     */
    private void SaveUserAndHomeServerToLoaclDB(String fserverSN, String nickName, String remark,int isdefault) {
        //判断本地是否有、有则更新；没有添加
        DB_HServerAndUser tmpItem = DB_HServerAndUser_BLL.GetOne(fserverSN, PublicInfo.currentLoginUser.getUser_phoneNumber());
        if (tmpItem != null) {
            tmpItem.setHserver_nickName(nickName);
            tmpItem.setRemark(remark);
            tmpItem.setIsdefault(isdefault);
            tmpItem.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
            DB_HServerAndUser_BLL.Update(tmpItem);
        } else {
            DB_HServerAndUser item = new DB_HServerAndUser();

            item.setHserver_sn(fserverSN);
            item.setHserver_nickName(nickName);
            item.setRemark(remark);
            item.setIsdefault(isdefault);
            item.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
            DB_HServerAndUser_BLL.Add(item);
        }
    }

    /**
     * 将家庭服务器信息保存到本地*
     */
    private void SaveHomeServerToLoaclDB(String fserverSN, String fserverIP, String fserverName, String remark) {
        //判断本地是否有
        DB_HomeServer tempItem = DB_HServer_BLL.GetOne(fserverSN);
        if (tempItem != null) {
            tempItem.setHserver_nickName(fserverName);
            tempItem.setHserver_ip(fserverIP);
            tempItem.setRemark(remark);
            DB_HServer_BLL.Update(tempItem);
        } else {
            DB_HomeServer item = new DB_HomeServer();
            item.setHserver_sn(fserverSN);
            item.setRemark(remark);
            item.setHserver_ip(fserverIP);
            item.setHserver_nickName(fserverName);
            DB_HServer_BLL.Add(item);
        }
    }

    /**
     * 判断本地秘钥是否正确*
     */
    private int ValidateKey(List<T_HServerKey> pubkeylist, String fserversn) {
        int keyType = -1;
        for (T_HServerKey pkey : pubkeylist) {
            List<DB_HServerKey> dbkeylist = DB_HServerKey_BLL.GetList(fserversn, pkey.getKeyvalue());
            if (dbkeylist != null && dbkeylist.size() > 0) {
                if (pkey.getKeytype() == MyKeyType.SuperKey) {
                    keyType = 1;
                }
                if (pkey.getKeytype() == MyKeyType.NormalKey) {
                    if (keyType == -1) {
                        keyType = 0;
                    }
                }
            }
        }
        return keyType;
    }
    /**
    *保存用户信息到本地*
     */
    private void SaveUserToLoacalDB(T_User t_user) {
        DB_User db_user = DB_User_BLL.GetOne(t_user.getUser_phoneNumber());
        if (db_user != null) {
            db_user.setUser_nickName(t_user.getUser_nickName());
            db_user.setUser_email(t_user.getUser_email());
            db_user.setUser_pwd(t_user.getUser_pwd());
            db_user.setUser_headImg(t_user.getUser_headImg());
            db_user.setRemark(t_user.getRemark());
            DB_User_BLL.Update(db_user);
        } else {
            db_user = new DB_User();
            db_user.setUser_phoneNumber(t_user.getUser_phoneNumber());
            db_user.setUser_nickName(t_user.getUser_nickName());
            db_user.setUser_email(t_user.getUser_email());
            db_user.setUser_pwd(t_user.getUser_pwd());
            db_user.setUser_headImg(t_user.getUser_headImg());
            db_user.setRemark(t_user.getRemark());
            DB_User_BLL.Add(db_user);
        }
    }

    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {

        //解析公网服务器返回的数据
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        Log.d("Tag", "接收到公网服务器返回的数据"+commandStruct.getCommandCode());
        if (commandStruct == null) {
            Log.d("test", "commdhelper.UnPackageCommand is null");
            return;
        }
        if (commandStruct.getCommandCode() == MyCommandCode.UserLogin) {
            Gson gson = new Gson();
            //现将byte[] 转成string
            String dataStr = new String(commandStruct.getData());
            Log.d("test", dataStr);
            //再将string转成对象
            ResultInfo<T_User> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_User>>() {
            }.getType());
            if(resultInfo!=null) {
                boolean resultInfoState = resultInfo.getState();
                if (resultInfoState) {
                    SaveUserToLoacalDB(resultInfo.getData());
                    //登录成功
                    PublicInfo.currentLoginUser = resultInfo.getData();
                    Message tempMsg = handler.obtainMessage();
                    tempMsg.what = 2;
                    List<View_HServerAndUser> view_hServerAndUser = resultInfo.getData().getView_hServerAndUser();
                    tempMsg.obj = view_hServerAndUser;
                    handler.sendMessage(tempMsg);
                } else {
                    Message tempMsg = handler.obtainMessage();
                    tempMsg.what = 3;
                    tempMsg.obj = "登录失败！密码有误，请重新输入！";
                    handler.sendMessage(tempMsg);
                }
            }
        }
        else  if(commandStruct.getCommandCode()==MyCommandCode.GetDeviceList)//获取家庭服务器列表
        {
            Log.d("test","MyCommandCode.GetDeviceList");
            DealGetDeviceList(commandStruct);
        }
    }
}