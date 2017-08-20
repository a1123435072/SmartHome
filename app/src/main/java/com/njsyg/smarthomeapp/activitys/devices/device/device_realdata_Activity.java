package com.njsyg.smarthomeapp.activitys.devices.device;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.datacenter.hisdata_chart;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_DeviceHisData;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_DeviceRealData;
import com.njsyg.smarthomeapp.common.utils.DateUtils;
import com.njsyg.smarthomeapp.entity.M_DeviceElectricityData;
import com.njsyg.smarthomeapp.entity.M_DeviceSwitchStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class device_realdata_Activity extends AppCompatActivity implements View.OnClickListener, SocketClient.ReadDataDelegate {

    private LineChart lineChart;
    TextView textViewTitle, textViewU, textViewI, textViewE;
    ImageView imageViewOnlineState, imageViewSwitchState;
    CheckBox checkBoxU, checkBoxI, checkBoxE;
    Button buttonHis;
    ImageView imageViewBack;
    Handler handler;
    final String TAG = "realdata_Activity";
    String deviceSN = "0001";
    String homeServerSN = "0000000000000107";
    ArrayList<String> xValues = new ArrayList<String>();
    ArrayList<Entry> yUValues = new ArrayList<Entry>();
    ArrayList<Entry> yIValues = new ArrayList<Entry>();
    ArrayList<Entry> yEValues = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_realdata);
        InitView();
        InitHandler();
        InitData();
    }

    /*
    *初始化控件
    */
    private void InitView() {
        textViewU = (TextView) findViewById(R.id.txt_U);
        textViewI = (TextView) findViewById(R.id.txt_I);
        textViewE = (TextView) findViewById(R.id.txt_E);
        buttonHis = (Button) findViewById(R.id.btn_his);
        imageViewOnlineState = (ImageView) findViewById(R.id.image_onlinestate);
        imageViewSwitchState = (ImageView) findViewById(R.id.image_switchstate);
        imageViewBack = (ImageView) findViewById(R.id.imgage_back);
        textViewTitle = (TextView) findViewById(R.id.title);
        lineChart = (LineChart) findViewById(R.id.chart_real);
        InitChart();
        checkBoxU = (CheckBox) findViewById(R.id.checkbox_U);
        checkBoxI = (CheckBox) findViewById(R.id.checkbox_I);
        checkBoxE = (CheckBox) findViewById(R.id.checkbox_E);
        imageViewBack.setOnClickListener(this);
        buttonHis.setOnClickListener(this);


    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
        homeServerSN = intent.getStringExtra("homeServerSN");
        deviceSN = intent.getStringExtra("sdeviceSN");
        SocketManager.setReadHomeServerDataDelegate(homeServerSN, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (homeServerSN.equals("") == false) {
            SocketManager.setReadHomeServerDataDelegate(homeServerSN, null);//取消委托对象
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgage_back:
                //返回
                Back();
                break;
            case R.id.btn_his:
                //打开历史页面
                Intent his=new Intent(this, hisdata_chart.class);
                his.putExtra("homeServerSN",homeServerSN);
                his.putExtra("sdeviceSN",deviceSN);
                startActivity(his);
                break;
        }
    }

    /*
    *初始化Handler
    */
    private void InitHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0://显示开关状态
                        ShowDeviceSwitchState((M_DeviceSwitchStatus) msg.obj);
                        break;
                    case 1://显示用电信息
                        ShowDeviceElectricityInfo((M_DeviceElectricityData) msg.obj);
                        break;
                }
            }
        };
    }

    /**
     * 显示开关状态
     *
     * @param m_deviceStatus
     */
    private void ShowDeviceSwitchState(M_DeviceSwitchStatus m_deviceStatus) {
        int SwitchStatus = m_deviceStatus.getDeviceSwitchStatus();
        if (SwitchStatus == 0) {
            imageViewSwitchState.setImageDrawable(getResources().getDrawable(R.drawable.close));
        } else {
            imageViewSwitchState.setImageDrawable(getResources().getDrawable(R.drawable.open));
        }
    }

    /**
     * 显示用电数据
     *
     * @param mDeviceDatas
     */
    private void ShowDeviceElectricityInfo(M_DeviceElectricityData mDeviceDatas) {
        float deviceU = mDeviceDatas.getDeviceU();
        float deviceI = mDeviceDatas.getDeviceI();
        float deviceE = mDeviceDatas.getDeviceElectricity();
        textViewU.setText(deviceU + "");
        textViewI.setText(deviceI + "");
        textViewE.setText(deviceE + "");
        ShowDeviceHisData(mDeviceDatas);
    }

    /// <summary>
    ///返回
    /// </summary>
    private void Back() {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Back();
    }

    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {
        Log.d(TAG, "接收到" + type + "返回的数据");
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        if (commandStruct == null) {
            Log.d(TAG, "commdhelper.UnPackageCommand is null");
            return;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d(TAG, dataStr);
        Log.d(TAG, "commandStruct.getCommandCode()" + commandStruct.getCommandCode());
        switch (commandStruct.getCommandCode()) {
            case MyCommandCode.ReadDeviceSwitchStatus://智能设备开关状态
                M_DeviceSwitchStatus m_deviceStatus = gson.fromJson(dataStr, new TypeToken<M_DeviceSwitchStatus>() {
                }.getType());
                if (m_deviceStatus != null && m_deviceStatus.getDeviceSN().equals(deviceSN)) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj = m_deviceStatus;
                    handler.sendMessage(message);
                }
                break;
            case MyCommandCode.ReadDeviceElectricityInfo://智能设备用电数据
                M_DeviceElectricityData mDeviceDatas = gson.fromJson(dataStr, new TypeToken<M_DeviceElectricityData>() {
                }.getType());
                if (mDeviceDatas != null && mDeviceDatas.getDeviceSN().equals(deviceSN)) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = mDeviceDatas;
                    handler.sendMessage(message);
                }
                break;
        }
    }

    LineData lineData;
    private void InitChart() {
        lineChart.setDrawBorders(false);  // 是否在折线图上添加边框
        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似 listview 的 emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");
        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//
        lineChart.setBackgroundColor(Color.rgb(114, 188, 223));// 设置背景
        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组 y 的 value 的
        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色
//      mLegend.setTypeface(mTf);// 字体
        lineChart.animateX(2500); // 立即执行的动画, x 轴
        lineDataSet = new LineDataSet(yEValues, "电量" /* 显示在比例图上 */);
        // 用 y 轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(Color.WHITE);// 显示颜色
        lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
        lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet); // add the datasets
        // create a data object with the datasets
        lineData = new LineData(xValues, lineDataSets);
        lineChart.setData(lineData); // 设置数据
    }
    LineDataSet lineDataSet;
    private void ShowDeviceHisData(M_DeviceElectricityData mDeviceDatas) {
        float deviceU = mDeviceDatas.getDeviceU();
        float deviceI = mDeviceDatas.getDeviceI();
        float deviceE = mDeviceDatas.getDeviceElectricity();
        Date updateDate = mDeviceDatas.getUpdateTime();
        LineData data = lineChart.getData();
        // 每一个LineDataSet代表一条线，每张统计图表可以同时存在若干个统计折线，这些折线像数组一样从0开始下标。
        // 本例只有一个，那么就是第0条折线
        LineDataSet set = data.getDataSetByIndex(0);
        data.addXValue(DateUtils.ConvertShortTime(updateDate));
        // 生成随机测试数
        float f = deviceE;
        // set.getEntryCount()获得的是所有统计图表上的数据点总量，
        // 如从0开始一样的数组下标，那么不必多次一举的加1
        Entry entry = new Entry(f, set.getEntryCount());
        // 往linedata里面添加点。注意：addentry的第二个参数即代表折线的下标索引。
        // 因为本例只有一个统计折线，那么就是第一个，其下标为0.
        // 如果同一张统计图表中存在若干条统计折线，那么必须分清是针对哪一条（依据下标索引）统计折线添加。
        data.addEntry(entry, 0);
        lineChart.notifyDataSetChanged();
        lineChart.moveViewToX(data.getXValCount() - 5);
        lineChart.invalidate(); // refresh
    }
}