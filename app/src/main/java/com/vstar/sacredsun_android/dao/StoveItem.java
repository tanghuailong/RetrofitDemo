package com.vstar.sacredsun_android.dao;

/**
 * Created by tangh on 2017/1/9.
 */
@Deprecated
public class StoveItem {
    //炉子编号
    private String stoveNum;
    //运行状态
    private String runState;
    //产品型号
    private String productModel;
    //订单数量
    private String orderNum;
    //剩余时间
    private int timeLeft;
    //设定值和实际值
    private int firstSetting;
    private int firstAcutal;
    private int secondSetting;
    private int secondActual;
    private int thirdSetting;
    private int thirdActual;
    private int fourSetting;
    private int fourActual;

    public int getFourActual() {
        return fourActual;
    }

    public void setFourActual(int fourActual) {
        this.fourActual = fourActual;
    }

    public String getStoveNum() {
        return stoveNum;
    }

    public void setStoveNum(String stoveNum) {
        this.stoveNum = stoveNum;
    }

    public int getFourSetting() {
        return fourSetting;
    }

    public void setFourSetting(int fourSetting) {
        this.fourSetting = fourSetting;
    }

    public int getThirdActual() {
        return thirdActual;
    }

    public void setThirdActual(int thirdActual) {
        this.thirdActual = thirdActual;
    }

    public int getSecondSetting() {
        return secondSetting;
    }

    public void setSecondSetting(int secondSetting) {
        this.secondSetting = secondSetting;
    }

    public int getSecondActual() {
        return secondActual;
    }

    public void setSecondActual(int secondActual) {
        this.secondActual = secondActual;
    }

    public int getThirdSetting() {
        return thirdSetting;
    }

    public void setThirdSetting(int thirdSetting) {
        this.thirdSetting = thirdSetting;
    }

    public int getFirstAcutal() {
        return firstAcutal;
    }

    public void setFirstAcutal(int firstAcutal) {
        this.firstAcutal = firstAcutal;
    }

    public int getFirstSetting() {
        return firstSetting;
    }

    public void setFirstSetting(int firstSetting) {
        this.firstSetting = firstSetting;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }
}
