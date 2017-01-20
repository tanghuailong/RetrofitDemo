package com.vstar.sacredsun_android;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by tanghuailong on 2017/1/13.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化时间的工具类
        AndroidThreeTen.init(this);
        //初始化网络调试工具
        Stetho.initializeWithDefaults(this);
    }
}
