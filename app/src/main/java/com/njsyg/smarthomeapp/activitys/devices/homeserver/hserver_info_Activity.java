package com.njsyg.smarthomeapp.activitys.devices.homeserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServerKey_BLL;
import com.njsyg.smarthomeapp.bll.db_bll.DB_HServer_BLL;

import java.util.List;

import njsyg.greendao.db.model.DB_HServerAndUser;
import njsyg.greendao.db.model.DB_HServerKey;
import njsyg.greendao.db.model.DB_HomeServer;

/**
 * Created by user on 2016/7/6.
 */
public class hserver_info_Activity extends AppCompatActivity implements View.OnClickListener {
    int fserverid = 0;
    private ImageView imageButtonBack;
    private TextView txtIP;
    private TextView txtNick;
    private TextView txtNKey;
    private TextView txtSkey;
    private ImageView imageViewSetNick;
    private ImageView imageViewSetNKey;
    private ImageView imageViewSetSKey;
    private CheckBox checkBoxSetdefault;
    private String SN="";
    DB_HServerAndUser db_hServerAndUser;
    DB_HomeServer homeServer;
    DB_HServerKey nkey;
    DB_HServerKey skey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hserver_info);
        InitView();
        InitData();
    }

    /**
     * 初始化控件*
     */
    private void InitView() {
        imageButtonBack = (ImageView) findViewById(R.id.img_fserverinfo_back);
        txtIP = (TextView) findViewById(R.id.txt_fserverinfo_ip);
        txtNick = (TextView) findViewById(R.id.txt_fserverinfo_nick);
        txtNKey = (TextView) findViewById(R.id.txt_fserverinfo_nkey_value);
        txtSkey = (TextView) findViewById(R.id.txt_fserverinfo_skey_value);
        imageViewSetNick = (ImageView) findViewById(R.id.btn_fserverinfo_setnick);
        imageViewSetNKey = (ImageView) findViewById(R.id.btn_fserverinfo_setnkey);
        imageViewSetSKey = (ImageView) findViewById(R.id.btn_fserverinfo_setskey);
        checkBoxSetdefault=(CheckBox)findViewById(R.id.cb_setdefault);
        imageButtonBack.setOnClickListener(this);
        imageViewSetNick.setOnClickListener(this);
        imageViewSetNKey.setOnClickListener(this);
        imageViewSetSKey.setOnClickListener(this);
    }

    /*
    *初始化数据
    */
    private void InitData() {
        Intent intent = getIntent();
         db_hServerAndUser = (DB_HServerAndUser) intent.getSerializableExtra("DB_HServerAndUser");
        if (db_hServerAndUser != null) {
             homeServer= DB_HServer_BLL.GetOne(db_hServerAndUser.getId());
             List<DB_HServerKey> keyList= DB_HServerKey_BLL.GetList(db_hServerAndUser.getHserver_sn());
            if(homeServer!=null) {
                txtIP.setText(homeServer.getHserver_ip());
            }
            txtNick.setText(db_hServerAndUser.getHserver_nickName());
            SN=db_hServerAndUser.getHserver_sn();
            if(db_hServerAndUser.getIsdefault()==1)
                checkBoxSetdefault.setChecked(true);
            else
                checkBoxSetdefault.setChecked(false);
            if (keyList == null || keyList.size() == 0) {
                txtNKey.setText("暂未设置");
                txtSkey.setText("暂未设置");
                return;
            }
            for (DB_HServerKey x : keyList) {
                if (x.getKeytype() == 0) {
                    txtNKey.setText(x.getKeyvalue());
                    nkey=new DB_HServerKey();
                    nkey.setKeytype(0);
                    nkey.setKeyvalue(x.getKeyvalue());
                    continue;
                } else if (x.getKeytype() == 1) {
                    txtSkey.setText(x.getKeyvalue());
                    skey=new DB_HServerKey();
                    skey.setKeytype(0);
                    skey.setKeyvalue(x.getKeyvalue());
                    continue;
                }
            }

            if (txtNKey.getText().equals("")) {
                txtNKey.setText("暂未设置");
            } else if (txtSkey.getText().equals("")) {
                txtSkey.setText("暂未设置");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fserverinfo_back:
                //返回
                Back();
                break;
            case R.id.btn_fserverinfo_setnick:
                //设置昵称
                OpenEditPage("名称", txtNick.getText().toString(), 4);
                break;
            case R.id.btn_fserverinfo_setnkey:
                //设置普通用户秘钥
                OpenEditPage("普通用户秘钥", txtNKey.getText().toString(), 5);
                break;
            case R.id.btn_fserverinfo_setskey:
                //设置超级用户秘钥
                OpenEditPage("超级用户秘钥", txtSkey.getText().toString(), 6);
                break;
        }
    }

    /******
     * 打开编辑页面
     **/
    private void OpenEditPage(String Title, String Value, int RequestCode) {
        Intent intent = new Intent(this, hserver_edit_Activity.class);
        intent.putExtra("Title", Title);
        intent.putExtra("FserverSN",db_hServerAndUser.getHserver_sn());
       // intent.putExtra("Value", Value);
        switch (Title)
        {
            case "名称":
                intent.putExtra("DB_HomeServer",homeServer);
                break;
            case "普通用户秘钥":
                intent.putExtra("DB_HServerKey",nkey);
                break;
            case "超级用户秘钥":
                intent.putExtra("DB_HServerKey",skey);
                break;
        }
        startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        switch (resultCode) {
            case 4://昵称
                this.txtNick.setText(result);
                break;
            case 5://普通用户秘钥
                this.txtNKey.setText(result);
                break;
            case 6://超级用户秘钥
                this.txtSkey.setText(result);
                break;
        }
    }

    /*********
     * 返回
     */
    private void Back() {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        intent.putExtra("result", "My name is linjiqin");//把返回数据存入Intent
        hserver_info_Activity.this.setResult(RESULT_OK, intent);  //设置返回数据
        hserver_info_Activity.this.finish();//关闭Activity
    }
    @Override
    public void onBackPressed() {
        Back();
    }
}