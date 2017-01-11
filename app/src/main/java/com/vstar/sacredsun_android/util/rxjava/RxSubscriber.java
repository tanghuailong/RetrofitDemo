package com.vstar.sacredsun_android.util.rxjava;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by tanghuailong on 2017/1/11.
 */

public abstract class RxSubscriber<T> extends Subscriber {
    @Override
    public void onCompleted() {
        Log.d("RxSubscriber","completed");
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
