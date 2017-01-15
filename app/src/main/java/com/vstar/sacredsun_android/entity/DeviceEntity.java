package com.vstar.sacredsun_android.entity;
/**
 * Created by tanghuailong on 2017/1/15.
 */

/**
 * 设备基本信息
 */
public class DeviceEntity {

    private String assetsCode;
    private String materialCode;
    private String orderQuantity;
    private String redidualTime;
    private Status status;
    private String temperature;
    private String temperature1;
    private String temperature2;
    private String humidity;
    private String humidity1;
    private String humidity2;

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

    public String getRedidualTime() {
        return redidualTime;
    }

    public void setRedidualTime(String redidualTime) {
        this.redidualTime = redidualTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "DeviceEntity{" +
                "assetsCode='" + assetsCode + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", orderQuantity='" + orderQuantity + '\'' +
                ", redidualTime='" + redidualTime + '\'' +
                ", status=" + status +
                ", temperature='" + temperature + '\'' +
                ", temperature1='" + temperature1 + '\'' +
                ", temperature2='" + temperature2 + '\'' +
                ", humidity='" + humidity + '\'' +
                ", humidity1='" + humidity1 + '\'' +
                ", humidity2='" + humidity2 + '\'' +
                '}';
    }
}
