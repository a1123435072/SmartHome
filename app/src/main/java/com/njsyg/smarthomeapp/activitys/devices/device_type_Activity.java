package com.njsyg.smarthomeapp.activitys.devices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.common.MyDeviceType;

public class device_type_Activity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout relativeLayoutKaiguan;
    RelativeLayout relativeLayoutCamera;
    ImageView imageViewBack;
   String fserverSN="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_type);
        InitView();
        InitData();
    }

    /*
    *初始化控件
    */
    private void InitView() {
        relativeLayoutKaiguan = (RelativeLayout) findViewById(R.id.devicetype_layout_kaiguan);
        relativeLayoutCamera = (RelativeLayout) findViewById(R.id.devicetype_layout_camera);
        imageViewBack = (ImageView) findViewById(R.id.devicetype_img_back);
        relativeLayoutKaiguan.setOnClickListener(this);
        relativeLayoutCamera.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
    }
    /*
    *初始化数据
    */
    private void InitData()
    {
        Intent intent=getIntent();
        fserverSN= intent.getStringExtra("fserverSN");
    }

    /**
     * 返回*
     */
    private void Back() {
        Intent mIntent = new Intent();
        // 设置结果，并进行传送
        mIntent.putExtra("result","dd");
        this.setResult(0, mIntent);
        this.finish();
    }
    @Override
    public void onBackPressed() {
        Back();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.devicetype_layout_kaiguan:
                //智能开关
                Intent intent = new Intent(device_type_Activity.this, select_addmode_Activity.class);
                intent.putExtra("deviceType", MyDeviceType.Kaiguan);
                intent.putExtra("fserverSN", fserverSN);
                startActivityForResult(intent, 1);
                break;
            case R.id.devicetype_layout_camera:
                //智能相机
                break;
            case R.id.devicetype_img_back:
                //返回
                Back();
                break;
        }
    }
    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     * <p>
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i("test", result);
    }
}
