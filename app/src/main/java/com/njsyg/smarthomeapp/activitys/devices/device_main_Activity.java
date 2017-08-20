package com.njsyg.smarthomeapp.activitys.devices;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.MainActivity;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.devices.device.sdevice_info_Activity;
import com.njsyg.smarthomeapp.activitys.devices.homeserver.hserver_info_Activity;
import com.njsyg.smarthomeapp.activitys.devices.homeserver.hserver_setkey_Activity;
import com.njsyg.smarthomeapp.adapters.IndexPage.OnTapListener;
import com.njsyg.smarthomeapp.adapters.SDeviceM.HomeServerArrayListItem;
import com.njsyg.smarthomeapp.adapters.SDeviceM.HomeServerRecyclerViewAdapter;
import com.njsyg.smarthomeapp.adapters.SDeviceM.SmartDeviceArrayListItem;
import com.njsyg.smarthomeapp.adapters.SDeviceM.SmartDeviceRecycleViewAdapter;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_Device_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServerAndUser_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServer_BLL;
import com.njsyg.smarthomeapp.bll.fserver_bll;
import com.njsyg.smarthomeapp.bll.sdevice_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_Device;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerAndUser;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HomeServer;
import com.njsyg.smarthomeapp.bll.socket_bll.models.View_HServerAndUser;
import com.njsyg.smarthomeapp.common.PublicInfo;
import com.njsyg.smarthomeapp.common.utils.DialogTool;

import java.util.ArrayList;
import java.util.List;

import njsyg.greendao.db.model.DB_Device;
import njsyg.greendao.db.model.DB_HServerAndUser;
import njsyg.greendao.db.model.DB_HomeServer;

