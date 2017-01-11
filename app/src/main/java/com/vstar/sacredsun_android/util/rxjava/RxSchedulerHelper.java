package com.vstar.sacredsun_android.util.rxjava;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tanghuailong on 2017/1/11.
 */

public class RxSchedulerHelper {
    public static <T> Observable.Transformer<T,T> io_main() {
        return new Observable.Transformer<T,T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
           }
        };
    }
}
