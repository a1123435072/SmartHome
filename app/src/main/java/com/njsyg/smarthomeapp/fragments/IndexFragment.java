package com.njsyg.smarthomeapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.devices.device.device_realdata_Activity;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_DeviceRealData_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_Device_BLL;
import com.njsyg.smarthomeapp.bll.sdevice_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_ControlDevice;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_DeviceOnlineRecord;
import com.njsyg.smarthomeapp.common.Const;
import com.njsyg.smarthomeapp.common.Converts;
import com.njsyg.smarthomeapp.common.PublicInfo;
import com.njsyg.smarthomeapp.common.utils.RippleView;
import com.njsyg.smarthomeapp.adapters.IndexPage.IndexDeviceArrayListItem;
import com.njsyg.smarthomeapp.adapters.IndexPage.RecyclerViewAdapter;
import com.njsyg.smarthomeapp.entity.M_DeviceElectricityData;
import com.njsyg.smarthomeapp.entity.M_DeviceSwitchStatus;

import java.util.ArrayList;
import java.util.List;

import njsyg.greendao.db.model.DB_Device;
import njsyg.greendao.db.model.DB_DeviceRealData;

import static android.os.Looper.getMainLooper;

/**
 * Created by zz on 2016/10/20.
 */
public class IndexFragment extends Fragment implements RecyclerViewAdapter.DataControlDelegate,SocketClient.ReadDataDelegate {

    private final String TAG="IndexFragment";
    public static Context s_context;
    private Context context;
    RecyclerViewAdapter indexSDeviceListViewAdapter;
    private ArrayList<IndexDeviceArrayListItem> indexDeviceArrayListItemArrayList = new ArrayList<IndexDeviceArrayListItem>();
    RippleView rippleView;
    TextView textView_home_servier;
    RecyclerView recyclerView;
    Handler handler = null;
    private String defaultHomeServerSN="0000000000000107";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regBroadcast();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        Log.i(TAG,"IndexFragment onCreateView");
        InitHandler();
        //初始化控件
        InitView(view);
        //初始化数据
        InitData();

