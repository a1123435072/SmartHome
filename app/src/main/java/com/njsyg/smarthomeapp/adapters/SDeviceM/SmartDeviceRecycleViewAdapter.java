package com.njsyg.smarthomeapp.adapters.SDeviceM;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.adapters.IndexPage.OnTapListener;

import java.util.ArrayList;

/**
 * Created by HUAQING on 2016/10/31.
 */

public class SmartDeviceRecycleViewAdapter extends RecyclerView.Adapter<SmartDeviceRecycleViewAdapter.ViewHolder>{
    private ArrayList<SmartDeviceArrayListItem> dataList;
    private DataControlDelegate mDelegate;
    /**
     * 委托接口，用于对adapter中的数据进行操作
     *
     * @author Change
     */
    public static interface DataControlDelegate {
        public void showOther(String sdeviceSN);//
        public void deleteSDevice(String sdeviceSN);//删除
        public void editSDevice(String sdeviceSN);//修改
    }

    public SmartDeviceRecycleViewAdapter(ArrayList<SmartDeviceArrayListItem> dataList) {
        this.dataList = dataList;
    }

    @Override
    public SmartDeviceRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.smart_device_row_view,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SmartDeviceRecycleViewAdapter.ViewHolder viewHolder, final int i) {
        /**绑定控件属性*/
        final String sdeviceSN=dataList.get(i).getsDeviceSN();
        viewHolder.im_smartDeviceImage.setImageDrawable(dataList.get(i).getImgSmartDevice());
        viewHolder.tv_SmartDeviceName.setText(dataList.get(i).getSmartName());
        viewHolder.im_otherSmartDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDelegate!=null)
                {
                    mDelegate.showOther(sdeviceSN);
                }
            }
        });
        viewHolder.im_editSmartDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDelegate!=null)
                {
                    mDelegate.editSDevice(sdeviceSN);
                }
            }
        });
        viewHolder.im_deleteSmartDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDelegate!=null)
                {
                    mDelegate.deleteSDevice(sdeviceSN);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView im_smartDeviceImage;
        private TextView tv_SmartDeviceName;
        private ImageView im_otherSmartDevice;
        private ImageView im_editSmartDevice;
        private ImageView im_deleteSmartDevice;
        public ViewHolder(View v) {
            super(v);
            im_smartDeviceImage = (ImageView) v.findViewById(R.id.im_smart_device_image);/** 家庭服务器图片*/
            tv_SmartDeviceName = (TextView) v.findViewById(R.id.tv_smart_device_name);/** 家庭服务器名字*/
            im_otherSmartDevice = (ImageView) v.findViewById(R.id.im_oher_smart_device);/** 家庭服务器图片*/
            im_editSmartDevice = (ImageView) v.findViewById(R.id.im_edit_smart_device);/** 编辑家庭服务器*/
            im_deleteSmartDevice = (ImageView) v.findViewById(R.id.im_delete_smart_device);/** 删除家庭服务器*/
        }
    }
    /**
     * 设置委托者
     *
     * @param _delegate
     */
    public void setDataControlDelegate(DataControlDelegate _delegate) {
        this.mDelegate = _delegate;
    }
}
