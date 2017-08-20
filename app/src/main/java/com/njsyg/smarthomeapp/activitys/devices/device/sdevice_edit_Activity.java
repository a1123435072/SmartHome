package com.njsyg.smarthomeapp.activitys.devices.device;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_Device_BLL;
import com.njsyg.smarthomeapp.bll.sdevice_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_Device;

import njsyg.greendao.db.model.DB_Device;

public class sdevice_edit_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate {
    final String Tag = "sdevice_edit_Activity";
    ImageView imageViewBack;
    ImageView imageViewSave;
    TextView textViewTitle;
    EditText editTextValue;
    Handler handler;
    String Title;
    DB_Device db_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdevice_edit);
        InitView();
        InitHandler();
        InitData();
    }

    /*
    *初始化控件
    */
    private void InitView() {
        imageViewBack = (ImageView) findViewById(R.id.sdeviceedit_img_back);
        imageViewSave = (ImageView) findViewById(R.id.sdeviceedit_img_save);
        textViewTitle = (TextView) findViewById(R.id.sdeviceedit_txt_title);
        editTextValue = (EditText) findViewById(R.id.sdeviceedit_txt_value);
        imageViewBack.setOnClickListener(this);
        imageViewSave.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SocketManager.setReadPublicServerDataDelegate(null);;//取消委托对象
    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
        Title = intent.getStringExtra("Title");
         db_device=(DB_Device)intent.getSerializableExtra("DB_Device");
        String Value = "";
        if (Title.equals("名称")) {
            Value = db_device.getDevice_nickName();
        } else if (Title.equals("安装位置")) {
            Value = db_device.getDevice_installPlace();
        }
        textViewTitle.setText(Title);
        if (!Value.equals("")) {
            editTextValue.setText(Value);
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
                    case 0:
                        Log.d(Tag, "发送socket失败");
                        Toast.makeText(sdevice_edit_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        break;
                    case 2:
                        Toast.makeText(sdevice_edit_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        SaveToLocalDB();
                        if (Title.equals("名称")) {
                            Back(1, editTextValue.getText().toString().trim());
                        } else if (Title.equals("安装位置")) {
                            Back(2, editTextValue.getText().toString().trim());
                        }
                        break;
                    case 3:
                        Toast.makeText(sdevice_edit_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdeviceedit_img_back:
                //返回
                Back(0, "");
                break;
            case R.id.sdeviceedit_img_save:
                //保存
                Save();
                break;
        }
    }

    /**
     * 返回*
     */
    private void Back(int resultCode, String value) {
        Intent mIntent = new Intent();
        // 设置结果，并进行传送
        mIntent.putExtra("result", value);//把返回数据存入Intent
        this.setResult(resultCode, mIntent);
        this.finish();
    }
    @Override
    public void onBackPressed() {
        Back(0,"");
    }
    /**
     * 保存*
     */
    private void Save() {
        T_Device tmp = new T_Device();
        tmp.setDevice_sn(db_device.getDevice_sn());
        if (Title.equals("名称")) {
            tmp.setDevice_nickName(editTextValue.getText().toString().trim());
            tmp.setDevice_installPlace(db_device.getDevice_installPlace());
        } else if (Title.equals("安装位置")) {
            tmp.setDevice_nickName(db_device.getDevice_nickName());
            tmp.setDevice_installPlace(editTextValue.getText().toString().trim());
        }
        Log.d("test", tmp.getDevice_nickName() + ":" + tmp.getDevice_installPlace());
        sdevice_bll.UpdDeviceByPublicServer(handler, tmp);
    }

    /**
     * 保存到本地*
     */
    private void SaveToLocalDB() {
        if (db_device != null) {
            Long id = db_device.getDevice_id();
            DB_Device tmp = DB_Device_BLL.GetOne(id);
            if (Title.equals("名称")) {
                tmp.setDevice_nickName(editTextValue.getText().toString().trim());
                tmp.setDevice_installPlace(db_device.getDevice_installPlace());
            } else if (Title.equals("安装位置")) {
                tmp.setDevice_nickName(db_device.getDevice_nickName());
                tmp.setDevice_installPlace(editTextValue.getText().toString().trim());
            }
            DB_Device_BLL.InsertOrUpdate(tmp);
        }
    }

    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {
        Log.d("Tag", "接收到公网服务器返回的数据");
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        if (commandStruct == null) {
            Log.d("test", "commdhelper.UnPackageCommand is null");
            return;
        }
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        switch (commandStruct.getCommandCode()) {
            case 0x07://修改智能设备
                //再将string转成对象
                ResultInfo<T_Device> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_Device>>() {
                }.getType());
                boolean resultInfoState = resultInfo.getState();
                if (resultInfoState) {
                    //修改成功
                    Message tempMsg = handler.obtainMessage();
                    tempMsg.what = 2;
                    tempMsg.obj = "修改成功!";
                    handler.sendMessage(tempMsg);
                    Log.d(Tag, "修改成功！");
                } else {
                    Message tempMsg = handler.obtainMessage();
                    tempMsg.what = 4;
                    tempMsg.obj = "修改失败！请稍后再试！";
                    handler.sendMessage(tempMsg);
                    Log.d(Tag, "修改失败！请稍后再试！");
                }
                break;
        }
    }
}
