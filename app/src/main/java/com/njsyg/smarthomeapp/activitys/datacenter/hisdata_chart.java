package com.njsyg.smarthomeapp.activitys.datacenter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.common.Const;
import com.njsyg.smarthomeapp.common.utils.DateUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

//历史数据表格
public class hisdata_chart extends AppCompatActivity {
    private LinearLayout llStartTime, llEndTime;
    private TextView tvStartTime, tvEndTime;
    private String startDate, endDate;
    private CheckBox cbxU, cbxI, cbEl;
    private ImageView showChart, hisBack;
    private ArrayList<DeviceData> deviceDatas;
    private static final String tag = "History_data";
    private String year_start, month_start, day_start, //日历选择器选择的起始时间
            year_end, month_end, day_end,//日历选择器选择的终止时间
            year_current, month_current, day_current,//初始化界面时 请求url的 endtime（默认今天）
            year_yes, month_yes, day_yes;//初始化界面时 请求url的 starttime（默认昨天）
    private String[] startTime, endTime;

    private int[] mColors = new int[]{Color.RED, Color.GREEN, Color.BLUE};

    private LineChart mLineChart;
    private LineDataSet dataSetU, dataSetI, dataSetE;//
    ArrayList<String> xLabel = new ArrayList<>();//x轴数据

    private ArrayList<Entry> yValsE = new ArrayList<>();
    private ArrayList<Entry> yValsU = new ArrayList<>();
    private ArrayList<Entry> yValsI = new ArrayList<>();
    ;

