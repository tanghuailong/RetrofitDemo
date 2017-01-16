package com.vstar.sacredsun_android.service;

/**
 * Created by tanghuailong on 2017/1/15.
 */

import com.vstar.sacredsun_android.entity.DeviceDetailEntity;
import com.vstar.sacredsun_android.entity.DeviceEntity;
import com.vstar.sacredsun_android.entity.EmptyResult;
import com.vstar.sacredsun_android.entity.HttpResult;
import com.vstar.sacredsun_android.entity.WorkShopName;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * retrofit 访问接口
 */
public interface SacredsunService {

    //获取设备的基本生产信息和工艺信息
    @FormUrlEncoded
    @POST("equipmentmgt/control/initKanban")
     Observable<HttpResult<WorkShopName,DeviceEntity>> getDeviceBasicData(@Field("workshopCode") String workshopCode);

    //获取设备的详细信息初始化chart
    @FormUrlEncoded
    @POST("equipmentmgt/control/initChart")
    Observable<HttpResult<DeviceDetailEntity,EmptyResult>> getDeviceDetailData(@Field("assetsCode") String assetsCode);

    //TODO 接口待修改
    @FormUrlEncoded
    @POST("equipmentmgt/control/chartData")
    Observable<HttpResult> getChartDate();

}
