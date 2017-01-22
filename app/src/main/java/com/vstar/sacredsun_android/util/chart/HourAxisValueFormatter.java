package com.vstar.sacredsun_android.util.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.threeten.bp.LocalTime;

/**
 * Created by tanghuailong on 2017/1/13.
 */

public class HourAxisValueFormatter implements IAxisValueFormatter {


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        LocalTime localTime = LocalTime.ofSecondOfDay((long)value);
        return localTime.getHour()+":"+localTime.getMinute();
    }




}
