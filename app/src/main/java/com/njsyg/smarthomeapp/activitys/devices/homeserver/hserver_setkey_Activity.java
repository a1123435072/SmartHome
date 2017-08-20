package com.njsyg.smarthomeapp.activitys.devices.homeserver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.fserver_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerKey;

public class hserver_setkey_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate {
    final String Tag = "hserver_setkey_Activity";
    EditText editTextNKey;
    EditText editTextSKey;
    ImageView imageViewBack;
    ImageView imageViewSave;
    String SN;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hserver_setkey);
        InitView();
        InitData();
    }

    /*
    *初始化控件
    */
    private void InitView() {
        editTextNKey = (EditText) findViewById(R.id.fserversetkey_txt_nkey);
        editTextSKey = (EditText) findViewById(R.id.fserversetkey_txt_skey);
        imageViewBack = (ImageView) findViewById(R.id.fserversetkey_img_back);
        imageViewSave = (ImageView) findViewById(R.id.fserversetkey_img_save);
        imageViewBack.setOnClickListener(this);
        imageViewSave.setOnClickListener(this);
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d(Tag, "发送socket失败");
                        Toast.makeText(hserver_setkey_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        break;
                    case 2:
                        Toast.makeText(hserver_setkey_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        Back();
                        break;
                    case 3:
                        Toast.makeText(hserver_setkey_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        Log.d(Tag, "InitView end");
    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
        SN = intent.getStringExtra("SN");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fserversetkey_img_back://返回
                Back();
                break;
            case R.id.fserversetkey_img_save://保存
                Save();
                break;
        }
    }

    /**
     * 返回*
     */
    private void Back() {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        intent.putExtra("result", "My name is linjiqin");//把返回数据存入Intent
        hserver_setkey_Activity.this.setResult(RESULT_OK, intent);  //设置返回数据
        hserver_setkey_Activity.this.finish();//关闭Activity
    }

    /**
     * 设置秘钥*
     */
    private void Save() {
        String nkey = editTextNKey.getText().toString().trim();
        String skey = editTextNKey.getText().toString().trim();
        T_HServerKey t_hServerKey = new T_HServerKey();
        t_hServerKey.setHserver_sn(SN);
        t_hServerKey.setKeytype(0);
        t_hServerKey.setKeyvalue(nkey);
        fserver_bll.SetHServerKeyBySetHServerKey(handler, t_hServerKey);
        t_hServerKey.setKeytype(1);
        t_hServerKey.setKeyvalue(skey);
        fserver_bll.SetHServerKeyBySetHServerKey(handler, t_hServerKey);
    }

    /**
     * 处理公网服务器返回的命令*
     */
    public void DeaCommand(CommandStruct commandStruct) {
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        Log.d("test", dataStr);
        //再将string转成对象
        ResultInfo<T_HServerKey> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_HServerKey>>() {
        }.getType());
        boolean resultInfoState = resultInfo.getState();
        if (resultInfoState) {
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 2;
            tempMsg.obj = "设置秘钥成功！";
            handler.sendMessage(tempMsg);
        } else {
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 3;
            tempMsg.obj = "设置家庭服务器秘钥失败！";
            handler.sendMessage(tempMsg);
        }
    }

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
            case 0x03:
                DeaCommand(commandStruct);
                break;

        }
    }
}
