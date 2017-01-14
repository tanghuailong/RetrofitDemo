package com.vstar.sacredsun_android.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.annimon.stream.Stream;
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
import com.vstar.sacredsun_android.dao.ChartValueDTO;
import com.vstar.sacredsun_android.entity.ChartTypeEntity;
import com.vstar.sacredsun_android.entity.ChartValueEntity;
import com.vstar.sacredsun_android.entity.HttpResult;
import com.vstar.sacredsun_android.util.chart.ConstantChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        initChart(mLineChart);
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
        xl.setAxisMinimum(0f);
        xl.setAxisMaximum(86400f);
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

//    private void addEntry(float currentX) {
//        LineData data = mLineChart.getData();
//        if (data != null) {
//            ILineDataSet set = data.getDataSetByIndex(0);
//            if (set == null) {
//                set = createSet();
//                data.addDataSet(set);
//            }
//            data.addEntry(new Entry(currentX, (float) (Math.random() * 40) + 30f), 0);
//            if (set.getEntryCount() > 20) {
//                set.removeFirst();
//            }
//            data.notifyDataChanged();
//            mLineChart.notifyDataSetChanged();
//            mLineChart.moveViewToX(data.getEntryCount());
//        }
//    }
//
//    private LineDataSet createSet() {
//        LineDataSet set = new LineDataSet(null, "温度");
//        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        set.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set.setColor(Color.WHITE);
//        set.setLineWidth(2f);
//        set.setCircleRadius(4f);
//        set.setFillAlpha(65);
//        set.setFillColor(ColorTemplate.getHoloBlue());
//        set.setHighLightColor(Color.rgb(244, 117, 117));
//        set.setValueTextColor(Color.WHITE);
//        set.setValueTextSize(9f);
//        set.setDrawValues(false);
//        return set;
//    }

    private void feedMultiple() {

    }

    @OnClick(R.id.start_mock)
    public void startMOck() {
//        Subscription subscription = HttpMethods.getInstance().getService(MovieService.class)
//                .getTopMovie(1, 12)
//                .compose(RxHelper.io_main())
//                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS)))
//                .repeatWhen(completed -> completed.delay(5, TimeUnit.SECONDS))
//                .subscribe((r) -> {
//                    Log.d(LOG_TAG,r.toString());
//                },(e) -> {
//                    e.printStackTrace();
//                },() -> {
//                    Log.d(LOG_TAG,"completed");
//                });

        //假数据
        ChartTypeEntity chartTypeEntity = new ChartTypeEntity();
        chartTypeEntity.setField("temperature");
        ChartValueEntity chartValueEntity = new ChartValueEntity();
        chartValueEntity.setStamp("2016-12-30 14:00:00");
        chartValueEntity.setValue("46");
        List<ChartValueEntity> items = new ArrayList<>();
        items.add(chartValueEntity);
        List<HttpResult<ChartTypeEntity, ChartValueEntity>> list = new ArrayList<>();
        HttpResult<ChartTypeEntity, ChartValueEntity> result = new HttpResult<>();
        result.setCode("0");
        result.setMessage("获取数据成功");
        result.setItem(chartTypeEntity);
        result.setItems(items);
        String chartType = result.getItem().getField();
        Stream.of(result.getItems()).map((r) -> {
            return new ChartValueDTO(r, chartType);
        }).map(r -> {
                r.setStamp(r.getStamp() - r.getBeginOfTime());
                return r;
            }).forEach(r -> {
                addToChart(r);
            });
        }




    private void addToChart(ChartValueDTO dto) {

        LineData lineData = mLineChart.getData();
        if (lineData != null) {
            String chartTypeLabel = ConstantChart.chartTypeAndDesc.get(dto.getChartType());
            ILineDataSet dataSet = lineData.getDataSetByLabel(chartTypeLabel, false);
            if (dataSet == null) {
                dataSet = createSet(chartTypeLabel);
                lineData.addDataSet(dataSet);
            }
            lineData.addEntry(new Entry(dto.getStamp(),dto.getValue()),ConstantChart.chartTypeAndIndex.get(dto.getChartType()));
            lineData.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            //当需要一定得时候调用
//            mLineChart.moveViewToX(lineData.getEntryCount());
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
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;

    }

    private void handleChartData(HttpResult<ChartTypeEntity, ChartValueEntity> result) {

    }

    @OnClick(R.id.add_mock)
    public void addMock() {
    }
}
