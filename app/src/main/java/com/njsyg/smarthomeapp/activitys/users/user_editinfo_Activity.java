package com.njsyg.smarthomeapp.activitys.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.db_bll.DB_User_BLL;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketClient;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.models.ResultInfo;
import com.njsyg.smarthomeapp.bll.socket_bll.models.T_User;
import com.njsyg.smarthomeapp.bll.user_bll;
import com.njsyg.smarthomeapp.common.PublicInfo;

import njsyg.greendao.db.model.DB_User;

public class user_editinfo_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate {

    final String Tag = "user_editinfo_Activity";
    TextView textViewTitle;
    EditText editTextValue;
    Button buttonSave;
    ImageView imageViewBack;
    Handler handler;
    String title = "";
    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editinfo);
        InitView();
        InitData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "取消委托对象");
        SocketManager.setReadPublicServerDataDelegate(null);//取消委托对象
    }

    /*
    *初始化控件
    */
    private void InitView() {
        textViewTitle = (TextView) findViewById(R.id.usereditinfo_txt_title);
        editTextValue = (EditText) findViewById(R.id.usereditinfo_edit_value);
        buttonSave = (Button) findViewById(R.id.usereditinfo_btn_save);
        imageViewBack = (ImageView) findViewById(R.id.usereditinfo_img_back);
        buttonSave.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this);//注册委托对象
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d(Tag, "发送socket失败");
                        Toast.makeText(user_editinfo_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        break;
                    case 2:
                        Toast.makeText(user_editinfo_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        SaveUserToLocalDB();
                        SaveSuccessBack();
                        break;
                    case 3:
                        Toast.makeText(user_editinfo_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        value = intent.getStringExtra("Value");
        textViewTitle.setText(title);
        if (!value.equals("")) {
            editTextValue.setText(value);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usereditinfo_btn_save:
                //保存
                Save();
                break;
            case R.id.usereditinfo_img_back:
                Back();
                break;
        }
    }

    /**
     * 返回
     */
    private void Back() {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        intent.putExtra("result", "");//把返回数据存入Intent
        this.setResult(RESULT_OK, intent);  //设置返回数据
        this.finish();//关闭Activity
    }
    @Override
    public void onBackPressed() {
        Back();
    }
    /**
     * 返回
     */
    private void SaveSuccessBack() {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        if (title.equals("设置昵称")) {
            this.setResult(1, intent);  //设置返回数据
        }
        if (title.equals("设置邮箱")) {
            this.setResult(2, intent);  //设置返回数据
        }
        this.finish();//关闭Activity
    }

    /**
     * 保存
     */
    private void Save() {
        T_User t_user = new T_User();
        t_user.setUser_id(PublicInfo.currentLoginUser.getUser_id());
        t_user.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
        t_user.setUser_pwd(PublicInfo.currentLoginUser.getUser_pwd());
        String value = editTextValue.getText().toString().trim();
        if (title.equals("设置昵称")) {
            t_user.setUser_nickName(value);
            t_user.setUser_email(PublicInfo.currentLoginUser.getUser_email());
        } else if (title.equals("设置邮箱")) {
            t_user.setUser_nickName(PublicInfo.currentLoginUser.getUser_nickName());
            t_user.setUser_email(value);
        }
        t_user.setUser_headImg(PublicInfo.currentLoginUser.getUser_headImg());
        t_user.setRemark(PublicInfo.currentLoginUser.getRemark());
        user_bll.UpdateUserByPublicServer(handler, t_user);
    }

    /**
    *将用户信息保存到本地*
     */
    public void SaveUserToLocalDB() {
        DB_User db_user = DB_User_BLL.GetOne(PublicInfo.currentLoginUser.getUser_phoneNumber());
        if (db_user != null) {
            String value = editTextValue.getText().toString().trim();
            if (title.equals("设置昵称")) {
                db_user.setUser_nickName(value);
            } else if (title.equals("设置邮箱")) {
                db_user.setUser_email(value);
            }
            DB_User_BLL.Update(db_user);
        }
    }

    @Override
    public void ReadSocketData(EServerType type, byte[] datas) {
        //解析公网服务器返回的数据
        Log.d("Tag", "接收到公网服务器返回的数据");
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(datas);
        if (commandStruct == null) {
            Log.d("test", "commdhelper.UnPackageCommand is null");
            return;
        }
        if (commandStruct.getCommandCode() == 0x05) {
            Gson gson = new Gson();
            //现将byte[] 转成string
            String dataStr = new String(commandStruct.getData());
            Log.d("test", dataStr);
            //再将string转成对象
            ResultInfo<T_User> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_User>>() {
            }.getType());
            boolean resultInfoState = resultInfo.getState();
            if (resultInfoState) {
                //修改成功
                Message tempMsg = handler.obtainMessage();
                if (title.equals("设置昵称")) {
                    PublicInfo.currentLoginUser.setUser_nickName(editTextValue.getText().toString());
                } else if (title.equals("设置邮箱")) {
                    PublicInfo.currentLoginUser.setUser_email(editTextValue.getText().toString());
                }
                tempMsg.what = 2;
                tempMsg.obj = "修改成功!";
                handler.sendMessage(tempMsg);
                Log.d(Tag, "修改成功！");
            } else {
                Message tempMsg = handler.obtainMessage();
                tempMsg.what = 3;
                tempMsg.obj = "修改失败！请稍后再试！";
                handler.sendMessage(tempMsg);
                Log.d(Tag, "修改失败！请稍后再试！");
            }
        }
    }
}
