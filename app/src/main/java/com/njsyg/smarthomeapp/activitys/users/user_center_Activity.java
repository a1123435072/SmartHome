package com.njsyg.smarthomeapp.activitys.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.common.PublicInfo;

public class user_center_Activity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayoutHeadImg;
    LinearLayout linearLayoutNick;
    LinearLayout linearLayoutEmail;
    LinearLayout linearLayoutChangePwd;
    Button buttonLoginOut;
    ImageView imageViewHeadImg;
    TextView textViewNick;
    TextView textViewEmail;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center_);
        InitView();
        InitData();
    }

    /**
     * 初始化控件*
     */
    private void InitView() {
        linearLayoutHeadImg = (LinearLayout) findViewById(R.id.usercenter_linear_headimg);
        linearLayoutNick = (LinearLayout) findViewById(R.id.usercenter_linear_nick);
        linearLayoutEmail = (LinearLayout) findViewById(R.id.usercenter_linear_email);
        linearLayoutChangePwd = (LinearLayout) findViewById(R.id.usercenter_linear_changepwd);
        buttonLoginOut = (Button) findViewById(R.id.usercenter_btn_loginout);
        imageViewHeadImg = (ImageView) findViewById(R.id.usercenter_img);
        textViewNick = (TextView) findViewById(R.id.usercenter_txt_nickvalue);
        textViewEmail = (TextView) findViewById(R.id.usercenter_txt_emailvalue);
        imageViewBack = (ImageView) findViewById(R.id.usercenter_img_back);
        linearLayoutHeadImg.setOnClickListener(this);
        linearLayoutNick.setOnClickListener(this);
        linearLayoutEmail.setOnClickListener(this);
        linearLayoutChangePwd.setOnClickListener(this);
        buttonLoginOut.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
    }

    /*
    *初始化数据
    */
    private void InitData() {
        String nick = PublicInfo.currentLoginUser.getUser_nickName();
        String email = PublicInfo.currentLoginUser.getUser_email();
        String headimg = PublicInfo.currentLoginUser.getUser_headImg();
        if (nick.equals("")) {
            textViewNick.setText("未设置昵称");
        } else {
            textViewNick.setText(nick);
        }
        if (email.equals("")) {
            textViewEmail.setText("未绑定邮箱");
        } else {
            textViewEmail.setText(email);
        }
        if (headimg.equals("")) {
            imageViewHeadImg.setImageResource(R.drawable.user_default_image);
        } else {
            //需要生成图片显示
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usercenter_linear_headimg:
                //设置头像
                break;
            case R.id.usercenter_linear_nick:
                //设置昵称
                ShowEditPage("设置昵称", textViewNick.getText().toString(),1);
                break;
            case R.id.usercenter_linear_email:
                //设置email
                ShowEditPage("设置邮箱",textViewEmail.getText().toString(), 2);
                break;
            case R.id.usercenter_linear_changepwd:
                //修改密码
                Intent intent = new Intent(user_center_Activity.this, user_changepwd_Activity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.usercenter_btn_loginout:
                //退出登录
                this.finish();
                Intent intentlogin = new Intent(user_center_Activity.this, user_login_Activity.class);
                startActivity(intentlogin);
                break;
            case R.id.usercenter_img_back:
                Back();
                break;
        }
    }

    /**
     * 修改头像*
     */
    private void EditHeadImg() {

    }

    /**
     * 打开修改页面*
     */
    private void ShowEditPage(String title, String value, int requestCode) {
        Intent intent = new Intent(user_center_Activity.this, user_editinfo_Activity.class);
        intent.putExtra("Title", title);
        intent.putExtra("Value", value);
        startActivityForResult(intent, requestCode);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求码来区别
        switch (requestCode) {
            case 1:
                //设置昵称
                Log.d("user_center_Activity","设置昵称返回");
                if(textViewNick==null)
                {
                    Log.d("user_center_Activity","textViewNick is null ");
                }
                Log.d("user_center_Activity","textViewNick "+textViewNick.getText().toString());
                Log.d("user_center_Activity","textViewNick "+PublicInfo.currentLoginUser.getUser_nickName());
                textViewNick.setText(PublicInfo.currentLoginUser.getUser_nickName());
                break;
            case 2:
                //绑定邮箱
                Log.d("user_center_Activity","绑定邮箱返回");
                textViewEmail.setText(PublicInfo.currentLoginUser.getUser_email());
                break;
            case 3:
                //修改密码
                break;
            default:
                break;
        }
    }

    /**
    *返回*
     */
    private void Back() {
        Intent mIntent = new Intent();
        // 设置结果，并进行传送
        this.setResult(0, mIntent);
        this.finish();
    }
    @Override
    public void onBackPressed() {
        Back();
    }
}
