package com.vstar.sacredsun_android.dao;

import com.vstar.sacredsun_android.entity.ChartValueEntity;
import com.vstar.sacredsun_android.util.chart.TimeHelper;

import org.threeten.bp.LocalDateTime;

/**
 * Created by tanghuailong on 2017/1/14.
 */

/**
 * 图表 所需的数据
 */
public class ChartValueDTO {
    String chartType;
    long beginOfTime;
    long stamp;
    float value;

    public ChartValueDTO() {

    }
    public ChartValueDTO(ChartValueEntity entity,String chartType) {
        //TODO 时间处理上未找到更好的处理方式
        LocalDateTime temp = TimeHelper.strTransfromLocalDateTime(entity.getStamp());
        this.chartType = chartType;
        this.value = Float.parseFloat(entity.getValue());
        this.stamp = TimeHelper.dateTimeTransformSecond(temp);
        this.beginOfTime = TimeHelper.dateTimeTransformStartSecond(temp);
    }

    public long getBeginOfTime() {
        return beginOfTime;
    }

    public void setBeginOfTime(long beginOfTime) {
        this.beginOfTime = beginOfTime;
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

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    @Override
    public String toString() {
        return "ChartValueDTO{" +
                "chartType='" + chartType + '\'' +
                ", beginOfTime=" + beginOfTime +
                ", stamp=" + stamp +
                ", value=" + value +
                '}';
    }
}
