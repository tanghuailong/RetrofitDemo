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
//  @POST("equipmentmgt/control/initKanban")
    @FormUrlEncoded
    @POST("http://www.mocky.io/v2/587d8f210f000020125df65e")
     Observable<HttpResult<WorkShopName,DeviceEntity>> getDeviceBasicData(@Field("workshopCode") String workshopCode);


    //获取设备的详细信息初始化chart
    //@POST("equipmentmgt/control/initChart")
    @FormUrlEncoded
    @POST("http://www.mocky.io/v2/587d6c660f0000b40f5df63f")
    Observable<HttpResult<DeviceDetailEntity,EmptyResult>> getDeviceDetailData(@Field("assetsCode") String assetsCode);

    //TODO 接口待修改
    @FormUrlEncoded
    @POST("equipmentmgt/control/chartData")
    Observable<HttpResult> getChartDate();

}
