package com.vstar.sacredsun_android.service;

/**
 * Created by tanghuailong on 2017/1/15.
 */

import com.vstar.sacredsun_android.entity.ChartValueEntity;
import com.vstar.sacredsun_android.entity.DeviceDetailEntity;
import com.vstar.sacredsun_android.entity.DeviceEntity;
import com.vstar.sacredsun_android.entity.EmptyResult;
import com.vstar.sacredsun_android.entity.HttpResult;
import com.vstar.sacredsun_android.entity.WorkShopName;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * retrofit 访问接口
 */
public interface SacredsunService {

    //获取设备的基本生产信息和工艺信息
     @GET("equipmentmgt/control/initKanban")
     Observable<HttpResult<WorkShopName,DeviceEntity>> getDeviceBasicData(@Query("workshopCode") String workshopCode);


    //获取设备的详细信息初始化chart
//    @GET("equipmentmgt/control/initChart")
    @GET("http://www.mocky.io/v2/58807862270000031bf0de74")
    Observable<HttpResult<DeviceDetailEntity,EmptyResult>> getDeviceDetailData(@Query("assetsCode") String assetsCode);

    //获取图表的数据
//    @GET("equipmentmgt/control/chartData")
    @GET("http://www.mocky.io/v2/588079d3270000271bf0de78")
    Observable<HttpResult<EmptyResult,ChartValueEntity>> getChartDate(@Query("assetsCode") String assetsCode,@Query("date") String date,@Field("refreshTime") String refreshTime );

    //获取设定值
//    @GET("equipmentmgt/control/enactmentValue")
    @GET("http://www.mocky.io/v2/588079d3270000271bf0de78")
    Observable<HttpResult<EmptyResult,ChartValueEntity>> getPresetValue(@Query("assetsCode") String assetsCode,@Query("date") String date);
}
