package com.njsyg.smarthomeapp.activitys.devices.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;

import njsyg.greendao.db.model.DB_Device;

public class sdevice_info_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageViewBack;
    TextView textViewIP;
    TextView textViewType;
    TextView textViewNick;
    TextView textViewInstallPalce;
    ImageView imageViewSetNick;
    ImageView imageViewSetInstallPalce;
    DB_Device db_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdevice_info);
        InitView();
        InitData();
    }

    /*
    *初始化控件
    */
    private void InitView() {
        imageViewBack = (ImageView) findViewById(R.id.sdeviceinfo_img_back);
        textViewIP = (TextView) findViewById(R.id.sdeviceinfo_txt_ipvalue);
        textViewType = (TextView) findViewById(R.id.sdeviceinfo_txt_typevalue);
        textViewNick = (TextView) findViewById(R.id.sdeviceinfo_txt_nickvalue);
        textViewInstallPalce = (TextView) findViewById(R.id.sdeviceinfo_txt_installplacevalue);
        imageViewSetNick = (ImageView) findViewById(R.id.sdeviceinfo_img_setnick);
        imageViewSetInstallPalce = (ImageView) findViewById(R.id.sdeviceinfo_img_setinstallplace);
        imageViewBack.setOnClickListener(this);
        imageViewSetNick.setOnClickListener(this);
        imageViewSetInstallPalce.setOnClickListener(this);
    }
    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
        db_device=(DB_Device) intent.getSerializableExtra("DB_Device");
        if(db_device!=null) {
            textViewNick.setText(db_device.getDevice_nickName());
            textViewInstallPalce.setText(db_device.getDevice_installPlace());
            textViewIP.setText(db_device.getDevice_ip());
            int devicetype=db_device.getDevice_type();
            if(devicetype==1) {
                textViewType.setText("智能开关");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdeviceinfo_img_back:
                //返回
                Back();
                break;
            case R.id.sdeviceinfo_img_setnick:
                //设置昵称
                OpenEditPage("名称", textViewNick.getText().toString(), 1);
                break;
            case R.id.sdeviceinfo_img_setinstallplace:
                //设置安装位置
                OpenEditPage("安装位置", textViewInstallPalce.getText().toString(), 2);
                break;
        }
    }

    /**
     * 返回*
     */
    private void Back() {
        Intent mIntent = new Intent();
        // 设置结果，并进行传送
        mIntent.putExtra("result", "My name is linjiqin");//把返回数据存入Intent
        this.setResult(0, mIntent);
        this.finish();
    }
    @Override
    public void onBackPressed() {
        Back();
    }
    /******
     * 打开编辑页面
     **/
    private void OpenEditPage(String Title, String Value, int RequestCode) {
        Intent intent = new Intent(this, sdevice_edit_Activity.class);
        intent.putExtra("Title", Title);
        intent.putExtra("DB_Device", db_device);
        startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        switch (resultCode) {
            case 1://昵称
                textViewNick.setText(result);
                break;
            case 2://安装位置
                textViewInstallPalce.setText(result);
                break;
        }
    }
}
