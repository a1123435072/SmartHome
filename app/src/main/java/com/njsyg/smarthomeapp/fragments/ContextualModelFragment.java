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
import com.njsyg.smarthomeapp.adapters.MissionsPage.ContextualModelArrayListItem;
import com.njsyg.smarthomeapp.adapters.MissionsPage.ContextualModelRecyclerViewAdapter;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class ContextualModelFragment extends Fragment {
    private ArrayList<ContextualModelArrayListItem> contextualModelArrayListItemArrayList = new ArrayList<ContextualModelArrayListItem>();//数组
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contextual_model, null, false);
        InitData();/** 初始化数据*/
        /** 初始化控件*/
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.Contextual_modele_recycler_view);
        /** R额cycleView进行的设置*/
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(layoutManager);
        ContextualModelRecyclerViewAdapter recyclerViewAdapter = new ContextualModelRecyclerViewAdapter();
        recyclerViewAdapter.updataDataList(contextualModelArrayListItemArrayList);
        /** 设置侦听*/
        recyclerViewAdapter.setOnTapListener(new OnTapListener() {
            @Override
            public void onTapView(int position) {
                Log.d("ContextualModelFragment", "Tap item : " + position);
            }
        });
        recyclerview.setAdapter(recyclerViewAdapter);
        return view;
    }

    /**初始化数据*/
    private void InitData() {
        for (int i = 0; i < 8; i++) {
            ContextualModelArrayListItem one = new ContextualModelArrayListItem();
            one.setContextualModelImage(getResources().getDrawable(R.drawable.contextual_model_house));/** 情景模式  模式图片*/
            one.setContextualModelName("回家模式");                                 /** 情景模式  模式名称*/
            one.setContextualModelLight(getResources().getDrawable(R.drawable.light));/** 情景模式  智能灯泡*/
            one.setContextualModelTv(getResources().getDrawable(R.drawable.tv));    /** 情景模式  电视*/
            one.setContextualModelMusic(getResources().getDrawable(R.drawable.music));/** 情景模式  音乐*/
            if (one.getIntSwitchState() == 0) {
                one.setContextualModelStartUp(getResources().getDrawable(R.drawable.qi_dong));/** 情景模式  开启关闭状态*/
            } else {
                one.setContextualModelStartUp(getResources().getDrawable(R.drawable.shut_down));/** 情景模式  开启关闭状态*/
            }
            contextualModelArrayListItemArrayList.add(one);
        }
    }


}
