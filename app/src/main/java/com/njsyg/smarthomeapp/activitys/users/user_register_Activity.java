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
import com.njsyg.smarthomeapp.common.utils.FormatValidateUtils;

import njsyg.greendao.db.model.DB_User;

public class user_register_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate {

    private EditText editTextPhoneNum;
    private EditText editTextPwd;
    private EditText editTextEmail;
    private Button buttonReg;
    private ImageView imageViewBack;
    Handler handler = null;
    final String Tag = "user_register_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_);
        InitView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "取消委托对象");
        SocketManager.setReadPublicServerDataDelegate(null);//取消委托对象
    }

    private void InitView() {

        editTextPhoneNum = (EditText) findViewById(R.id.txt_register_phone);
        editTextPwd = (EditText) findViewById(R.id.txt_register_pwd);
        editTextEmail = (EditText) findViewById(R.id.txt_register_email);
        buttonReg = (Button) findViewById(R.id.btn_register_reg);
        imageViewBack = (ImageView) findViewById(R.id.img_register_back);
        buttonReg.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this);//注册委托对象
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d("test", "发送socket失败");
                        Toast.makeText(user_register_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d("test", "发送socket成功");
                        break;
                    case 2:
                        Toast.makeText(user_register_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        SaveUserToLocalDB();
                        Intent intent = new Intent(user_register_Activity.this, user_login_Activity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Toast.makeText(user_register_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        UserRegiste();
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_register_back:
                Intent intentLogin = new Intent(user_register_Activity.this, user_login_Activity.class);
                startActivity(intentLogin);
                break;
            case R.id.btn_register_reg:
                String phone = editTextPhoneNum.getText().toString().trim();
                String pwd = editTextPwd.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                if ("".equals(phone) || "".equals(pwd)) {
                    Toast.makeText(this, "亲,别闹,赶快注册吧....", Toast.LENGTH_SHORT).show();
                    break;
                }
                //是否是手机号
                if (FormatValidateUtils.isMobileNO(phone)) {
                    user_bll muser_bll = new user_bll();
                    T_User t_user = new T_User();
                    t_user.setUser_phoneNumber(phone);
                    t_user.setUser_email(email);
                    t_user.setUser_headImg("");
                    t_user.setUser_nickName("");
                    t_user.setUser_pwd(pwd);
                    user_bll.PhoneNumberValidateByPublicServer(handler, t_user);

                } else {
                    Toast.makeText(this, "手机号格式有误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /****
     * 注册
     */
    private void UserRegiste() {
        T_User t_user = new T_User();
        String phone = editTextPhoneNum.getText().toString().trim();
        String pwd = editTextPwd.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        t_user.setUser_phoneNumber(phone);
        t_user.setUser_email(email);
        t_user.setUser_pwd(pwd);
        user_bll.UserRegByPublicServer(handler, t_user);
    }
    private void DealReg(boolean resultInfoState) {
        if (resultInfoState) {
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 2;
            tempMsg.obj = "注册成功了,赶快登陆去吧！";
            handler.sendMessage(tempMsg);
            Log.d(Tag, "注册成功！");

        } else {
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 3;
            tempMsg.obj = "注册失败！请稍后再试！";
            handler.sendMessage(tempMsg);
            Log.d(Tag, "注册失败！请稍后再试！");
        }
    }

    private void DealPhoneValidate(boolean resultInfoState) {
        if (resultInfoState) {//手机号可以注册
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 4;
            handler.sendMessage(tempMsg);
        } else {
            Message tempMsg = handler.obtainMessage();
            tempMsg.what = 3;
            tempMsg.obj = "该手机号已经注册！";
            handler.sendMessage(tempMsg);
        }
    }

    /**
     * 将用户信息保存到本地数据库*
     */
    private void SaveUserToLocalDB() {
        String user_phoneNumber = editTextPhoneNum.getText().toString().trim();
        String user_email = editTextEmail.getText().toString().trim();
        String user_pwd = editTextPwd.getText().toString().trim();
        DB_User db_user = new DB_User();
        db_user.setUser_phoneNumber(user_phoneNumber);
        db_user.setUser_email(user_email);
        db_user.setUser_pwd(user_pwd);
        DB_User_BLL.Add(db_user);
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
        Gson gson = new Gson();
        //现将byte[] 转成string
        String dataStr = new String(commandStruct.getData());
        //再将string转成对象
        ResultInfo<T_User> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_User>>() {
        }.getType());
        boolean resultInfoState = resultInfo.getState();
        switch (commandStruct.getCommandCode()) {
            case 0x02:
                DealReg(resultInfoState);
                break;
            case 0x01:
                DealPhoneValidate(resultInfoState);
                break;
        }
    }
}