    ArrayList listI = new ArrayList();
    ArrayList listU = new ArrayList();
    ArrayList listElec = new ArrayList();
    private String homeServerSN;
    private String sdeviceSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisdata_chart);
        initView();
        InitData();
        initSelectDate();
        setLineChart(mLineChart);
        selectDate();
    }

    //初始化日期选择器的日期
    private void initSelectDate() {
        initStartDate();
        initEndDate();
    }

    //初始化的 startdate 为昨天
    private void initStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String s = DateUtils.ConvertDate(yesterday);
        Log.d(tag, s);//2016-10-31 08:26:37
        Log.d(tag, s.length() + "");//19
        String substring = s.substring(0, 10);
        String[] dateYes = substring.split("-");
        year_yes = dateYes[0];
        month_yes = dateYes[1];
        day_yes = dateYes[2];
        tvStartTime.setText(year_yes + " - " + month_yes + " - " + day_yes);
    }

    private void initEndDate() {
        String s = DateUtils.GetNowDate();
        Log.d(tag, s);//2016-11-01 08:09:16
        Log.d(tag, s.length() + "");//19
        String substring = s.substring(0, 10);
        String[] dateNow = substring.split("-");
        year_current = dateNow[0];
        month_current = dateNow[1];
        day_current = dateNow[2];
        tvEndTime.setText(year_current + " - " + month_current + " - " + day_current);
    }

    //日期选择器选择数据
    private void selectDate() {

        llEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(hisdata_chart.this)
                        .create();
                dialog.show();
                DatePicker picker = new DatePicker(hisdata_chart.this);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                picker.setDate(year, month);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        endDate = date;
                        endTime = endDate.split("-");
                        year_end = endTime[0];
                        month_end = endTime[1];
                        if (month_end.length() == 1) {
                            month_end = "0" + month_end;
                        }
                        day_end = endTime[2];
                        if (day_end.length() == 1) {
                            day_end = "0" + day_end;
                        }
                        tvEndTime.setText(year_end + " - " + month_end + " - " + day_end);
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
        llStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(hisdata_chart.this)
                        .create();
                dialog.show();
                DatePicker picker = new DatePicker(hisdata_chart.this);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                picker.setDate(year, month);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        startDate = date;
                        startTime = startDate.split("-");
                        year_start = startTime[0];
                        month_start = startTime[1];
                        if (month_start.length() == 1) {
                            month_start = "0" + month_start;
                        }
                        day_start = startTime[2];
                        if (day_start.length() == 1) {
                            day_start = "0" + day_start;
                        }
                        tvStartTime.setText(year_start + " - " + month_start + " - " + day_start);
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }

    //设置 linechart的样式
    private void setLineChart(LineChart chart) {

        // 为chart添加空数据
        chart.setData(new LineData());
        chart.setDescription("");

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置

        // 设置右侧坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        // 设置左侧坐标轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
    }

    private void initView() {
        mLineChart = (LineChart) findViewById(R.id.chart_line);
        showChart = (ImageView) findViewById(R.id.show_chart);
        hisBack = (ImageView) findViewById(R.id.hisdata_img_back);
        llStartTime = (LinearLayout) findViewById(R.id.ll_start_time);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        llEndTime = (LinearLayout) findViewById(R.id.ll_end_time);
        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
        cbEl = (CheckBox) findViewById(R.id.cbx_electricity);
        cbxU = (CheckBox) findViewById(R.id.cbx_U);
        cbxI = (CheckBox) findViewById(R.id.cbx_I);
        cbEl.setChecked(true);
        String url = Const.HIS_DATA_URL + "_hserverSN=" + homeServerSN + "&_deviceSN=" + sdeviceSN + "&begtime=" + year_yes + month_yes + day_yes +
                "104353&endtime=" + year_current + month_current + day_current + "l004753";
//        String url = Const.HIS_DATA_URL + "_hserverSN=" + homeServerSN + "&_deviceSN=" + sdeviceSN + "&begtime=20161007" +
//                "104353&endtime=20161008" + "l004753";//Test 1007-1008
        getData(url);
        addEDataSet();
        click();
    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
//        homeServerSN = intent.getStringExtra("homeServerSN");
//        sdeviceSN = intent.getStringExtra("sdeviceSN");
        homeServerSN = "0000000000000107";
        sdeviceSN = "0001";
    }

    /**
     * 移除一个DataSet
     */
    public void removeDataSet(LineDataSet dataSet) {
        LineData lineData = mLineChart.getData();
        if (lineData != null) {
            lineData.removeDataSet(dataSet);
            mLineChart.notifyDataSetChanged();
            mLineChart.invalidate();
        }
    }

    //电流
    public void addIDataSet() {
        LineData lineData = mLineChart.getLineData();
        if (lineData != null) {
            int cout = (lineData.getDataSetCount() + 1);
            if (lineData.getXValCount() == 0) {
                for (int i = 0; i < listI.size(); i++) {
                    lineData.addXValue(xLabel.get(i));
                }
            }

            for (int i = 0; i < lineData.getXValCount(); i++) {
                yValsI.add(new Entry((Float) listI.get(i), i));
            }

            dataSetI = new LineDataSet(yValsI, "电流");
            dataSetI.setColor(mColors[0]);
            dataSetI.setCircleColor(mColors[0]);
            dataSetI.setValueTextColor(mColors[0]);
            lineData.addDataSet(dataSetI);
            mLineChart.notifyDataSetChanged();
            mLineChart.invalidate();
        }
    }

    //用电量
    public void addEDataSet() {
        LineData lineData = mLineChart.getLineData();
        if (lineData != null) {
            int cout = (lineData.getDataSetCount() + 1);
            if (lineData.getXValCount() == 0) {
                for (int i = 0; i < listElec.size(); i++) {
                    lineData.addXValue(xLabel.get(i));
                }
            }

            for (int i = 0; i < lineData.getXValCount(); i++) {
                yValsE.add(new Entry((Float) listElec.get(i), i));
            }

            dataSetE = new LineDataSet(yValsE, "耗电量");
            dataSetE.setColor(mColors[1]);
            dataSetE.setCircleColor(mColors[1]);
            dataSetE.setValueTextColor(mColors[1]);
            lineData.addDataSet(dataSetE);
            mLineChart.notifyDataSetChanged();
            mLineChart.invalidate();
        }
    }

    //电压
    public void addUDataSet() {
        LineData lineData = mLineChart.getLineData();
        if (lineData != null) {
            int cout = (lineData.getDataSetCount() + 1);
            if (lineData.getXValCount() == 0) {
                for (int i = 0; i < listU.size(); i++) {
                    lineData.addXValue(xLabel.get(i));
                }
            }

            for (int i = 0; i < lineData.getXValCount(); i++) {
                yValsU.add(new Entry((Float) listU.get(i), i));
            }

            dataSetU = new LineDataSet(yValsU, "电压");
            dataSetU.setColor(mColors[2]);
            dataSetU.setCircleColor(mColors[2]);
            dataSetU.setValueTextColor(mColors[2]);
            lineData.addDataSet(dataSetU);
            mLineChart.notifyDataSetChanged();
            mLineChart.invalidate();
        }
    }

    private void getData(String url) {
        Log.d(tag, year_start + month_start + day_start);
        Log.d(tag, year_end + month_end + day_end);

        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CacheCallback<String>() {
            private String result = null;
            private boolean hasError = false;

            @Override
            public boolean onCache(String result) {
                this.result = result;
                return true;//信任缓存
            }

            @Override
            public void onSuccess(String result) {
                hasError = false;
                if (result != null) {
                    this.result = result;
                } else {
                    Toast.makeText(hisdata_chart.this, "获取的数据为空", Toast.LENGTH_SHORT).show();
                    Log.d(tag, "获取的数据为空");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(tag, "onError");
                hasError = true;
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(tag, "onCancelled");
            }

            @Override
            public void onFinished() {
                if (hasError) {
                    Toast.makeText(hisdata_chart.this, "错误", Toast.LENGTH_SHORT).show();
                } else {
                    parseData(result);
                }
            }
        });
    }

    //得到json数据
    private void getData() {
        Log.d(tag, year_start + month_start + day_start);
        Log.d(tag, year_end + month_end + day_end);
//      "http://192.168.1.119:3000/tools/gethisData/getHis?_hserverSN=0000000000000107&begtime=20161007104353&endtime=20161009l004753";

        String url = Const.HIS_DATA_URL + "_hserverSN=" + homeServerSN + "&_deviceSN=" + sdeviceSN + "&begtime=" + year_start + month_start + day_start +
                "104353&endtime=" + year_end + month_end + day_end + "l004753";

        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CacheCallback<String>() {
            private String result = null;
            private boolean hasError = false;

            @Override
            public boolean onCache(String result) {
                this.result = result;
                return true;//信任缓存
            }

            @Override
            public void onSuccess(String result) {
                hasError = false;
                if (result != null) {
                    this.result = result;
                } else {
                    Toast.makeText(hisdata_chart.this, "获取的数据为空", Toast.LENGTH_SHORT).show();
                    Log.d(tag, "获取的数据为空");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(tag, "onError");
                hasError = true;
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(tag, "onCancelled");
            }

            @Override
            public void onFinished() {
                if (hasError) {
                    Toast.makeText(hisdata_chart.this, "错误", Toast.LENGTH_SHORT).show();
                } else {
                    parseData(result);
                }
            }
        });
    }

    //解析json数据
    private void parseData(String json) {
        Gson gson = new Gson();
        Result result = gson.fromJson(json, Result.class);
        deviceDatas = (ArrayList<DeviceData>) result.getResult();
        for (int i = 0; i < deviceDatas.size(); i++) {
            DeviceData deviceData = deviceDatas.get(i);
            float device_electricity = deviceData.getDevice_electricity();
            float device_i = deviceData.getDevice_I();
            float device_u = deviceData.getDevice_U();
            listI.add(device_i);
            listElec.add(device_electricity);
            listU.add(device_u);
            xLabel.add(deviceData.getUpdatetime());
        }
    }


    //控件的点击事件
    private void click() {

        hisBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hisdata_chart.this.finish();
            }
        });

        showChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineData data = mLineChart.getData();
                data.clearValues();
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
                getData();
                yValsE.clear();
                yValsU.clear();
                yValsI.clear();
