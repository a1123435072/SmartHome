package com.njsyg.smarthomeapp.dal;

import android.app.Application;

import org.xutils.x;

/**
 * Created by PCPC on 2016/6/8.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DbCore.init(this);
        x.Ext.init(this);//想要使用Xutils，所进行的初始化操作
    }
}
