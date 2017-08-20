package com.njsyg.smarthomeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;


import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.njsyg.smarthomeapp.common.utils.ViewFindUtils;
import com.njsyg.smarthomeapp.entity.TabEntity;
import com.njsyg.smarthomeapp.fragments.FindsFragment;
import com.njsyg.smarthomeapp.fragments.IndexFragment;
import com.njsyg.smarthomeapp.fragments.MissionsFragment;
import com.njsyg.smarthomeapp.fragments.UsersFragment;

import java.util.ArrayList;
import java.util.Random;

import static com.njsyg.smarthomeapp.R.mipmap.tab_contact_unselect;
import static com.njsyg.smarthomeapp.R.mipmap.tab_more_select;
import static com.njsyg.smarthomeapp.R.mipmap.tab_more_unselect;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"首页","任务", "发现", "个人中心"};//底部菜单栏：数组
    private int[] mIconUnselectIds = {                            //底部菜单栏：图标
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            tab_more_unselect, tab_contact_unselect};
    private int[] mIconSelectIds = {                                //底部菜单栏：图标
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            tab_more_select, R.mipmap.tab_contact_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //添加四个Fragment
        mFragments.add(new IndexFragment());//首页
        mFragments.add(new MissionsFragment());//消息页面
        mFragments.add(new FindsFragment());//发现界面
        mFragments.add(new UsersFragment());//用户界面

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.view_pager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mViewPager.setOffscreenPageLimit(4);
        /** 菜单栏*/
        menu = ViewFindUtils.find(mDecorView, R.id.tl_menu);
        setMenu();

//        //三位数
//        menu.showMsg(1, 100);
//        menu.setMsgMargin(1, -5, 5);
//        //设置未读消息红点
//        menu.showDot(2);
//        MsgView rtv_2_2 = menu.getMsgView(2);
//        if (rtv_2_2 != null) {
//            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
//        }
//
//        //设置未读消息背景
//        menu.showMsg(3, 5);
//        menu.setMsgMargin(3, 0, 5);
//        MsgView rtv_2_3 = menu.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }
    }

    Random mRandom = new Random();
    private void setMenu() {
        menu.setTabData(mTabEntities);
        menu.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
//                if (position == 0) {
//                    menu.showMsg(0, mRandom.nextInt(100) + 1);
//                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                menu.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {      }
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
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

