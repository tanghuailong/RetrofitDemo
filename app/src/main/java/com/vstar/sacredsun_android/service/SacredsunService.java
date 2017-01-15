package com.vstar.sacredsun_android.service;

/**
 * Created by tanghuailong on 2017/1/15.
 */

import com.vstar.sacredsun_android.entity.DeviceDetailEntity;
import com.vstar.sacredsun_android.entity.DeviceEntity;
import com.vstar.sacredsun_android.entity.EmptyResult;
import com.vstar.sacredsun_android.entity.HttpResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * retrofit 访问接口
 */
public interface SacredsunService {

    //获取设备的基本生产信息和工艺信息
    @FormUrlEncoded
    @POST("equipmentmgt/control/initKanban")
    public HttpResult<EmptyResult,DeviceEntity> initKanban(@Field("workshopCode") String workshopCode);

    //获取设备的详细信息初始化chart
    @FormUrlEncoded
    @POST("equipmentmgt/control/initChart")
    public HttpResult<DeviceDetailEntity,EmptyResult> initChart(@Field("assetsCode") String assetsCode);

    //TODO 此接口待了解，并且需要清楚上面请求参数的类型
    @FormUrlEncoded
    @POST("equipmentmgt/control/chartData")
    public HttpResult getChartDate();

}
