package com.njsyg.smarthomeapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.common.utils.ViewFindUtils;
import com.njsyg.smarthomeapp.entity.TabEntity;

import java.util.ArrayList;
import java.util.Random;

/**
 * 定时任务容器：TimeTaskFragment + ContextualModelFragment
 * Created by zz on 2016/10/20.
 */
public class MissionsFragment extends Fragment {
    private String Tag="MissionsFragment";
    private Context mContext = this.getContext();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.time_task_blackkkkkkkkkk, R.mipmap.qingjingmoshi_blackkkkkk};/**显示 非当前页状态图标*/
    private int[] mIconSelectIds = {
            R.mipmap.time_task_white, R.mipmap.qingjingmoshi_white};/**显示 在当前页状态图标*/
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager mViewPager;
    private CommonTabLayout timeTask_contextualModel_TabLayout;
    /** 设置两个Fragment界面，TimeTaskFragment + ContextualModelFragment 并设置显示的图标*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments.add(new TimeTaskFragment());
        mFragments.add(new ContextualModelFragment());
        for (int i = 0; i < 2; i++) {
            mTabEntities.add(new TabEntity(mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missions, container,false);
        Log.i(Tag,"mFragments:"+mFragments.size()+";"+"mTabEntities:"+mTabEntities.size());
        mViewPager = ViewFindUtils.find(view, R.id.time_task_view_pager);/**找到对应的ViewPager*/
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        timeTask_contextualModel_TabLayout = ViewFindUtils.find(view, R.id.title_up);/**找到对应的Tile*/
        title();/**对Title的一些设置*/
        return view;
    }

    Random mRandom = new Random();
    private void title() {
        timeTask_contextualModel_TabLayout.setTabData(mTabEntities);
        timeTask_contextualModel_TabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                Log.d(Tag,"timeTask_contextualModel_TabLayout setCurrentItem position:"+position);
            }
            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    timeTask_contextualModel_TabLayout.showMsg(0, mRandom.nextInt(100) + 1); 设置未读消息
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                timeTask_contextualModel_TabLayout.setCurrentTab(position);
                Log.d(Tag,"mViewPager onPageSelected position:"+position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(0);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
    /** 下面这一部分我不明白具体什么用，如果没用的删掉吧*/
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
