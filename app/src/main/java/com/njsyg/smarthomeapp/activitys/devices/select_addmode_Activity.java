package com.njsyg.smarthomeapp.activitys.devices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.devices.homeserver.hserver_search_Activity;
import com.njsyg.smarthomeapp.common.MyAddMode;

/**
 * Created by user on 2016/7/4.
 */
public class select_addmode_Activity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout layout_scan;
    RelativeLayout layout_search;
    ImageView imageViewBack;
    final String Tag = "select_addmode_Activity";
    int DeviceType = -1;
    String fserverSN="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_add_mode);
        InitView();
        InitData();
    }

    /**
     * 初始化控件*
     */
    private void InitView() {
        layout_scan = (RelativeLayout) findViewById(R.id.layout_scan);
        layout_search = (RelativeLayout) findViewById(R.id.layout_search);
        imageViewBack = (ImageView) findViewById(R.id.selectaddmode_img_back);
        layout_scan.setOnClickListener(this);
        layout_search.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
        DeviceType = intent.getIntExtra("deviceType", -1);
        fserverSN = intent.getStringExtra("fserverSN");
        Log.d(Tag, "fserverSN:" + fserverSN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_scan:
                //ShowDeviceSearch(MyAddMode.Scan);
                break;
            case R.id.layout_search:
                ShowDeviceSearch(MyAddMode.Search);
                break;
            case R.id.selectaddmode_img_back:
                Back();
                break;
        }
    }

    /**
     * 返回*
     */
    private void Back() {
        Intent mIntent = new Intent();
        // 设置结果，并进行传送
        mIntent.putExtra("result","test");
        this.setResult(0, mIntent);
        this.finish();
    }
    @Override
    public void onBackPressed() {
        Back();
    }
    /*****
     * 显示查找设备页面
     ***/
    private void ShowDeviceSearch(String addmode) {
        Intent intent = new Intent();
        intent.putExtra("deviceType", DeviceType);
        intent.putExtra("addMode", addmode);
        Log.d(Tag,"fserverSN:"+fserverSN);
        if (fserverSN==null) {
            intent.setClass(this,hserver_search_Activity.class);
        } else {
            intent.putExtra("fserverSN", fserverSN);
            intent.setClass(this, hserver_search_Activity.class);
        }
        startActivity(intent);
    }
}
