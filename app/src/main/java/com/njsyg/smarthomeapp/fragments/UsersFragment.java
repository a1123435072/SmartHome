package com.njsyg.smarthomeapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.devices.device.sdevice_selection_Activity;
import com.njsyg.smarthomeapp.activitys.devices.device_main_Activity;
import com.njsyg.smarthomeapp.activitys.users.user_center_Activity;
import com.njsyg.smarthomeapp.common.PublicInfo;

/**
 * Created by zz on 2016/10/20.
 */
public class UsersFragment extends Fragment implements View.OnClickListener{
    private TextView textViewNick;
    private ImageView imageViewSetNick,imageViewUserHeadImg,imageView_setdevice,btn_setmissions;
    RelativeLayout relativeLayout_setinfo,relativeLayout_setdevice,relativeLayout_setmissions;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        Log.i("UsersFragment","UsersFragment onCreateView");
        InitView(view);
        InitData();
        return view;
    }
    /**
     * 初始化控件
     **/
    private void InitView(View view) {
        imageView_setdevice = (ImageView) view.findViewById(R.id.btn_setdevice);
        textViewNick = (TextView) view.findViewById(R.id.userfragment_txt_nick);
        imageViewSetNick = (ImageView) view.findViewById(R.id.userfragment_img_userheadimg);
        imageViewUserHeadImg = (ImageView) view.findViewById(R.id.userfragment_img_setinfo);
        relativeLayout_setinfo=(RelativeLayout)view.findViewById(R.id.relativeLayout_setinfo);
        relativeLayout_setdevice=(RelativeLayout)view.findViewById(R.id.relativeLayout_setdevice);
        relativeLayout_setmissions=(RelativeLayout)view.findViewById(R.id.relativeLayout_setmissions);
        btn_setmissions=(ImageView)view.findViewById(R.id.btn_setmissions);
        imageView_setdevice.setOnClickListener(this);
        imageViewSetNick.setOnClickListener(this);
        imageViewUserHeadImg.setOnClickListener(this);
        relativeLayout_setinfo.setOnClickListener(this);
        relativeLayout_setdevice.setOnClickListener(this);
        relativeLayout_setmissions.setOnClickListener(this);
        btn_setmissions.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void InitData() {
        String nick = PublicInfo.currentLoginUser.getUser_nickName();
        if (nick.equals("")) {
            textViewNick.setText("未设置昵称");
        } else {
            textViewNick.setText(nick);
        }
        //头像
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userfragment_img_setinfo:
            case R.id.relativeLayout_setinfo://设置用户信息
                Intent intent_info=new  Intent(getActivity(), user_center_Activity.class);
                startActivity(intent_info);
                break;
            case R.id.btn_setdevice:
            case R.id.relativeLayout_setdevice://设置设备
                //显示设备管理界面
//                Intent intent_device = new Intent(getActivity(),device_main_Activity.class);
                Intent intent_device = new Intent(getActivity(),sdevice_selection_Activity.class);
                startActivityForResult(intent_device,0);
                break;
            case R.id.relativeLayout_setmissions:
            case R.id.btn_setmissions:
//                Intent intent_missions = new Intent(getActivity(),MainActivity.class);
//                intent_missions.putExtra("selectTapIndex",1);
//                startActivity(intent_missions);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                //用户信息修改返回
                break;
            case 2:
                break;
            default:
                break;
        }
    }


}
