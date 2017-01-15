package com.vstar.sacredsun_android.entity;

/**
 * Created by tanghuailong on 2017/1/15.
 */

/**
 * 设备的详细信息
 */
public class DeviceDetailEntity {

    private String assetsCode;
    private String materialCode;
    private String orderQuantity;
    private String residualTime;
    private Status assetsState;
    private String temperature;
    private String temperature1;
    private String temperature2;
    private String humidity;
    private String humidity1;
    private String humidity2;
    private String productionStage;
    private String programNumber;
    private String beginTime;
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAssetsCode() {
        return assetsCode;
    }

    public void setAssetsCode(String assetsCode) {
        this.assetsCode = assetsCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getResidualTime() {
        return residualTime;
    }

    public void setResidualTime(String residualTime) {
        this.residualTime = residualTime;
    }

    public Status getAssetsState() {
        return assetsState;
    }

    public void setAssetsState(Status assetsState) {
        this.assetsState = assetsState;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature1() {
        return temperature1;
    }

    public void setTemperature1(String temperature1) {
        this.temperature1 = temperature1;
    }

    public String getTemperature2() {
        return temperature2;
    }

    public void setTemperature2(String temperature2) {
        this.temperature2 = temperature2;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getHumidity1() {
        return humidity1;
    }

    public void setHumidity1(String humidity1) {
        this.humidity1 = humidity1;
    }

    public String getHumidity2() {
        return humidity2;
    }

    public void setHumidity2(String humidity2) {
        this.humidity2 = humidity2;
    }

    public String getProductionStage() {
        return productionStage;
    }

    public void setProductionStage(String productionStage) {
        this.productionStage = productionStage;
    }

    public String getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(String programNumber) {
        this.programNumber = programNumber;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public String toString() {
        return "DeviceDetailEntity{" +
                "assetsCode='" + assetsCode + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", orderQuantity='" + orderQuantity + '\'' +
                ", residualTime='" + residualTime + '\'' +
                ", assetsState=" + assetsState +
                ", temperature='" + temperature + '\'' +
                ", temperature1='" + temperature1 + '\'' +
                ", temperature2='" + temperature2 + '\'' +
                ", humidity='" + humidity + '\'' +
                ", humidity1='" + humidity1 + '\'' +
                ", humidity2='" + humidity2 + '\'' +
                ", productionStage='" + productionStage + '\'' +
                ", programNumber='" + programNumber + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