        s_context = getContext();
        return view;
    }

    /**
     * 初始化控件
     */
    private void InitView(View view) {
        rippleView = (RippleView) view.findViewById(R.id.home_server);
        textView_home_servier = (TextView) view.findViewById(R.id.tv_home_server);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        indexDeviceArrayListItemArrayList.clear();
        indexSDeviceListViewAdapter = new RecyclerViewAdapter(indexDeviceArrayListItemArrayList);
        indexSDeviceListViewAdapter.setDataControlDelegate(this);
        recyclerView.setAdapter(indexSDeviceListViewAdapter);
        SocketManager.setReadPublicServerDataDelegate(this);
        SocketManager.setReadHomeServerDataDelegate(defaultHomeServerSN,this);
    }

    /**
     * 初始化数据
     */
    private void InitData() {
        ShowDefaultHomeserverInfo();
        ShowDeviceList(defaultHomeServerSN);
        ShowDeviceRealDataList();
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
                        Log.d(TAG, "发送socket失败");
                        break;
                    case 1:
                        Log.d(TAG, "发送socket成功");
                        break;
                    case 2:
                        T_DeviceOnlineRecord t_deviceOnlineRecord = (T_DeviceOnlineRecord) msg.obj;
                        if (t_deviceOnlineRecord != null) {
                            //SetDeviceOnlineState(t_deviceOnlineRecord);
                        }
                        break;
                    case 5:
                        indexSDeviceListViewAdapter.notifyDataSetChanged();
                        break;
                    case 6:
//                        boolean state = (boolean) msg.obj;
//                        String now = DateUtils.GetNowDateTime();
//                        if (state) {
//                            textViewState.setText("在线" + now);
//                        } else {
//                            textViewState.setText("离线" + now);
//                        }
                        break;
                }
            }
        };
    }
    /**
     * 控制开关状态
     * @param sdeviceSN
     * @param currentSwitchState
     */
    @Override
    public void setDeviceSwitchState(String sdeviceSN, int currentSwitchState) {
      Log.i(TAG,"setDeviceSwitchState"+sdeviceSN+"/"+currentSwitchState);
        //控制智能设备
        T_ControlDevice t_controlDevice = new T_ControlDevice();
        t_controlDevice.setHomeServerSN(defaultHomeServerSN);
        t_controlDevice.setDeviceSN(sdeviceSN);
        t_controlDevice.setFromPhoneNum(PublicInfo.currentLoginUser.getUser_phoneNumber());
        switch (currentSwitchState) {
            case 0:
                t_controlDevice.setSetState(1);
                break;
            case 1:
                t_controlDevice.setSetState(0);
                break;
        }
        t_controlDevice.setRemark("");
        //与家庭服务器链接
        if (SocketManager.getHomeServerConnectState(t_controlDevice.getHomeServerSN())) {
            sdevice_bll.ControlSDeviceState(handler, t_controlDevice);
        } else {   //没有连接家庭服务器，则将命令转发给公网服务器
            //transfer_bll.SendTransferByteByPublicServer(t_controlDevice, handler);
        }
    }

    /**
     * 展示实时数据
     * @param sdeviceSN
     */
    @Override
    public void showDeviceRealData(String sdeviceSN) {
        Log.i(TAG,"showDeviceRealData"+sdeviceSN);
        Intent real=new Intent(getActivity(), device_realdata_Activity.class);
        real.putExtra("homeServerSN",defaultHomeServerSN);
        real.putExtra("sdeviceSN",sdeviceSN);
        startActivity(real);
    }

    /**
     * 显示默认的家庭服务器（从本地数据库中读取）
     */
    private void ShowDefaultHomeserverInfo()
    {

    }

    /**
     * 显示家庭服务器下面的智能设备列表（从本地数据库中读取）
     * @param homeserverSN
     */
    private void ShowDeviceList(String homeserverSN)
    {
        List<DB_Device> deviceList= DB_Device_BLL.GetList(homeserverSN);
        if(deviceList!=null)
        {
            IndexDeviceArrayListItem item ;
            for (DB_Device one :deviceList)
            {
                item= new IndexDeviceArrayListItem();
                item.setDeviceSN(one.getDevice_sn());
                item.setInstallPlace(one.getDevice_installPlace());
                item.setNickName(one.getDevice_nickName());
                indexDeviceArrayListItemArrayList.add(item);
            }
            Message message = new Message();
            message.what = 5;
            handler.sendMessage(message);
        }
    }

    /**
     * 显示智能设备实时数据（从本地数据库中读取）
     */
    private void ShowDeviceRealDataList()
    {
        for (int i=0;i< indexDeviceArrayListItemArrayList.size();i++)
        {
            IndexDeviceArrayListItem item=indexDeviceArrayListItemArrayList.get(i) ;
            String deviceSN=item.getDeviceSN();
            DB_DeviceRealData dbDeviceRealData= DB_DeviceRealData_BLL.GetOne(deviceSN);
            item.setIntOnlineState(0);
            if(dbDeviceRealData!=null)
            {
                item.setIntSwitchState(dbDeviceRealData.getDevice_state());
                item.setDianYa(dbDeviceRealData.getDevice_U()+"");
                item.setDianLiu(dbDeviceRealData.getDevice_I()+"");
                item.setBattery(dbDeviceRealData.getDevice_electricity()+"");
            }else
            {
                item.setIntSwitchState(0);
                item.setDianYa("0");
                item.setDianLiu("0");
                item.setBattery("0");
            }
            if (item.getIntOnlineState() == 1) {
                item.setOnlineState(getResources().getDrawable(R.drawable.online));
            } else {
                item.setOnlineState(getResources().getDrawable(R.drawable.offline));
            }
            if (item.getIntSwitchState() == 0) {
                item.setSwitchState(getResources().getDrawable(R.drawable.close));
            } else {
                item.setSwitchState(getResources().getDrawable(R.drawable.open));
            }
        }
        Message message = new Message();
        message.what = 5;
        handler.sendMessage(message);
    }

    /**
     * 显示智能设备实时开关状态
     * @param m_deviceStatus
     */
    private void ShowDeviceRealSwitchStatus(M_DeviceSwitchStatus m_deviceStatus )
    {
        for (int i=0;i< indexDeviceArrayListItemArrayList.size();i++)
        {
            IndexDeviceArrayListItem item=indexDeviceArrayListItemArrayList.get(i) ;
            if(item.getDeviceSN().equals(m_deviceStatus.getDeviceSN()))
            {
                item.setIntSwitchState(m_deviceStatus.getDeviceSwitchStatus());
                if (item.getIntSwitchState() == 0) {
                    item.setSwitchState(getResources().getDrawable(R.drawable.close));
                } else {
                    item.setSwitchState(getResources().getDrawable(R.drawable.open));
                }
                item.setOnlineState(getResources().getDrawable(R.drawable.online));
                break;
            }
        }
        Message message = new Message();
        message.what = 5;
        handler.sendMessage(message);
    }

    /**
     * 显示智能设备实时用电数据
     * @param mDeviceElectricityData
     */
    private void ShowDeviceRealElectricityData(M_DeviceElectricityData mDeviceElectricityData )
    {
        for (int i=0;i< indexDeviceArrayListItemArrayList.size();i++)
        {
            IndexDeviceArrayListItem item=indexDeviceArrayListItemArrayList.get(i) ;
            if(item.getDeviceSN().equals(mDeviceElectricityData.getDeviceSN()))
            {
                item.setDianYa(mDeviceElectricityData.getDeviceU()+"");
                item.setDianLiu(mDeviceElectricityData.getDeviceI()+"");
                item.setBattery(mDeviceElectricityData.getDeviceElectricity()+"");
                item.setOnlineState(getResources().getDrawable(R.drawable.online));
                break;
            }
        }
        Message message = new Message();
        message.what = 5;
        handler.sendMessage(message);
    }


    /**
     * 接收到socket服务端返回的数据
     * @param type
     * @param datas
     */
    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {
        Log.d("IndexFragment", "接收到" + type + "返回的数据");
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        if (commandStruct == null) {
            Log.d("test", "commdhelper.UnPackageCommand is null");
            return;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        Log.d("test", "commandStruct.getCommandCode()" + commandStruct.getCommandCode());
        if (type.equals(EServerType.PublicServer)) {
            switch (commandStruct.getCommandCode()) {
                case MyCommandCode.DeviceRealonlineRecord://接收到公网服务器推送的智能设备在线状态
                    T_DeviceOnlineRecord t_deviceOnlineRecord = gson.fromJson(dataStr, new TypeToken<T_DeviceOnlineRecord>() {
                    }.getType());
                    if (t_deviceOnlineRecord != null) {
                        if (SocketManager.getHomeServerConnectState(defaultHomeServerSN) == false) {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = t_deviceOnlineRecord;
                            handler.sendMessage(message);
                        }
                    }
                    break;
            }
        } else {//家庭服务器
            switch (commandStruct.getCommandCode()) {
                case MyCommandCode.ReadDeviceSwitchStatus://智能设备开关状态
                    M_DeviceSwitchStatus mdeviceStatus = gson.fromJson(dataStr, new TypeToken<M_DeviceSwitchStatus>() {
                    }.getType());
                    if (mdeviceStatus != null) {
                        ShowDeviceRealSwitchStatus(mdeviceStatus);
                        //本地数据保存
                        SaveDataToLoaclDB(mdeviceStatus);
                    }
                    break;
                case MyCommandCode.ReadDeviceElectricityInfo://智能设备用电数据
                    M_DeviceElectricityData mDeviceDatas = gson.fromJson(dataStr, new TypeToken<M_DeviceElectricityData>() {
                    }.getType());
                    if (mDeviceDatas != null) {
                        ShowDeviceRealElectricityData(mDeviceDatas);
                        //本地数据保存
                        SaveDataToLoaclDB(mDeviceDatas);
                    }
                    break;

            }
        }
    }

    /**
     * 将智能设备实时开关状态保存到本地数据库
     * @param m_deviceStatus
     */
    private void SaveDataToLoaclDB( M_DeviceSwitchStatus m_deviceStatus) {
        DB_DeviceRealData one = DB_DeviceRealData_BLL.GetOne(m_deviceStatus.getDeviceSN());
        if (one == null) {
            one = new DB_DeviceRealData();
            one.setId(null);
            one.setDevice_sn(m_deviceStatus.getDeviceSN());
        }
        one.setDevice_state(m_deviceStatus.getDeviceSwitchStatus());
        one.setUpdatetime(m_deviceStatus.getUpdateTime());
        DB_DeviceRealData_BLL.AddOrUpdate(one);
    }

    /**
     * 将智能设备实时用电数据保存到本地数据库
     * @param m_deviceElectricityData
     */
    private void SaveDataToLoaclDB( M_DeviceElectricityData m_deviceElectricityData) {
        DB_DeviceRealData one = DB_DeviceRealData_BLL.GetOne(m_deviceElectricityData.getDeviceSN());
        if (one == null) {
            one = new DB_DeviceRealData();
            one.setId(null);
            one.setDevice_sn(m_deviceElectricityData.getDeviceSN());
        }
        one.setDevice_U(m_deviceElectricityData.getDeviceU());
        one.setDevice_I(m_deviceElectricityData.getDeviceI());
        one.setDevice_P(m_deviceElectricityData.getDeviceP());
        one.setDevice_electricity(m_deviceElectricityData.getDeviceElectricity());
        one.setUpdatetime(m_deviceElectricityData.getUpdateTime());
        DB_DeviceRealData_BLL.AddOrUpdate(one);
    }

    /// <summary>
    ///注册广播
    /// </summary>
    BroadcastReceiver bcReceiver;
    public void regBroadcast()
    {
        bcReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                EServerType eServerType=(EServerType)intent.getSerializableExtra("eServerType");
                byte[] data = intent.getByteArrayExtra("data");
                Log.d(TAG,"接收"+eServerType+"到广播"+ Converts.byte2HexStr(data));
                ReadSocketData(eServerType,data);
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction(Const.BC);
        getActivity().registerReceiver(bcReceiver, intentToReceiveFilter);
    }
}
