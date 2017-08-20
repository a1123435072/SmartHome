package com.njsyg.smarthomeapp.activitys.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.activitys.users.user_login_Activity;
import com.njsyg.smarthomeapp.bll.socket_bll.SocketManager;
import com.njsyg.smarthomeapp.common.utils.SharePreferenceUtils;

public class splash_Activity extends AppCompatActivity {
    protected static final String TAG = splash_Activity.class.getSimpleName();
    public static final String PREF_IS_USER_GUIDE_SHOWED = "is_user_guide_showed";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitSocketService();
        setContentView(R.layout.activity_weclome_splash);
        initViews();
    }
    private void InitSocketService() {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                SocketManager.initializePublicServer();
            }
        });
        one.start();
    }
    // 初始化欢迎页面的动画
    private void initViews() {
        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);// 初始化动画集合
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画结束的回调
            @Override
            public void onAnimationEnd(Animation animation) {
                // 判断新手引导是否展示过
                boolean showed = SharePreferenceUtils.getBoolean(
                        splash_Activity.this, PREF_IS_USER_GUIDE_SHOWED, false);

                if (showed) {
                    // 已经展示过, 进入主页面
                    Log.d(TAG, "进入主页面");
                    startActivity(new Intent(splash_Activity.this,
                            user_login_Activity.class));
                } else {
                    // 没展示, 进入新手引导页面
                    Log.d(TAG, "进入新手引导页面");
                    startActivity(new Intent(splash_Activity.this,
                            welcome_Activity.class));
                }

                finish();
            }
        });

        rlRoot.startAnimation(set);
    }
}
