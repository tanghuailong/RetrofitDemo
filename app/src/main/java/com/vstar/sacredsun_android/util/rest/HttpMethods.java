package com.vstar.sacredsun_android.util.rest;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by tanghuailong on 2017/1/11.
 */
/*处理网络访问的工具类*/
public class HttpMethods {

      private static final String BASE_URL = "http://10.100.172.187:8081/";
      private static final int DEFAULT_TIME = 5;
      private static final String TAG = "HttpMethods";

    Retrofit retrofit;

    private HttpMethods() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        //开启网络调试和日志记录
        OkHttpClient client = builder.addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    private static class SingleHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingleHolder.INSTANCE;
    }

    public <T> T getService(Class<T> tClass) {
        return retrofit.create(tClass);
    }


}
