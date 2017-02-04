package com.vstar.sacredsun_android.dao;

/**
 * Created by tanghuailong on 2017/1/22.
 */

public class ChartValueDTO {

    private long stamp;              //一个小时内的秒数
    private float value;             //value值
    private String type;             //类型

    public ChartValueDTO() {

    }

    public ChartValueDTO(long stamp, float value, String type) {
        this.stamp = stamp;
        this.value = value;
        this.type = type;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