//                mLineChart.setData(new LineData());//把chart清空
                mLineChart.setData(data);
                boolean cbxIChecked = cbxI.isChecked();
                boolean cbxUChecked = cbxU.isChecked();
                boolean cbElChecked = cbEl.isChecked();
                if (cbElChecked) {
//                    removeDataSet(dataSetE);
//                    LineData data = mLineChart.getData();
//                    dataSetE.
                    addEDataSet();
                }
                if (cbxUChecked) {
//                    removeDataSet(dataSetU);
//                    LineData data = mLineChart.getData();
//                    if (data != null) {
//                        data.removeDataSet(dataSetU);
//                        mLineChart.notifyDataSetChanged();
//                        mLineChart.invalidate();
//                    }
                    addUDataSet();
                }
                if (cbxIChecked) {
//                    removeDataSet(dataSetI);
//                    LineData data = mLineChart.getData();
//                    if (data != null) {
//                        data.removeDataSet(dataSetE);
//                        mLineChart.notifyDataSetChanged();
//                        mLineChart.invalidate();
//                    }
                    addIDataSet();
                }
            }
        });

        cbEl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addEDataSet();
                } else {
                    LineData data = mLineChart.getData();
                    if (data != null) {
                        data.removeDataSet(dataSetE);
                        mLineChart.notifyDataSetChanged();
                        mLineChart.invalidate();
                    }
                }
            }
        });
        cbxU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addUDataSet();
                } else {
                    LineData data = mLineChart.getData();
                    if (data != null) {
                        data.removeDataSet(dataSetU);
                        mLineChart.notifyDataSetChanged();
                        mLineChart.invalidate();
                    }
                }
            }
        });
        cbxI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addIDataSet();
                } else {
                    LineData data = mLineChart.getData();
                    if (data != null) {
                        data.removeDataSet(dataSetI);
                        mLineChart.notifyDataSetChanged();
                        mLineChart.invalidate();
                    }
                }
            }
        });
    }
}