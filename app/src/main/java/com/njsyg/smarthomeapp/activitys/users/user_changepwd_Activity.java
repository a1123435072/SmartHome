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

public class user_changepwd_Activity extends AppCompatActivity implements View.OnClickListener,SocketClient.ReadDataDelegate {

    ImageView imageViewBack;
    EditText editTextOldPwd;
    EditText editTextNewPwd;
    Button buttonSave;
    TextView editTextForgotPwd;
    Handler handler = null;
    final String Tag="user_changepwd_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_changepwd_);
        InitView();
        InitData();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test","取消委托对象");
        SocketManager.setReadPublicServerDataDelegate(null);//取消委托对象
    }
    /*
    *初始化控件
    */
    private void InitView() {
        imageViewBack = (ImageView) findViewById(R.id.changepwd_img_back);
        editTextOldPwd = (EditText) findViewById(R.id.changepwd_txt_oldpwd);
        editTextNewPwd = (EditText) findViewById(R.id.changepwd_txt_newpwd);
        buttonSave = (Button) findViewById(R.id.changepwd_btn_save);
        editTextForgotPwd = (TextView) findViewById(R.id.changepwd_txt_forgodpwd);
        imageViewBack.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        SocketManager.setReadPublicServerDataDelegate(this); //注册委托对象
        Log.d(Tag,"user_changepwd_Activity注册委托对象");
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d(Tag, "发送socket失败");
                        Toast.makeText(user_changepwd_Activity.this, "发送socket失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Log.d(Tag, "发送socket成功");
                        break;
                    case 2:
                        Toast.makeText(user_changepwd_Activity.this,msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        SaveUserPwdToLocalDB();
                        Intent intent = new Intent(user_changepwd_Activity.this, user_login_Activity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Toast.makeText(user_changepwd_Activity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /*
    *初始化数据
    */
    private void InitData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepwd_img_back:
                //返回
                Back();
                break;
            case R.id.changepwd_btn_save:
                //保存
                String oldpwd=editTextOldPwd.getText().toString().trim();
                String newpwd=editTextNewPwd.getText().toString().trim();
                if(oldpwd.equals("")||newpwd.equals("")) {
                    Toast.makeText(this, "请输入旧密码和新密码后点击完成....", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(oldpwd.equals(newpwd))
                {
                    Toast.makeText(this, "新密码和旧密码不能相同.", Toast.LENGTH_SHORT).show();
                    break;
                }
                T_User t_user=new T_User();
                t_user.setUser_id(PublicInfo.currentLoginUser.getUser_id());
                t_user.setUser_nickName(PublicInfo.currentLoginUser.getUser_nickName());
                t_user.setUser_email(PublicInfo.currentLoginUser.getUser_email());
                t_user.setUser_headImg(PublicInfo.currentLoginUser.getUser_headImg());
                t_user.setRemark(PublicInfo.currentLoginUser.getRemark());
                t_user.setUser_phoneNumber(PublicInfo.currentLoginUser.getUser_phoneNumber());
                t_user.setNewPassword(newpwd);
                t_user.setUser_pwd(oldpwd);
                user_bll.UpdatePasswordByPublicServer(handler,t_user);
                break;
            case R.id.changepwd_txt_forgodpwd:
                //忘记密码
                break;
        }
    }
    /**
     *返回
     */
    private void Back() {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        this.setResult(RESULT_OK, intent);  //设置返回数据
        this.finish();//关闭Activity
    }

    /**
    *保存用户密码到本地*
     */
    private void SaveUserPwdToLocalDB()
    {
       DB_User db_user= DB_User_BLL.GetOne(PublicInfo.currentLoginUser.getUser_phoneNumber());
        if(db_user!=null)
        {
            String newpwd=editTextNewPwd.getText().toString().trim();
            db_user.setUser_pwd(newpwd);
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
        if (commandStruct.getCommandCode() == 0x04) {
            Gson gson = new Gson();
            //现将byte[] 转成string
            String dataStr = new String(commandStruct.getData());
            Log.d("test", dataStr);
            //再将string转成对象
            ResultInfo<T_User> resultInfo = gson.fromJson(dataStr, new TypeToken<ResultInfo<T_User>>() {
            }.getType());
            boolean resultInfoState = resultInfo.getState();
            if (resultInfoState) {
                //修改密码成功
                Message tempMsg = handler.obtainMessage();
                tempMsg.what = 2;
                tempMsg.obj = "修改密码成功!";
                handler.sendMessage(tempMsg);
                Log.d(Tag, "修改密码成功！");
            } else {
                Message tempMsg = handler.obtainMessage();
                tempMsg.what = 3;
                tempMsg.obj = "修改密码失败！请稍后再试！";
                handler.sendMessage(tempMsg);
                Log.d(Tag, "修改密码失败！请稍后再试！");
            }
        }
    }
}
