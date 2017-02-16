package com.vstar.sacredsun_android.util.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.vstar.sacredsun_android.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

/**
 * Created by tanghuailong on 2017/1/15.
 */

public class MyMarkerView extends MarkerView {

    private TextView tvContent;


    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(e.getY() + " 在 " + getDateTime(e.getX()));
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }

    public String getDateTime(float xValue) {
//        String  localTime = TimeHelper.secondTransformStr((long) xValue);
        String minute = String.valueOf(((int) xValue/60));
        minute = String.format("%-2s",minute).replace(' ','0');
        String second = String.valueOf((int) xValue % 60);
        second = String.format("%-2s",second).replace(' ','0');
        String localDate = TimeHelper.localDateTransformStr(LocalDate.now());
        return localDate+" "+ LocalTime.now().getHour()+":"+minute+":"+second;
    }
}