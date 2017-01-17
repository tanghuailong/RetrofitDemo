package com.vstar.sacredsun_android;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

import butterknife.ButterKnife;

/**
 * Created by tanghuailong on 2017/1/13.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络调试工具
        AndroidThreeTen.init(this);
        Stetho.initializeWithDefaults(this);
        ButterKnife.setDebug(true);
    }
}
