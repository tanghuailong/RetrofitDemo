package com.vstar.sacredsun_android.service;

/**
 * Created by tanghuailong on 2017/1/11.
 */

import com.vstar.sacredsun_android.entity.HttpResultTest;
import com.vstar.sacredsun_android.entity.Subject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 测试使用
 */
@Deprecated
public interface MovieService {
    @GET("top250")
    Observable<HttpResultTest<Subject>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
