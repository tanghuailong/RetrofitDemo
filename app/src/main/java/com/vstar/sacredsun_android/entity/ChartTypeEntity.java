package com.vstar.sacredsun_android.entity;

/**
 * Created by tanghuailong on 2017/1/14.
 */

/**
 * 折线图类型Entity
 */
@Deprecated
public class ChartTypeEntity {

    // temperature1  temperature2 humidity1 and humidity2
    String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ChartTypeEntity{" +
                "field='" + field + '\'' +
                '}';
    }
}
