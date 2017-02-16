package com.vstar.sacredsun_android.util.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by tanghuailong on 2017/1/13.
 */

public class HourAxisValueFormatter implements IAxisValueFormatter {

    public String hour = "";

    public HourAxisValueFormatter() {

    }

    public HourAxisValueFormatter(int hourInt) {
        hour = String.valueOf(hourInt);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        String minute = String.valueOf(Math.round(value/60));
        minute = String.format("%-2s",minute).replace(' ','0');
        return hour + ":" + minute;

    }




}
