package com.njsyg.smarthomeapp.fragments;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.adapters.IndexPage.OnTapListener;
import com.njsyg.smarthomeapp.adapters.MissionsPage.TimeTaskRecyclerViewAdapter;
import com.njsyg.smarthomeapp.adapters.MissionsPage.smartDeviceTimeTaskArrayListItem;

import java.util.ArrayList;
/**
 *     定时任务界面
 * A simple {@link Fragment} subclass.
 */
public class TimeTaskFragment extends Fragment {
    private ArrayList<smartDeviceTimeTaskArrayListItem> smartDeviceTimeTaskArrayListItemsArrayList = new ArrayList<smartDeviceTimeTaskArrayListItem>();
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_task, container, false);
        //初始化数据
        InitData();
        /**初始化控件*/
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.Time_Task_recycler_view);
        /** RecycleView 设置*/
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(layoutManager);
        TimeTaskRecyclerViewAdapter recyclerViewAdapter = new TimeTaskRecyclerViewAdapter();
        recyclerViewAdapter.updataDataList(smartDeviceTimeTaskArrayListItemsArrayList);
        /**设置点击侦听*/
        recyclerViewAdapter.setOnTapListener(new OnTapListener() {
            @Override
            public void onTapView(int position) {
                Log.d("TimeTaskFragment", "Tap item : " + position);
            }
        });
        recyclerview.setAdapter(recyclerViewAdapter);

        return view;
    }
    /**初始化数据 */
    private void InitData() {
        for (int i = 0; i < 8; i++) {
            smartDeviceTimeTaskArrayListItem one = new smartDeviceTimeTaskArrayListItem();
            one.setSmartDeviceImage(getResources().getDrawable(R.drawable.online));/** 定时任务 设备图片 */
            one.setDeviceName("卧室智能开关" + i);                                  /** 定时任务 设备名称 */
            one.setTimeTask("07:0" + i);/** 定时任务 定时时间 */
            one.setOpenClose("打开");/** 定时任务 显示打开关闭状态 */
            one.setEveryDay("每天");/** 定时任务 显示执行 频率*/
            if (one.getIntSwitchState() == 0) {
                one.setSwitchState(getResources().getDrawable(R.drawable.open));/** 定时任务 打开或者关闭按钮 */
            } else {
                one.setSwitchState(getResources().getDrawable(R.drawable.closeeee));/** 定时任务 打开或者关闭按钮 */
            }
            smartDeviceTimeTaskArrayListItemsArrayList.add(one);
        }
    }
}
