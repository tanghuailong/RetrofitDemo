package com.vstar.sacredsun_android.entity;

/**
 * Created by tanghuailong on 2017/1/14.
 */

/**
 * chart 数据部分 entity
 */
public class ChartValueEntity {
    String stamp;
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    @Override
    public String toString() {
        return "ChartValueEntity{" +
                "stamp='" + stamp + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}