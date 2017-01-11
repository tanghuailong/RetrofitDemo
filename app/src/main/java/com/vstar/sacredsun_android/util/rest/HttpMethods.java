package com.vstar.sacredsun_android.util.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by tanghuailong on 2017/1/11.
 */
/*处理网络访问的工具类*/
public class HttpMethods {

    //测试使用
    private static final String BASE_URL = "https://api.douban.com/v2/movie/";
//  private static final String BASE_URL = "";
    private static final int DEFAULT_TIME = 5;

    Retrofit retrofit;

    private HttpMethods(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    private static class SingleHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }
    public static HttpMethods getInstance() {
        return SingleHolder.INSTANCE;
    }

    public<T> T getService(Class<T> tClass){
        return retrofit.create(tClass);
    }
}
