package com.njsyg.smarthomeapp.activitys.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.users.user_login_Activity;
import com.njsyg.smarthomeapp.common.utils.DensityUtils;
import com.njsyg.smarthomeapp.common.utils.SharePreferenceUtils;

import java.util.ArrayList;

public class welcome_Activity extends AppCompatActivity {
    private static final int[] mImageIds = new int[]{R.drawable.guide_1, R.drawable.guide_2,
            R.drawable.guide_3};
    private Button btnStart;
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout llPointGroup;//引导圆点的父控件
    private int mPointWidth;//两个圆点之间的距离
    private View viewRedPoint;//小红点
    private ViewPager vpGuide;
    SharedPreferences share;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);

        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = findViewById(R.id.view_red_point);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开始体验
                SharePreferenceUtils.putBoolean(welcome_Activity.this,
                      splash_Activity.PREF_IS_USER_GUIDE_SHOWED, true);// 记录已经展现过了新手引导页

                //跳转主页面
                startActivity(new Intent(welcome_Activity.this, user_login_Activity.class));
                finish();
            }
        });
        initViews();
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.addOnPageChangeListener(new GuidePageListener());
    }


    private void initViews() {

        mImageViewList = new ArrayList<>();

        //初始化引导页的三个界面
        for (int mImageId : mImageIds) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageId);//设置引导页背景
            mImageViewList.add(imageView);
        }

        //初始化引导小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);//设置引导页默认圆点

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10)
            );
            if (i > 0) {
                params.leftMargin = DensityUtils.dp2px(this, 10);//设置圆点间隔
            }
            point.setLayoutParams(params);//设置圆点的大小
            llPointGroup.addView(point);//将圆点添加给线性布局

            //获取视图树，对layout结束事件进行监听
            llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                //当layout执行结束后会结束此方法
                @Override
                public void onGlobalLayout() {
                    Log.d("Show", "layout结束");
                    //llPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
                    System.out.println("圆点距离" + mPointWidth);
                }
            });
        }
    }

    /**
     * ViewPaper的数据适配器
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * ViewPager的滑动监听器
     */
    class GuidePageListener implements ViewPager.OnPageChangeListener {

        //滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            System.out.println("当前位置:" + position + ";百分比:" + positionOffset + ";移动距离:" + positionOffsetPixels);
            float length = mPointWidth * positionOffset + position * mPointWidth;//圆点的移动距离
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    viewRedPoint.getLayoutParams();//获取当前红点的布局参数
            params.leftMargin = (int) length;//设置小红点的左边距
            viewRedPoint.setLayoutParams(params);//重新给小红点设置布局参数

        }

        //某个页面被选中
        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {//最后一个页面
                btnStart.setVisibility(View.VISIBLE);//显示开始体验的按钮
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        //滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
