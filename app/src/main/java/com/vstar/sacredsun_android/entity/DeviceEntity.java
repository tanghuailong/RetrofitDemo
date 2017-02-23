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
    private int quantity;
    private int remainingTime;
    private int totalTime;
    private Status status;
    private String temperature;
    private String temperature1;
    private String temperature2;
    private String humidity;
    private String humidity1;
    private String humidity2;
    private int isWarning;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public Status getStatus() {
        return status==null?Status.UNUSED:status;
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

    public int getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(int isWarning) {
        this.isWarning = isWarning;
    }

    @Override
    public String toString() {
        return "DeviceEntity{" +
                "assetsCode='" + assetsCode + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", quantity=" + quantity +
                ", remainingTime=" + remainingTime +
                ", totalTime=" + totalTime +
                ", status=" + status +
                ", temperature='" + temperature + '\'' +
                ", temperature1='" + temperature1 + '\'' +
                ", temperature2='" + temperature2 + '\'' +
                ", humidity='" + humidity + '\'' +
                ", humidity1='" + humidity1 + '\'' +
                ", humidity2='" + humidity2 + '\'' +
                ", isWarning=" + isWarning +
                '}';
    }
}
