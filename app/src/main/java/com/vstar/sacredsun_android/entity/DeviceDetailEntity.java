package com.vstar.sacredsun_android.entity;

/**
 * Created by tanghuailong on 2017/1/15.
 */

/**
 * 设备的详细信息
 */
public class DeviceDetailEntity {

    /* "assetsCode":"G101",
       "materialCode":"OPZV-120", ===> 产品型号
       "orderQuantity":"31000", ===> 订单数量
       "residualTime":"12000", ===> 剩余时间，单位分钟
       "assetsState":"pre-solidification",
       "temperature":"50" ===> 设定温度
       "temperature1":"50", ===> 实际温度1
        "temperature2":"52", ===> 实际温度2
        "humidity":"60", ===> 设定湿度
        "humidity1":"60", ===> 实际湿度1
        "humidity2":"64", ===> 实际湿度2
        "productionStage":"1", ===> 生产阶段，格式待定
        "programNumber":"3", ===> 程序号
        "beginTime":"2016-12-29 10:00", ===> 生产周期开始时间
        "endTime":"2017-01-04 14:00", ===> 生产周期结束时间
        "circulatingFan":"1" ===> 循环风机
        "waterValve":"1" ===> 雾化水阀
        "coolingDamper":"1" ===> 降温风门
        "humidityDamper":"1" ===> 排湿风门
        "steamHeating":"1" ===> 蒸汽加热
        "streamHumidity":"1" ===> 蒸汽加湿*/


    private String assetsCode;
    private String materialCode;
    private int orderQuantity;
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
    private String circulatingFan;
    private String waterValve;
    private String coolingDamper;
    private String humidityDamper;
    private String steamHeating;
    private String streamHumidity;
    private int remainingTime;
    private int totalTime;
    private int stageRemainingTime;
    private int stageTotalTime;
    private int isWarning;

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

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
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

    public String getCirculatingFan() {
        return circulatingFan;
    }

    public void setCirculatingFan(String circulatingFan) {
        this.circulatingFan = circulatingFan;
    }

    public String getStreamHumidity() {
        return streamHumidity;
    }

    public void setStreamHumidity(String streamHumidity) {
        this.streamHumidity = streamHumidity;
    }

    public String getHumidityDamper() {
        return humidityDamper;
    }

    public void setHumidityDamper(String humidityDamper) {
        this.humidityDamper = humidityDamper;
    }

    public String getCoolingDamper() {
        return coolingDamper;
    }

    public void setCoolingDamper(String coolingDamper) {
        this.coolingDamper = coolingDamper;
    }

    public String getSteamHeating() {
        return steamHeating;
    }

    public void setSteamHeating(String steamHeating) {
        this.steamHeating = steamHeating;
    }

    public String getWaterValve() {
        return waterValve;
    }

    public void setWaterValve(String waterValve) {
        this.waterValve = waterValve;
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

    public int getStageRemainingTime() {
        return stageRemainingTime;
    }

    public void setStageRemainingTime(int stageRemainingTime) {
        this.stageRemainingTime = stageRemainingTime;
    }

    public int getStageTotalTime() {
        return stageTotalTime;
    }

    public void setStageTotalTime(int stageTotalTime) {
        this.stageTotalTime = stageTotalTime;
    }

    public int getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(int isWarning) {
        this.isWarning = isWarning;
    }

    @Override
    public String toString() {
        return "DeviceDetailEntity{" +
                "assetsCode='" + assetsCode + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", orderQuantity=" + orderQuantity +
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
                ", circulatingFan='" + circulatingFan + '\'' +
                ", waterValve='" + waterValve + '\'' +
                ", coolingDamper='" + coolingDamper + '\'' +
                ", humidityDamper='" + humidityDamper + '\'' +
                ", steamHeating='" + steamHeating + '\'' +
                ", streamHumidity='" + streamHumidity + '\'' +
                ", remainingTime=" + remainingTime +
                ", totalTime=" + totalTime +
                ", stageRemainingTime=" + stageRemainingTime +
                ", stageTotalTime=" + stageTotalTime +
                ", isWarning=" + isWarning +
                '}';
    }
}
