package com.njsyg.smarthomeapp.adapters.MissionsPage;

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
 *  定时任务 RecyclerView  Adapter
 * Created by HUAQING on 2016/10/21.
 */
public class TimeTaskRecyclerViewAdapter extends RecyclerView.Adapter<TimeTaskRecyclerViewAdapter.ViewHolder> {
    private ArrayList<smartDeviceTimeTaskArrayListItem> dataList;
    private OnTapListener onTapListener;
    /** RecycleView设置布局样式*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timed_task_row_view, null);
        return new ViewHolder(v);
    }
    /** RecycleView绑定*/
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (onTapListener != null)
                    onTapListener.onTapView(i);
            }
        });
        viewHolder.im_smartdeviceimage.setImageDrawable(dataList.get(i).getSmartDeviceImage());
        viewHolder.tv_smartDevicename.setText(dataList.get(i).getDeviceName());
        viewHolder.tv_smartdivicetime.setText(dataList.get(i).getTimeTask());
        viewHolder.tv_smartdeviceopen.setText(dataList.get(i).getOpenClose());
        viewHolder.tv_smartdevicechongfu.setText(dataList.get(i).getEveryDay());
        viewHolder.im_smartdivicekaiguan.setImageDrawable(dataList.get(i).getSwitchState());
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }
    /** RecycleView找到控件ID*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_smartdivicetime;
        private TextView tv_smartDevicename;
        private  TextView tv_smartdeviceopen;
        private TextView tv_smartdevicechongfu;
        private ImageView im_smartdeviceimage;
        private ImageView im_smartdivicekaiguan;
        public ViewHolder(View v)
        {
            super(v);
            im_smartdeviceimage=(ImageView) v.findViewById(R.id.im_smart_device_image);/** 定时任务设备图片 */
            tv_smartDevicename = (TextView) v.findViewById(R.id.tv_smart_device_name);/** 定时任务 设备名称 */
            tv_smartdivicetime = (TextView) v.findViewById(R.id.tv_smart_divice_time);/** 定时任务 定时时间 */
            tv_smartdeviceopen = (TextView) v.findViewById(R.id.tv_smart_device_open);/** 定时任务 显示打开关闭状态 */
            tv_smartdevicechongfu= (TextView) v.findViewById(R.id.tv_smart_device_chong_fu);/** 定时任务 显示执行 频率*/
            im_smartdivicekaiguan=(ImageView) v.findViewById(R.id.im_smartdivice_kai_guan);/** 定时任务 打开或者关闭按钮 */
        }
    }
    /** RecycleView 更新数据*/
    public void updataDataList(ArrayList<smartDeviceTimeTaskArrayListItem> smartDeviceTimeTaskArrayListItemArrayList){
        this.dataList = smartDeviceTimeTaskArrayListItemArrayList;
        this.notifyDataSetChanged();
    }
    /** RecycleView 侦听接口*/
    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
