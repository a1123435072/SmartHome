package com.njsyg.smarthomeapp.activitys.devices.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.adapters.SDevice.SDeviceInfoListItem;
import com.njsyg.smarthomeapp.adapters.SDevice.SDeviceInfoListViewAdapter;
import com.njsyg.smarthomeapp.bll.db_bll.DB_Device_BLL;

import java.util.ArrayList;
import java.util.List;
import njsyg.greendao.db.model.DB_Device;

public class sdevice_list_Activity extends AppCompatActivity implements View.OnClickListener,SDeviceInfoListViewAdapter.DataControlDelegate {

    ListView listViewSDevice;
    ImageView imageViewBack;
    private SDeviceInfoListViewAdapter sDeviceInfoListViewAdapter;
    ArrayList<SDeviceInfoListItem> sDeviceInfoListItemList = new ArrayList<SDeviceInfoListItem>();
    String homeServerSN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        InitView();
        InitData();
    }

    /*
    *初始化控件
    */
    private void InitView() {
        listViewSDevice = (ListView) findViewById(R.id.sdevicelist_listView);
        imageViewBack = (ImageView) findViewById(R.id.sdevicelist_img_back);
        sDeviceInfoListViewAdapter = new SDeviceInfoListViewAdapter(sdevice_list_Activity.this, sDeviceInfoListItemList);
        sDeviceInfoListViewAdapter.setDataControlDelegate(this);
        imageViewBack.setOnClickListener(this);
        listViewSDevice.setAdapter(sDeviceInfoListViewAdapter);
        listViewSDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView c = (TextView) view.findViewById(R.id.item_sdevice_nick);
                String deviceSN = c.getTag().toString();
                //关闭本页面传递到首页选择的deviceSN
                Back(deviceSN);
            }
        });
    }

    /*
    *初始化数据
    */
    private void InitData() {
        homeServerSN = getIntent().getStringExtra("homeServerSN");
        if (!homeServerSN.equals("")) {
            List<DB_Device> db_deviceList = DB_Device_BLL.GetList(homeServerSN);
            if (db_deviceList != null && db_deviceList.size() > 0) {
                SDeviceInfoListItem one;
                for (DB_Device device : db_deviceList) {
                    one = new SDeviceInfoListItem();
                    one.setSdeviceSN(device.getDevice_sn());
                    one.setNick(device.getDevice_nickName());
                    one.setPlace(device.getDevice_installPlace());
                    sDeviceInfoListItemList.add(one);
                }
            }
        }
        sDeviceInfoListViewAdapter.notifyDataSetChanged();
    }
    /// <summary>
    ///返回
    /// </summary>
    private void Back(String deviceSN) {
        //返回
        Intent intent = new Intent();//数据是使用Intent返回
        intent.putExtra("result", deviceSN);//把返回数据存入Intent
        sdevice_list_Activity.this.setResult(2, intent);  //设置返回数据
        sdevice_list_Activity.this.finish();//关闭Activity
    }

    @Override
    public void selectedSDevice(String sdeviceSN) {
        Back(sdeviceSN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdevicelist_img_back:
                //返回
                Back("");
                break;
        }
    }
}