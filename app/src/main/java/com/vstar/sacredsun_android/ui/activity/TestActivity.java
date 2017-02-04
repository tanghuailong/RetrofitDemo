package com.vstar.sacredsun_android.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.annimon.stream.Stream;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vstar.sacredsun_android.R;
import com.vstar.sacredsun_android.dao.ChartValueDTO;
import com.vstar.sacredsun_android.entity.ChartValueEntity;
import com.vstar.sacredsun_android.service.SacredsunService;
import com.vstar.sacredsun_android.util.TextHelper;
import com.vstar.sacredsun_android.util.chart.ConstantChart;
import com.vstar.sacredsun_android.util.chart.HourAxisValueFormatter;
import com.vstar.sacredsun_android.util.chart.MyMarkerView;
import com.vstar.sacredsun_android.util.chart.TimeHelper;
import com.vstar.sacredsun_android.util.rest.HttpMethods;
import com.vstar.sacredsun_android.util.rxjava.RxHelper;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by tanghuailong on 2017/1/22.
 */

public class TestActivity extends AppCompatActivity{

    @BindView(R.id.testChart)
    LineChart lineChart;
    @BindView(R.id.addEntry)
    Button addEntry;

    //现在的时间
    private LocalDateTime currentTime = LocalDateTime.now();
    //当前处在哪个小时
    private int currentHour = LocalTime.now().getHour();

    private String  beginStamp = "";

    private static final String LOG_TAG = "TestActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initChart();

    }

    private void initChart(){

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setPinchZoom(true);
        Description description = new Description();
        description.setTextColor(Color.YELLOW);
        description.setText("温湿度变化");
        lineChart.setDescription(description);
        lineChart.setBackgroundResource(R.color.custom_bg_color);
        MyMarkerView myMarkerView = new MyMarkerView(TestActivity.this, R.layout.custom_marker_view);
        lineChart.setMarker(myMarkerView);
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        lineChart.setData(data);

        Legend legend = lineChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE);

        HourAxisValueFormatter formatter = new HourAxisValueFormatter(LocalTime.now().getHour());


        XAxis xl = lineChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.WHITE);
        xl.setValueFormatter(formatter);
        xl.setAxisMinimum(0f);
        xl.setAxisMaximum(3600);
        xl.setLabelCount(6);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawLabels(true);
        xl.setDrawAxisLine(true);
        xl.setEnabled(true);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setMinWidth(0f);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    @OnClick(R.id.addEntry)
    public void addEntry(){
        Log.d(LOG_TAG,"添加数据");
        List<ChartValueEntity> list = new ArrayList<>();

        Map<String,String> beginAndEnd = TimeHelper.getBeginAndEndTime(currentTime);

        HttpMethods.getInstance().getService(SacredsunService.class)
                .getChartDate("G102",beginAndEnd.get("begin"),beginAndEnd.get("end"))
                .compose(RxHelper.io_main())
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    if (!r.getItems().isEmpty()) {
                        int lastIndex = r.getItems().size();

                        drawLineChart(r.getItems());

                        refreshTodayChart(r.getItems().get(lastIndex-1).getStamp());
                    }
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "completed");
                });
    }

    //依据数据绘制折线图
    private void drawLineChart(List<ChartValueEntity> list) {
        Stream.of(list).map(origin ->  transformChartPoint(origin))
                .forEach(result -> addToChart(result));
    }

    private void refreshTodayChart(String stamp) {

        beginStamp = stamp;

        HttpMethods.getInstance().getService(SacredsunService.class)
                .getLineChartData("G102",beginStamp, TimeHelper.getEndTime(beginStamp))
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.SECONDS)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    if (!r.getItems().isEmpty()) {
                        int lastIndex = r.getItems().size();
                        drawLineChart(r.getItems());
                        beginStamp = r.getItems().get(lastIndex - 1).getStamp();
                        //在需要的时候重新绘制
                        redrawChartWhenNeed(beginStamp);
                    }
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "onCompleted");
                });



    }

    private void  addToChart(ChartValueDTO dto) {

            LineData lineData = lineChart.getData();
            if (lineData != null) {
                String label = ConstantChart.chartTypeAndDesc.get(dto.getType());
                ILineDataSet dataSet = lineData.getDataSetByLabel(label,false);
                if (dataSet == null) {
                    dataSet = createSet(ConstantChart.chartTypeAndDesc.get(dto.getType()));
                    lineData.addDataSet(dataSet);
                }
                int indexOfDateSet = lineData.getIndexOfDataSet(dataSet);
                lineData.addEntry(new Entry(dto.getStamp(), dto.getValue()), indexOfDateSet);
                lineData.notifyDataChanged();
                lineChart.notifyDataSetChanged();
                lineChart.moveViewToX(lineData.getEntryCount());
            }

    }

    private LineDataSet createSet(String dataSetLabel) {

        LineDataSet set = new LineDataSet(null, dataSetLabel);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setColor(ConstantChart.abbreAndColor.get(dataSetLabel));
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;

    }

    private ChartValueDTO transformChartPoint(ChartValueEntity entity) {
        //获得当前日期
        LocalDateTime nowTime = TimeHelper.strTransfromLocalDateTime(entity.getStamp());
        //获得当前时间的秒数(只包括分钟和秒)
        long currentSecond = (long)(nowTime.getMinute() * 60 + nowTime.getSecond());
        ChartValueDTO dto =  new ChartValueDTO(currentSecond, TextHelper.strTransFromFloat(entity.getValue()),entity.getField());
        return dto;
    }

    private void redrawChartWhenNeed(String beginStamp) {

        LocalDateTime localDateTime = TimeHelper.strTransfromLocalDateTime(beginStamp);
        //需要检测时间是否超过当前小时
        if(localDateTime.getHour() != currentHour) {
            currentHour = localDateTime.getHour();
            lineChart.clearValues();
            HourAxisValueFormatter newFormatter = new HourAxisValueFormatter(currentHour);
            XAxis xl = lineChart.getXAxis();
            xl.setValueFormatter(newFormatter);
            lineChart.invalidate();
        }
    }

}
