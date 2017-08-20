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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServerKey_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServer_BLL;
import com.njsyg.smarthomeapp.bll.fserver_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HServerKey;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_HomeServer;

import njsyg.greendao.db.model.DB_HServerKey;
import njsyg.greendao.db.model.DB_HomeServer;

public class hserver_edit_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate{
    final String Tag = "hserver_edit_Activity";
    private String Title = "";
    private String Value = "";
    private String SN = "";
    private ImageView imageViewBack;
    private ImageView imageViewSave;
    private TextView editTextTitle;
    private EditText editTextValue;
    Handler handler;
    DB_HomeServer homeServer;
    DB_HServerKey nkey;
    DB_HServerKey skey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hserver_edit);
        InitView();
        InitHandler();
        InitData();
    }

    /*******
     * 初始化
     */
    private void InitView() {

        imageViewBack = (ImageView) findViewById(R.id.img_fserveredit_back);
        imageViewSave = (ImageView) findViewById(R.id.img_fserveredit_save);
        editTextTitle = (TextView) findViewById(R.id.txt_fserveredit_title);
        editTextValue = (EditText) findViewById(R.id.txt_fserveredit_value);
        imageViewBack.setOnClickListener(this);
        imageViewSave.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this);//注册委托对象
    }

    @Override
    protected void onPause() {
        super.onPause();
        SocketManager.setReadPublicServerDataDelegate(null);//取消委托对象
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
                        Toast.makeText(hserver_edit_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        break;
                    case 2://修改昵称成功
                        Toast.makeText(hserver_edit_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        SaveToLocalDB();
                        Back(4);
                        break;
                    case 3://设置秘钥成功
                        Toast.makeText(hserver_edit_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        SaveToLocalDB();
                        if (Title.equals("普通用户秘钥")) {
                            Back(5);
                        } else if (Title.equals("超级用户秘钥")) {
                            Back(6);
                        }
                        break;
                    case 4:
                        Toast.makeText(hserver_edit_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 初始化数据*
     */
    private void InitData() {
        Intent intent = getIntent();
        Title = intent.getStringExtra("Title");
        SN = intent.getStringExtra("FserverSN");
        switch (Title) {
            case "名称":
                homeServer = (DB_HomeServer) intent.getSerializableExtra("DB_HomeServer");
                if (homeServer != null)
                    Value = homeServer.getHserver_nickName();
                break;
            case "普通用户秘钥":
                nkey = (DB_HServerKey) intent.getSerializableExtra("DB_HServerKey");
                if (nkey != null)
                    Value = nkey.getKeyvalue();
                break;
            case "超级用户秘钥":
                skey = (DB_HServerKey) intent.getSerializableExtra("DB_HServerKey");
                if (skey != null)
                    Value = nkey.getKeyvalue();
                break;
        }
        editTextTitle.setText(Title);
        editTextValue.setText(Value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fserveredit_back:
                Back(0);
                //返回
                break;
            case R.id.img_fserveredit_save:
                //保存
                if (editTextValue.getText().toString().trim().equals("")) {
                    Toast.makeText(hserver_edit_Activity.this, "请输入" + Title, Toast.LENGTH_SHORT).show();
                    return;
                }
                Save();
                break;
        }
    }

    /**
     * 返回*
     */
    private void Back(int resultCode) {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        String result = editTextValue.getText().toString();
        intent.putExtra("result", result);//把返回数据存入Intent
        hserver_edit_Activity.this.setResult(resultCode, intent);  //设置返回数据
        hserver_edit_Activity.this.finish();//关闭Activity
    }
    @Override
    public void onBackPressed() {
        Back(0);
    }
    /**
     * 保存*
     */
    private void Save() {
        //设置昵称
        if (Title.equals("名称")) {
            T_HomeServer t_homeServer = new T_HomeServer();
            t_homeServer.setHserver_nickName(editTextValue.getText().toString().trim());
            t_homeServer.setHserver_sn(SN);
            fserver_bll.UpdFServerByPublicServer(handler, t_homeServer);
        } else if (Title.equals("普通用户秘钥") | Title.equals("超级用户秘钥")) {
            //设置秘钥
            T_HServerKey t_hServerKey = new T_HServerKey();
            t_hServerKey.setHserver_sn(SN);
            if (Title.equals("普通用户秘钥")) {
                t_hServerKey.setKeytype(0);
            } else if (Title.equals("超级用户秘钥")) {
                t_hServerKey.setKeytype(1);
            }
            t_hServerKey.setKeyvalue(editTextValue.getText().toString().trim());
            t_hServerKey.setRemark("");
            fserver_bll.SetHServerKeyBySetHServerKey(handler, t_hServerKey);
        }
    }
    /**
     * 保存到本地*
     */
    private void SaveToLocalDB() {
        String values = editTextValue.getText().toString().trim();
        switch (Title) {
            case "名称":
                DB_HomeServer tmpHomeServer = DB_HServer_BLL.GetOne(homeServer.getHserver_id());
                if (tmpHomeServer != null) {
                    tmpHomeServer.setHserver_nickName(values);
                    DB_HServer_BLL.Update(tmpHomeServer);
                }
                break;
            case "普通用户秘钥":
                DB_HServerKey tmpNKey;
                if(nkey==null){
                    tmpNKey=new DB_HServerKey();
                    tmpNKey.setKeytype(0);
                    tmpNKey.setHserver_sn(SN);
                    tmpNKey.setKeyvalue(values);
                    DB_HServerKey_BLL.Add(tmpNKey);
                }else {
                    tmpNKey= DB_HServerKey_BLL.GetOne(nkey.getId());
                if (tmpNKey != null) {
                    tmpNKey.setKeyvalue(values);
                    DB_HServerKey_BLL.Update(tmpNKey);
                }else {
                    tmpNKey=new DB_HServerKey();
                    tmpNKey.setKeytype(0);
                    tmpNKey.setHserver_sn(SN);
                    tmpNKey.setKeyvalue(values);
                    DB_HServerKey_BLL.Add(tmpNKey);
                }}
                break;
            case "超级用户秘钥":
                DB_HServerKey tmpSKey;
                if(nkey==null){
                    tmpSKey=new DB_HServerKey();
                    tmpSKey.setKeytype(1);
                    tmpSKey.setHserver_sn(SN);
                    tmpSKey.setKeyvalue(values);
                    DB_HServerKey_BLL.Add(tmpSKey);
                }else {
                    tmpSKey = DB_HServerKey_BLL.GetOne(skey.getId());
                    if (tmpSKey != null) {
                        tmpSKey.setKeyvalue(values);
                        DB_HServerKey_BLL.Update(tmpSKey);
                    }else {
                        tmpSKey=new DB_HServerKey();
                        tmpSKey.setKeytype(1);
                        tmpSKey.setHserver_sn(SN);
                        tmpSKey.setKeyvalue(values);
                        DB_HServerKey_BLL.Add(tmpSKey);
                    }
                }
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
            case MyCommandCode.UpdHServer://修改家庭服务器
                //再将string转成对象
                ResultInfo<T_HomeServer> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_HomeServer>>() {
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
            case MyCommandCode.SetHServerKey://设置秘钥
                //再将string转成对象
                ResultInfo<T_HServerKey> resultServerKey = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_HServerKey>>() {
                }.getType());
                boolean resultInfoServerKeState = resultServerKey.getState();
                if (resultInfoServerKeState) {
                    //修改成功
                    Message tempMsg = handler.obtainMessage();
                    tempMsg.what = 3;
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
