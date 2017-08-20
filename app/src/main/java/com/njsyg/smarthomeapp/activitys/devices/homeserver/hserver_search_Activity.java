package com.njsyg.smarthomeapp.activitys.devices.homeserver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.devices.device_main_Activity;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServerAndUser_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServer_BLL;
import com.njsyg.smarthomeapp.bll.fserver_bll;
import com.njsyg.smarthomeapp.bll.session_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerAndUser;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerKey;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HomeServer;
import com.njsyg.smarthomeapp.bll.socket_bll.models.View_HServerAndUser;
import com.njsyg.smarthomeapp.common.Const;
import com.njsyg.smarthomeapp.common.PublicInfo;
import com.njsyg.smarthomeapp.common.utils.DialogTool;
import com.njsyg.smarthomeapp.common.utils.RadarView;

import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import njsyg.greendao.db.model.DB_HServerAndUser;
import njsyg.greendao.db.model.DB_HomeServer;

public class hserver_search_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate {
    final String Tag = "device_search_Activity";
    private int deviceType;
    private String addMode;
    private String IP;
    String SN = "";
    String NickName = "";
    TextView txtTime;
    TextView txtCancle;
    int timerseconds = 30;
    List<String> ipList = new ArrayList<String>();
    Dialog dialog;
    HashMap<Integer, String> hashMap_key = new HashMap<>();
    RadarSweep radarSweep;
    private Thread radarSweepThread;
    private RadarView radarView;
    Handler handler = null;
    TimerTask task = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hserver_search);
        InitView();
        InitHandler();
    }

    /**
     * 初始化View
     */
    private void InitView() {
        Intent intent = getIntent();
        deviceType = intent.getIntExtra("deviceType", -1);
        addMode = intent.getStringExtra("addMode");
        txtTime = (TextView) findViewById(R.id.txt_searchtime);
        txtCancle = (TextView) findViewById(R.id.txt_cancle);
        radarView = (RadarView) findViewById(R.id.radar);
        txtCancle.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this);//注册委托对象
        if (addMode.equals("Search")) {
            ShowDialog();
        } else {
            StartOneTimeTask();
        }
    }

    /**
     * 初始化Handler
     */
    private void InitHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d(Tag, "发送socket失败");
                        Toast.makeText(hserver_search_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        break;
                    case 2:
                        //绑定成功
                        Toast.makeText(hserver_search_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        UserBindFServerToLocalDB();
                        BackToDeviceMainPage();
                        break;
                    case 3:
                        //绑定失败
                        Toast.makeText(hserver_search_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        timerseconds--;
                        txtTime.setText(timerseconds + "秒");
                        if (timerseconds == 0) {
                            toConnect = false;
                            StopOneTimeTask();
                        }
                        break;
                    case 5:
                        List<T_HServerKey> t_hServerKeyList = (List<T_HServerKey>) msg.obj;
                        break;
                    case 6:
                        Log.d("test", "StopOneTimeTask");
                        StopOneTimeTask();
                        SearchFServerSuccess((T_HomeServer) msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        SocketManager.setReadPublicServerDataDelegate(null);//取消委托对象
        SocketManager.setReadHomeServerDataDelegate(SN,null);
    }
    @Override
    public void onBackPressed() {
        BackToDeviceMainPage();//返回到设备管理首页
    }

    /**
     * 弹出提示框
     */
    public void ShowDialog() {
        //定义1个文本输入框
        final EditText userName = new EditText(this);
        //创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请输入")//设置对话框标题
                .setIcon(android.R.drawable.ic_dialog_info)//设置对话框图标
                .setView(userName)//为对话框添加要显示的组件
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置对话框[肯定]按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IP = userName.getText().toString();//输入IP
                        if(IP.equals("")){
                            //输入框内容为空时不调用dismiss
                            Toast.makeText(hserver_search_Activity.this, "请输入IP地址", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            ShowDialog();
                            return;
                        }
                        toConnect = true;
                        StartOneTimeTask();
                    }
                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        hserver_search_Activity.this.finish();
                    }
                });

        builder.setCancelable(false);
        builder.show();
    }

    Timer timer;

    /***
     * 启动时间任务
     **/
    private void StartOneTimeTask() {
        toConnect = true;
        ConnectFServer(IP);
        timerseconds = 30;
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }
        };
        ipList.clear();
        if(radarSweepThread==null) {
            radarSweep = new RadarSweep();
            radarSweepThread = new Thread(radarSweep);//雷达扫描线程
            radarSweepThread.start();
            timer = new Timer(true);
            timer.schedule(task, 0, 1000); //延时1000ms后执行，1000ms执行一次
        }
    }

    /***
     * 关闭时间任务
     **/
    private void StopOneTimeTask() {
        toConnect = false;
        if (timer != null) timer.cancel();
        if(radarSweep!=null&&radarSweepThread!=null) {
            radarSweep.i = 0;
            radarSweepThread.interrupt();
            radarSweepThread = null;
        }
        if (timerseconds > 0 && txtCancle.getText().equals("取消")) {

                txtCancle.setText("再试一次");
                txtTime.setText("30秒");
                timerseconds = 30;
                return;

        }
        if (timerseconds == 30 && txtCancle.getText().equals("再试一次")) {
            txtCancle.setText("取消");
            StartOneTimeTask();
            return;
        }
        if (ipList.size() == 0 && timerseconds == 0 && txtCancle.getText().equals("取消")) {
            String msg = "1.请确认主机已接通电源 \n2.请确认主机与与手机连接到相同路由器";
            dialog = DialogTool.createConfirmDialog(this, "未找到主机", msg, "取消", "再试一次",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //取消
                            BackToDeviceMainPage();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //再试一次
                            StartOneTimeTask();
                        }
                    }, DialogTool.NO_ICON);
            dialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_cancle:
                StopOneTimeTask();
                break;
        }
    }

    boolean toConnect = true;
    private void ConnectFServer(final String ip) {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                int port = 9995;

                SocketClient socketClientFserver = new SocketClient(EServerType.HomeServer, ip, port);
                socketClientFserver.initialize();
                socketClientFserver.connect();
                socketClientFserver.startHeartTimer();
                socketClientFserver.SetReadDataDelegate(hserver_search_Activity.this);
                T_HomeServer one = new T_HomeServer();
                one.setFromPhoneNum(PublicInfo.currentLoginUser.getUser_phoneNumber());
                byte[] data = fserver_bll.GetOneInfoByHomeServerByte(handler, one);
                while (toConnect) {
                    socketClientFserver.synSendMsg(data, handler);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                byte[] datas= fserver_bll.DisConnectHomeServerByte(one);
                socketClientFserver.synSendMsg(datas,null);
                socketClientFserver.SetReadDataDelegate(null);
                socketClientFserver.dispose();

            }
        });
        one.start();
    }

    /******
     * 成功找到家庭服务器
     */
    private void SearchFServerSuccess(final T_HomeServer t_homeServer) {
        //判断该家庭服务器是否已经存在
        SN = t_homeServer.getHserver_sn();
        final EditText txtNickName = new EditText(this);
        txtNickName.setText("家庭服务器(" + IP + ")");
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("找到设备")//设置对话框标题
                .setIcon(android.R.drawable.ic_dialog_info)//设置对话框图标
                .setView(txtNickName)//为对话框添加要显示的组件
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {//设置对话框[肯定]按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NickName = t_homeServer.getHserver_nickName();
                        SaveFServerToLoaclDB(t_homeServer);
                        HashMap<String, DB_HServerAndUser> hashMap_Hserver = GetFServerListLocalDB();
                        if (hashMap_Hserver.containsKey(SN)) {
                            Toast.makeText(hserver_search_Activity.this, "该家庭服务器已经绑定", Toast.LENGTH_SHORT).show();
                            hserver_search_Activity.this.finish();
                        }
                        UserBindFServerByPublicServer(t_homeServer);
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 获取该用户绑定的家庭服务器列表*
     */
    private HashMap<String, DB_HServerAndUser> GetFServerListLocalDB() {
        List<DB_HServerAndUser> list = DB_HServerAndUser_BLL.GetListByPhoneNum(PublicInfo.currentLoginUser.getUser_phoneNumber());
        HashMap<String, DB_HServerAndUser> hashMap_Hserver = new HashMap<String, DB_HServerAndUser>();
        if (list != null && list.size() > 0) {
            for (DB_HServerAndUser x : list) {
                if (!hashMap_Hserver.containsKey(x.getHserver_sn())) {
                    hashMap_Hserver.put(x.getHserver_sn(), x);
                }
            }
        }
        return hashMap_Hserver;
    }

    private void GetHServerKeyListByPublicServer() {
        T_HServerKey t_HServerKey = new T_HServerKey();
        t_HServerKey.setHserver_sn(SN);
        fserver_bll.GetHServerKeyListByPublicServer(handler, t_HServerKey);
    }

    /**
     * 验证家服秘钥*
     */
    private void KeyValidate(List<T_HServerKey> t_hServerKeyList, final T_HomeServer t_homeServer) {
        if (t_hServerKeyList != null) {
            if (t_hServerKeyList.size() == 0) {
                //直接绑定
                UserBindFServerByPublicServer(t_homeServer);
            } else {

                for (T_HServerKey x : t_hServerKeyList) {
                    if (!hashMap_key.containsKey(x.getKeytype())) {
                        hashMap_key.put(x.getKeytype(), x.getKeyvalue());
                    }
                }
                final EditText txtKey = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入家庭服务器秘钥")//设置对话框标题
                        .setIcon(android.R.drawable.ic_dialog_info)//设置对话框图标
                        .setView(txtKey)//为对话框添加要显示的组件
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {//设置对话框[肯定]按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String key = txtKey.getText().toString();
                                if (hashMap_key.containsValue(key)) {
                                    UserBindFServerByPublicServer(t_homeServer);
                                } else {
                                    Toast.makeText(hserver_search_Activity.this, "家庭服务器秘钥输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)//设置对话框[否定]按钮
                        .show();

            }
        }
    }

    /***********
     * 向公网服务器发命令：绑定用户和家庭服务器之间关系
     *
     * @param t_homeServer：家庭服务器
     */
    private void UserBindFServerByPublicServer(T_HomeServer t_homeServer) {
        T_HServerAndUser t_hServerAndUser = new T_HServerAndUser();
        t_hServerAndUser.setHserver_sn(t_homeServer.getHserver_sn());
        t_hServerAndUser.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
        t_hServerAndUser.setRemark(t_homeServer.getRemark());
        t_hServerAndUser.setHserver_nickName(t_homeServer.getHserver_nickName());
        t_hServerAndUser.setIsdefault(0);
        Date nowdate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.format(nowdate);
        t_hServerAndUser.setAddtime(nowdate);
        fserver_bll.UserBindFServerByPublicServer(handler, t_hServerAndUser);
    }

    /*********************************
     * 在本地保存家庭服务器基本信息
     */
    private void UserBindFServerToLocalDB() {
        DB_HServerAndUser one = new DB_HServerAndUser();
        one.setHserver_sn(SN);
        one.setHserver_nickName(NickName);
        one.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
        one.setRemark("");
        one.setIsdefault(0);
        DB_HServerAndUser_BLL.Add(one);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SocketClient socketClient=new SocketClient(EServerType.HomeServer,IP, Const.HomeServerSOCKET_PORT);
                SocketManager.initializeHomeServer(SN,socketClient);
//                //向家庭服务器发送自身sessioninfo
//                Log.d(Tag,"向家庭服务器发送自身sessioninfo");
                session_bll.SendSessionByHomeServer(SN,handler);
            }
        }).start();
    }

    /**
     * 本地保存家庭服务器信息
     *
     * @param t_homeServer
     */
    private void SaveFServerToLoaclDB(T_HomeServer t_homeServer) {
        DB_HomeServer one = new DB_HomeServer();
        one.setHserver_sn(t_homeServer.getHserver_sn());
        one.setHserver_ip(t_homeServer.getHserver_ip());
        one.setHserver_nickName(t_homeServer.getHserver_nickName());
        one.setRemark(t_homeServer.getRemark());
        DB_HomeServer tmpHomeServer = DB_HServer_BLL.GetOne(t_homeServer.getHserver_sn());
        if (tmpHomeServer == null) {
            DB_HServer_BLL.Add(one);
        } else {
            if (tmpHomeServer.getHserver_ip().equals(t_homeServer.getHserver_ip()) == false) {
                tmpHomeServer.setHserver_ip(t_homeServer.getHserver_ip());
                DB_HServer_BLL.Update(tmpHomeServer);
            }
        }
    }

    /*****************
     * 判断家庭服务器是否需要设置秘钥
     *****************/
    private void CheckNeedSetKey() {
        //弹出提示框 设置秘钥
        dialog = DialogTool.createConfirmDialog(hserver_search_Activity.this, "提示", "该服务器未设置秘钥，现在就去设置", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //打开设置秘钥页面
                        Intent intent = new Intent(hserver_search_Activity.this, hserver_search_Activity.class);
                        intent.putExtra("SN", SN);
                        startActivityForResult(intent, 0);

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, DialogTool.NO_ICON);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i("test", result);
        Intent intent = new Intent(hserver_search_Activity.this, device_main_Activity.class);
        startActivity(intent);
    }

    /***
     * 返回到设备管理主页面
     */
    private void BackToDeviceMainPage() {
        Intent intent = new Intent(this, device_main_Activity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        if (commandStruct == null) {
            Log.d("test", "commdhelper.UnPackageCommand is null");
            return;
        }
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        if (type.equals(EServerType.PublicServer)) {
            switch (commandStruct.getCommandCode()) {
                case MyCommandCode.UserBindHServer:
                    //再将string转成对象
                    ResultInfo<View_HServerAndUser> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<View_HServerAndUser>>() {
                    }.getType());
                    boolean resultInfoState = resultInfo.getState();
                    if (resultInfoState) {
                        Message tempMsg = handler.obtainMessage();
                        tempMsg.what = 2;
                        tempMsg.obj = "绑定成功！";
                        handler.sendMessage(tempMsg);
                    } else {
                        Message tempMsg = handler.obtainMessage();
                        tempMsg.what = 3;
                        tempMsg.obj = resultInfo.getMsg();
                        handler.sendMessage(tempMsg);
                    }
                    break;
                case MyCommandCode.GetHServerKeyList:
                    //再将string转成对象
                    ResultInfo<T_HServerKey> resultInfo_key = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_HServerKey>>() {
                    }.getType());
                    boolean resultInfoState_key = resultInfo_key.getState();
                    if (resultInfoState_key) {
                        Message tempMsg = handler.obtainMessage();
                        tempMsg.what = 5;
                        tempMsg.obj = resultInfo_key.getData();
                        handler.sendMessage(tempMsg);
                    } else {
                        Message tempMsg = handler.obtainMessage();
                        tempMsg.what = 3;
                        tempMsg.obj = "操作失败！请稍后再试！";
                        handler.sendMessage(tempMsg);
                    }
                    break;
            }
        } else if (type.equals(EServerType.HomeServer)) {
            Log.d(Tag, "接收来自家庭服务器的数据");
            if (commandStruct.getCommandCode() == MyCommandCode.GetOneHServerInfo) {
                T_HomeServer t_homeServer = gson.fromJson(dataStr, new TypeToken<T_HomeServer>() {
                }.getType());
                Log.d(Tag, "停止查找");
                if(toConnect==true)
                {
                Message tempMsg = handler.obtainMessage();
                tempMsg.what = 6;
                tempMsg.obj = t_homeServer;
                handler.sendMessage(tempMsg);
                }
            }
        }
    }

    //雷达扫描动画刷新线程类
    private class RadarSweep implements Runnable {
        int i = 1;
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && i == 1) {
                try {
                    radarView.postInvalidate();//刷新radarView，执行onDraw()
                    Thread.sleep(10);//暂停当前线程，更新UI线程
                } catch (InterruptedException e) {
                    i = 0;//结束当前扫描线程标志符
                    break;
                }
            }
        }
    }
}