public class device_main_Activity extends AppCompatActivity implements View.OnClickListener,HomeServerRecyclerViewAdapter.DataControlDelegate,SmartDeviceRecycleViewAdapter.DataControlDelegate, SocketClient.ReadDataDelegate{
    private ArrayList<HomeServerArrayListItem> serverDeviceArrayListItemArrayList = new ArrayList<HomeServerArrayListItem>();
    private ArrayList<SmartDeviceArrayListItem> smartDeviceArrayListItemArrayList = new ArrayList<SmartDeviceArrayListItem>();
    private Context context;
    private ImageView im_home_server_header_add,im_sdevice_header_add,imgage_back;
    ExpandableLayout el_view_homeserver,el_view_smartdevice;
    HomeServerRecyclerViewAdapter hserverRecycleViewAdapter;
    SmartDeviceRecycleViewAdapter smartDeviceRecycleViewAdapter;
    ArrayMap<String, DB_HServerAndUser> arrayMap_DB_HServerAndUser = new ArrayMap<>();
    ArrayMap<String, DB_Device> arrayMap_DB_Device = new ArrayMap<>();
    Dialog dialog;
    Handler handler = null;
    String getSelectedSDeviceSN;
    private DB_HServerAndUser selectedHServerAndUser = new DB_HServerAndUser();
    RecyclerView hserverRecyclerView,smartDeviceRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_admin);
        InitView();
        SocketManager.setReadPublicServerDataDelegate(this);
        InitData();
        InitHandler();
    }
    /*
    *初始化控件
    */
    private void InitView()
    {
        /**初始化控件*/
        el_view_homeserver=(ExpandableLayout)findViewById(R.id.el_view_homeserver);
        el_view_smartdevice=(ExpandableLayout)findViewById(R.id.el_view_smartdevice);
        el_view_homeserver.show();
        hserverRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_home_server);
        im_home_server_header_add=(ImageView)findViewById(R.id.im_home_server_header_add);
        im_sdevice_header_add=(ImageView)findViewById(R.id.im_sdevice_header_add);
        imgage_back=(ImageView)findViewById(R.id.imgage_back);
        im_home_server_header_add.setOnClickListener(this);
        im_sdevice_header_add.setOnClickListener(this);
        imgage_back.setOnClickListener(this);
        //创建RecycleView对象
        hserverRecycleViewAdapter = new HomeServerRecyclerViewAdapter(serverDeviceArrayListItemArrayList);
        //设置RecycleView
        hserverRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        hserverRecyclerView.setLayoutManager(layoutManager);

        hserverRecyclerView.setAdapter(hserverRecycleViewAdapter);
        smartDeviceRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_smart_device);
        //设置RecycleView
        smartDeviceRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerr = new LinearLayoutManager(context);

        smartDeviceRecyclerView.setLayoutManager(layoutManagerr);
        //创建RecycleView对象
        smartDeviceRecycleViewAdapter = new SmartDeviceRecycleViewAdapter(smartDeviceArrayListItemArrayList);
        smartDeviceRecyclerView.setAdapter(smartDeviceRecycleViewAdapter);

        //注册
        hserverRecycleViewAdapter.setDataControlDelegate(this);
        smartDeviceRecycleViewAdapter.setDataControlDelegate(this);
    }

    /**
     * 初始化数据
     */
    private void InitData()
    {
        ShowHServerByLocalDB();
    }

    /**
     * 从本地数据库读取家庭服务器信息
     */
    private void ShowHServerByLocalDB()
    {
        serverDeviceArrayListItemArrayList.clear();
        smartDeviceArrayListItemArrayList.clear();
        //从本地数据库读取家庭服务器信息
        List<DB_HServerAndUser> dbHServerAndUserList= DB_HServerAndUser_BLL.GetListByPhoneNum(PublicInfo.currentLoginUser.getUser_phoneNumber());
        String defautHServerSN="";
        if(dbHServerAndUserList!=null) {
            HomeServerArrayListItem homeServerArrayListItem;
            for (DB_HServerAndUser item : dbHServerAndUserList) {
                arrayMap_DB_HServerAndUser.put(item.getHserver_sn(), item);
                homeServerArrayListItem = new HomeServerArrayListItem();
                homeServerArrayListItem.setHserverIp(item.getHserver_ip());
                homeServerArrayListItem.setHserverName(item.getHserver_nickName());
                homeServerArrayListItem.setHserverSN(item.getHserver_sn());
                homeServerArrayListItem.setIntIsDefault(item.getIsdefault());
                if (item.getIsdefault() == 1) {
                    defautHServerSN = item.getHserver_sn();
                    homeServerArrayListItem.setHserverIsDefault(getResources().getDrawable(R.drawable.icon_isdefault));
                } else {
                    homeServerArrayListItem.setHserverIsDefault(getResources().getDrawable(R.drawable.icon_notdefault));
                }
                serverDeviceArrayListItemArrayList.add(homeServerArrayListItem);
            }
        }
        hserverRecycleViewAdapter.notifyDataSetChanged();
        String hserverSn="";
        if(defautHServerSN.equals("")==false) {
            hserverSn = defautHServerSN;
        }else {
            if (dbHServerAndUserList != null && dbHServerAndUserList.size() > 0) {
                hserverSn = dbHServerAndUserList.get(0).getHserver_sn();
            }
        }
        if(hserverSn.equals(""))
        {
            return;
        }
        ShowSDeviceByLocalDB(hserverSn);
    }

    /**
     * 从本地数据库中读取智能设备信息
     * @param hserverSN
     */
    private void ShowSDeviceByLocalDB(String hserverSN)
    {
        smartDeviceArrayListItemArrayList.clear();
        //从本地数据库读取智能设备信息
        List<DB_Device> dbDeviceList = DB_Device_BLL.GetList(hserverSN);
        if(dbDeviceList!=null)
        {
            SmartDeviceArrayListItem smartDeviceArrayListItem;
            for (DB_Device item:dbDeviceList)
            {
                arrayMap_DB_Device.put(item.getDevice_sn(),item);
                smartDeviceArrayListItem=new SmartDeviceArrayListItem();
                smartDeviceArrayListItem.setImgSmartDevice(getResources().getDrawable(R.drawable.device));
                smartDeviceArrayListItem.setsDeviceSN(item.getDevice_sn());
                smartDeviceArrayListItem.setSmartName(item.getDevice_nickName()+"/"+item.getDevice_installPlace());
                smartDeviceArrayListItemArrayList.add(smartDeviceArrayListItem);
            }
        }
        smartDeviceRecycleViewAdapter.notifyDataSetChanged();
    }
    /*
   *初始化Handler
   */
    private void InitHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d("test", "发送socket失败");
                        break;
                    case 1:
                        Log.d("test", "发送socket成功");
                        break;
                    case 2:
                        Log.d("test", "接收到socket服务端消息");
                        break;
                }
            }
        };
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.im_home_server_header_add:
                //显示添加家庭服务器
                startActivityForResult(new Intent(this, select_addmode_Activity.class), 2);
                break;
            case R.id.imgage_back:
                //返回主页面
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.im_sdevice_header_add:
                //添加设备
                Intent intent_addsdevice = new Intent(this, device_type_Activity.class);
                Log.d("test","fserverSN:"+selectedHServerAndUser.getHserver_sn());
                intent_addsdevice.putExtra("fserverSN", selectedHServerAndUser.getHserver_sn());
                startActivityForResult(intent_addsdevice, 3);
                break;
        }
    }

  //////////////////////家庭服务器/////////////////////////////
    @Override
    public void setDefautHServer(final String hserverSN,final int isDefault) {
        if (isDefault == 0) {
            dialog = DialogTool.createConfirmDialog(this, "标题", "确定要设置为默认家庭服务器？", "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("test", "fserversn:" + hserverSN);
                            //向公网服务器发送删除用户与家庭服务器的绑定关系
                            T_HServerAndUser t_hServerAndUser = new T_HServerAndUser();
                            t_hServerAndUser.setHserver_sn(hserverSN);
                            t_hServerAndUser.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
                            fserver_bll.SetHServerDefaultByPublicServer(handler, t_hServerAndUser);

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, DialogTool.NO_ICON);
            dialog.show();
        }
    }

    @Override
    public void deleteHServer(final String hserverSN) {
        dialog = DialogTool.createConfirmDialog(this, "标题", "确定要删除吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("test", "fserversn:" + hserverSN);
                        //向公网服务器发送删除用户与家庭服务器的绑定关系
                        T_HServerAndUser t_hServerAndUser = new T_HServerAndUser();
                        t_hServerAndUser.setHserver_sn(hserverSN);
                        selectedHServerAndUser = arrayMap_DB_HServerAndUser.get(hserverSN);
                        t_hServerAndUser.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
                        T_HomeServer t_homeServer=new T_HomeServer();
                        t_homeServer.setFromPhoneNum(PublicInfo.currentLoginUser.getUser_phoneNumber());
                        byte[] datas= fserver_bll.DisConnectHomeServerByte(t_homeServer);
                        SocketManager.SendHomeServerMsg(hserverSN,datas,null);
                        fserver_bll.DeleteUserBindHServerByPublicServer(handler, t_hServerAndUser);

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, DialogTool.NO_ICON);
        dialog.show();
    }

    @Override
    public void editHServer(String hserverSN) {
        //打开家庭服务器详情页面
        DB_HServerAndUser one = arrayMap_DB_HServerAndUser.get(hserverSN);
        Intent intent = new Intent();
        intent.setClass(this, hserver_info_Activity.class);
        intent.putExtra("DB_HServerAndUser", one);
        startActivityForResult(intent, 3);
    }

    @Override
    public void clickItem(String hserverSN,int position) {
        hserverRecycleViewAdapter.setSelectItem(position);
        selectedHServerAndUser = arrayMap_DB_HServerAndUser.get(hserverSN);
        ShowSDeviceByLocalDB(hserverSN);
    }

    /////////////////////智能设备//////////////////////////////
    @Override
    public void showOther(String sdeviceSN) {

    }
    @Override
    public void editSDevice(String sdeviceSN) {
        //打开家庭服务器详情页面
        DB_Device one = arrayMap_DB_Device.get(sdeviceSN);
        Intent intent = new Intent();
        intent.setClass(this, sdevice_info_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DB_Device", one);
        intent.putExtras(bundle);
        startActivityForResult(intent, 4);
    }
    @Override
    public void deleteSDevice(final String sdeviceSN) {
        dialog = DialogTool.createConfirmDialog(this, "提示", "确定要删除吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //向公网服务器发送删除设备
                        T_Device t_device = new T_Device();
                        t_device.setDevice_sn(sdeviceSN);
                        getSelectedSDeviceSN = sdeviceSN;
                        sdevice_bll.DeleteDeviceByPublicServer(handler, t_device);
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
        ShowHServerByLocalDB();
        SocketManager.setReadPublicServerDataDelegate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SocketManager.setReadPublicServerDataDelegate(null);//取消委托对象
    }
    //////////////////////////////////////////////////////////////////
    ////////////////////////处理服务端返回的信息/////////////////////////
    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {
        Log.d("ReadFServerData", "获取家庭服务器列表返回数据" + datas.length);
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        if (commandStruct == null) {
            Log.d("test", "commdhelper.UnPackageCommand is null");
            return;
        }
        Log.d("ReadFServerData", "获取家庭服务器列表返回数据commandStruct.getCommandCode():" + commandStruct.getCommandCode());
        switch (commandStruct.getCommandCode()) {
            case MyCommandCode.DeleteUserBindHServer:
                DealDeleteFServerUserCommand(commandStruct);
                break;
            case MyCommandCode.DeleteDevice:
                DealDeleteDeviceByPublicServer(commandStruct);
                break;
            case MyCommandCode.SetHServerDefault:
                DealSetHServerDefaultByPublicServer(commandStruct);
                break;
        }
    }
    //处理公网服务器发来的删除用户与家庭服务器间绑定关系的命令
    public void DealDeleteFServerUserCommand(CommandStruct commandStruct) {
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        //再将string转成对象
        ResultInfo<View_HServerAndUser> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<View_HServerAndUser>>() {
        }.getType());
        boolean resultInfoState = resultInfo.getState();
        if (resultInfoState) {
            View_HServerAndUser u = resultInfo.getData();
            for (HomeServerArrayListItem x : serverDeviceArrayListItemArrayList) {
                if (x.getHserverSN() == selectedHServerAndUser.getHserver_sn()) {
                    serverDeviceArrayListItemArrayList.remove(x);
                    break;
                }
            }
            //本地删除
            DB_HServerAndUser_BLL.Delete(selectedHServerAndUser.getId());
            //停止家庭服务器socket连接
            SocketManager.stopHomeServer(getSelectedSDeviceSN);
            hserverRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    hserverRecycleViewAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Log.d("test", "解除与家庭服务器关系失败！请稍后再试！");
        }
    }

    /**
     * 服务端返回删除智能设备*
     */
    private void DealDeleteDeviceByPublicServer(CommandStruct commandStruct) {
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        //再将string转成对象
        ResultInfo<T_Device> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_Device>>() {
        }.getType());
        boolean resultInfoState = resultInfo.getState();
        if (resultInfoState) {
            for (SmartDeviceArrayListItem x : smartDeviceArrayListItemArrayList) {
                if (x.getsDeviceSN() == getSelectedSDeviceSN) {
                    smartDeviceArrayListItemArrayList.remove(x);
                    break;
                }
            }
            if (!getSelectedSDeviceSN.equals("")) {
                DB_Device_BLL.Delete(arrayMap_DB_Device.get(getSelectedSDeviceSN).getDevice_id());
                //通知家庭服务器 解除与智能设备的绑定
                T_Device t_device=new T_Device();
                t_device.setHserver_sn(selectedHServerAndUser.getHserver_sn());
                t_device.setDevice_sn(getSelectedSDeviceSN);
                sdevice_bll.DeleteDeviceByHomeServer(handler,t_device);
            }
            getSelectedSDeviceSN = "";
           smartDeviceRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                   smartDeviceRecycleViewAdapter.notifyDataSetChanged();
                }
            });
        } else {
            //Toast.makeText(this, "解除与家庭服务器关系失败！请稍后再试！", Toast.LENGTH_SHORT).show();
            Log.d("test", "删除智能设备失败！请稍后再试！");
        }
    }

    private void DealSetHServerDefaultByPublicServer(CommandStruct commandStruct)
    {
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        //再将string转成对象
        ResultInfo<T_HServerAndUser> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_HServerAndUser>>() {
        }.getType());
        boolean resultInfoState = resultInfo.getState();
        if (resultInfoState) {
            T_HServerAndUser u = resultInfo.getData();
            for (HomeServerArrayListItem x : serverDeviceArrayListItemArrayList) {
                if (x.getHserverSN().equals(u.getHserver_sn())) {
                    x.setIntIsDefault(1);
                    x.setHserverIsDefault(getResources().getDrawable(R.drawable.icon_isdefault));
                    arrayMap_DB_HServerAndUser.get(x.getHserverSN()).setIsdefault(1);
                }else
                {
                    x.setIntIsDefault(0);
                    x.setHserverIsDefault(getResources().getDrawable(R.drawable.icon_notdefault));
                    arrayMap_DB_HServerAndUser.get(x.getHserverSN()).setIsdefault(0);
                }
            }
            //本地修改默认家庭服务器
            //DB_HServerAndUser_BLL.Delete(selectedHServerAndUser.getId());
            hserverRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    hserverRecycleViewAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
