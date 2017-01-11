package com.vstar.sacredsun_android.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.service.MovieService;
import com.vstar.sacredsun_android.util.rest.HttpMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tanghuailong on 2017/1/10.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_chart)
    LineChart mLineChart;
    @BindView(R.id.start_mock)
    Button startMock;
    @BindView(R.id.add_mock)
    Button addMock;

    private float currentPosition = 0f;

    private Thread thread;

    private static final String LOG_TAG = "DetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stove);
        ButterKnife.bind(this);
//        initChart(mLineChart);
    }

    private void initChart(LineChart lineChart) {

        lineChart.getDescription().setEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setPinchZoom(true);
        lineChart.setVisibleXRangeMaximum(10);


        lineChart.setBackgroundResource(R.color.custom_bg_color);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        lineChart.setData(data);

        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE);

        XAxis xl = lineChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(false);
        xl.setEnabled(true);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setMinWidth(0f);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void addEntry(float currentX) {
        LineData data = mLineChart.getData();
        if(data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if(set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(currentX,(float)(Math.random() * 40) + 30f),0);
            if(set.getEntryCount() > 20) {
                set.removeFirst();
            }
            data.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.moveViewToX(data.getEntryCount());
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null,"温度");
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244,117,117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private void feedMultiple(){
        if(thread != null) {
            thread.interrupt();
        }

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                addEntry();
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<1000; i++) {
                    runOnUiThread(runnable);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    @OnClick(R.id.start_mock)
    public void startMOck(){
//        Log.d(LOG_TAG,"start mock ...");
//        feedMultiple();
        HttpMethods.getInstance().getService(MovieService.class)
                .getTopMovie(1,12)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((r) -> {
                    Log.d("result", r.toString());
                },(e) -> {
                    e.printStackTrace();
                });

    }

    @OnClick(R.id.add_mock)
    public void addMock() {
        Log.d(LOG_TAG,"add mock...");
        addEntry(currentPosition);
        currentPosition++;
    }
}
