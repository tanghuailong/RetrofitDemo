package com.vstar.sacredsun_android.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.daimajia.numberprogressbar.NumberProgressBar;
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
import com.vstar.sacredsun_android.entity.DeviceDetailEntity;
import com.vstar.sacredsun_android.service.SacredsunService;
import com.vstar.sacredsun_android.util.SPHelper;
import com.vstar.sacredsun_android.util.StatusMap;
import com.vstar.sacredsun_android.util.TextHelper;
import com.vstar.sacredsun_android.util.chart.ConstantChart;
import com.vstar.sacredsun_android.util.chart.HourAxisValueFormatter;
import com.vstar.sacredsun_android.util.chart.MyMarkerView;
import com.vstar.sacredsun_android.util.chart.TimeHelper;
import com.vstar.sacredsun_android.util.rest.HttpMethods;
import com.vstar.sacredsun_android.util.rxjava.RxHelper;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;

/**
 * Created by tanghuailong on 2017/1/10.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_chart)
    LineChart mLineChart;
    @BindView(R.id.device_code)
    TextView deviceCode;
    @BindView(R.id.detail_product_model)
    TextView detailProductModel;
    @BindView(R.id.detail_order_num_value)
    TextView detailOrderNumValue;
    @BindView(R.id.detail_run_value)
    TextView detailRunValue;
    @BindView(R.id.detail_product_stage_title)
    TextView detailProductStageTitle;
    @BindView(R.id.detail_program_num_value)
    TextView detailProgramNumValue;
    @BindView(R.id.first_setting)
    TextView firstSetting;
    @BindView(R.id.first_actual)
    TextView firstActual;
    @BindView(R.id.second_setting)
    TextView secondSetting;
    @BindView(R.id.second_actual)
    TextView secondActual;
    @BindView(R.id.third_setting)
    TextView thirdSetting;
    @BindView(R.id.third_actual)
    TextView thirdActual;
    @BindView(R.id.four_setting)
    TextView fourSetting;
    @BindView(R.id.four_actual)
    TextView fourActual;
    @BindView(R.id.detail_left_img)
    ImageView detailLeftImg;
    @BindView(R.id.detail_right_img)
    ImageView detailRightImg;
    @BindView(R.id.detail_reduce_temperate_value)
    TextView detailReduceTemperateValue;
    @BindView(R.id.detail_stream_heating_value)
    TextView detailStreamHeatingValue;
    @BindView(R.id.detail_exhaust_humidity_value)
    TextView detailExhaustHumidityValue;
    @BindView(R.id.detail_stream_humidity_value)
    TextView deatailStreamHumidityValue;
    @BindView(R.id.detail_cycle_blower_value)
    TextView detailCycleBlowerValue;
    @BindView(R.id.detail_water_valve)
    TextView detailWaterValve;
    @BindView(R.id.detail_reduce_humidity_img)
    ImageView reduceTemperatureImg;
    @BindView(R.id.detail_exhaust_humidity_img)
    ImageView exhaustHumidityImg;
    @BindView(R.id.detail_cycle_blower_img)
    ImageView fanImg;
    @BindView(R.id.detail_stream_heating_img)
    ImageView streamHeatingImg;
    @BindView(R.id.detail_stream_humidity_img)
    ImageView streamHumidityImg;
    @BindView(R.id.detail_water_valve_img)
    ImageView waterValveImg;
    @BindView(R.id.progress_bar_one)
    NumberProgressBar progressBarOne;
    @BindView(R.id.progress_bar_two)
    NumberProgressBar progressBarTwo;
    @BindView(R.id.compatSwitch)
    SwitchCompat compatSwitch;
    @BindView(R.id.kanban_name)
    TextView kanBanName;
    @BindView(R.id.total_last_time)
    TextView totalLastTime;
    @BindView(R.id.stage_last_time)
    TextView stageLastTime;



    private static final String LOG_TAG = "DetailActivity";
    private static final String TAG = "assetsCode";

    private Subscription presetSubscription = null;
    private Subscription todaySubscription = null;
    private Subscription deviceSubscription = null;
    private String beginStamp = "";
    private String assertsCode = "";

    //现在的时间
    private LocalDateTime currentTime = LocalDateTime.now();
    //当前处在哪个小时
    private int currentHour = LocalTime.now().getHour();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //看板名称
        String kanbenNameStr = (String) SPHelper.get(DetailActivity.this,getString(R.string.KANBAN_NAME),"");
        kanBanName.setText(kanbenNameStr);

        Intent intent = getIntent();
        assertsCode = intent.getStringExtra(TAG);

        initChart(mLineChart);
        openSubscribe();
    }

    private void initChart(LineChart lineChart) {

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
        MyMarkerView myMarkerView = new MyMarkerView(DetailActivity.this, R.layout.custom_marker_view);
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
        xl.setLabelCount(10);
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


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(LOG_TAG, "onStart");
//        openSubscribe();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(LOG_TAG, "onStop");
//        closeSubscribe();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestory");
        closeSubscribe();
    }

    private void openSubscribe() {
        if (deviceSubscription == null || deviceSubscription.isUnsubscribed()) {
            if (!TextUtils.isEmpty(assertsCode)) {
                initDeviceDetailInfo();
            }
        }
        if (todaySubscription == null || todaySubscription.isUnsubscribed()) {
            if (!TextUtils.isEmpty(assertsCode)) {
                initTodayChart();
            }
        }
        if (todaySubscription == null || todaySubscription.isUnsubscribed()) {
            if (!TextUtils.isEmpty(assertsCode)) {
                initTodaySettingValue();
            }
        }
    }

    private void openTodaySubscribe() {
        if (todaySubscription == null || todaySubscription.isUnsubscribed()) {
            if (!TextUtils.isEmpty(assertsCode)) {
                initTodayChart();
            }
        }
        if (presetSubscription == null || presetSubscription.isUnsubscribed()) {
            if (!TextUtils.isEmpty(assertsCode)) {
                initTodaySettingValue();
            }
        }
    }

    private void closeSubscribe() {
        if (todaySubscription != null && !todaySubscription.isUnsubscribed()) {
            todaySubscription.unsubscribe();
        }
        if (deviceSubscription != null && !deviceSubscription.isUnsubscribed()) {
            deviceSubscription.unsubscribe();
        }
        if (presetSubscription != null && !presetSubscription.isUnsubscribed()) {
            presetSubscription.unsubscribe();
        }
    }

    private void closeTodaySubscribe() {
        if (todaySubscription != null && !todaySubscription.isUnsubscribed()) {
            todaySubscription.unsubscribe();
        }
        if (presetSubscription != null && !presetSubscription.isUnsubscribed()) {
            presetSubscription.unsubscribe();
        }
    }

    private void initDeviceDetailInfo() {

        deviceSubscription = HttpMethods.getInstance().getService(SacredsunService.class)
                .getDeviceDetailData(assertsCode)
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.SECONDS)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    initDetailPage(r.getItem());
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "onCompleted");
                });
    }

    private void initDetailPage(DeviceDetailEntity entity) {

        deviceCode.setText(entity.getAssetsCode());
        detailOrderNumValue.setText(Integer.toString(entity.getOrderQuantity()));
        detailProductModel.setText(entity.getMaterialCode());
        detailRunValue.setText(StatusMap.abbreAndDesc.get(entity.getAssetsState().name()));
        detailRunValue.setBackgroundResource(StatusMap.statusAndView.get(entity.getAssetsState().name()));
        firstSetting.setText(entity.getTemperature());
        firstActual.setText(entity.getTemperature1());
        secondSetting.setText(entity.getTemperature());
        secondActual.setText(entity.getTemperature2());
        thirdSetting.setText(entity.getHumidity());
        thirdActual.setText(entity.getHumidity1());
        fourSetting.setText(entity.getHumidity());
        fourActual.setText(entity.getHumidity2());
        detailProductStageTitle.setText(StatusMap.abbreAndDesc.get(entity.getAssetsState().name()) + " " + entity.getProductionStage() + "阶段");
        detailProgramNumValue.setText(entity.getProgramNumber());
        //设置剩余时间
        totalLastTime.setText(TextHelper.minuteTransFormTimeStr(entity.getRemainingTime()));
        stageLastTime.setText(TextHelper.minuteTransFormTimeStr(entity.getStageRemainingTime()));
        progressBarOne.setMax(entity.getTotalTime());
        progressBarOne.setProgress(entity.getTotalTime() == 0 ? 0 : entity.getRemainingTime());
        progressBarTwo.setMax(entity.getStageTotalTime());
        progressBarTwo.setProgress(entity.getStageTotalTime() == 0 ? 0 : entity.getStageRemainingTime());
        //警报大于零都为开，否则为关
        compatSwitch.setChecked(entity.getIsWarning() > 0);

        setTextStatusValve(entity.getCirculatingFan(), detailCycleBlowerValue);
        setTextStatusValve(entity.getWaterValve(), detailWaterValve);
        setTextStatusValve(entity.getCoolingDamper(), detailReduceTemperateValue);
        setTextStatusValve(entity.getHumidityDamper(), detailExhaustHumidityValue);
        setTextStatusValve(entity.getSteamHeating(), detailStreamHeatingValue);
        setTextStatusValve(entity.getStreamHumidity(), deatailStreamHumidityValue);

        setImgStatusValve(reduceTemperatureImg, entity.getCoolingDamper(), R.drawable.door_able, R.drawable.door_disable);
        setImgStatusValve(exhaustHumidityImg, entity.getHumidityDamper(), R.drawable.door_able, R.drawable.door_disable);
        setImgStatusValve(fanImg, entity.getCirculatingFan(), R.drawable.fan_able, R.drawable.fan_disable);
        setImgStatusValve(streamHeatingImg, entity.getStreamHumidity(), R.drawable.stream_heating, R.drawable.stream_disable);
        setImgStatusValve(streamHumidityImg, entity.getStreamHumidity(), R.drawable.steam_humidity, R.drawable.stream_disable);
        setImgStatusValve(waterValveImg, entity.getWaterValve(), R.drawable.water_able, R.drawable.water_disable);
    }

    @OnClick(R.id.detail_left_img)
    public void previousChart() {

        closeTodaySubscribe();
        currentHour = currentHour - 1;

        redrawChart(mLineChart, currentHour);
        LocalDateTime previousBegin = LocalDateTime.now().withHour(currentHour).withMinute(0).withSecond(0);
        LocalDateTime previousEnd = previousBegin.plusHours(1);

        HttpMethods.getInstance().getService(SacredsunService.class)
                .getLineChartData(assertsCode, TimeHelper.localDateTimeTransFormStr(previousBegin), TimeHelper.localDateTimeTransFormStr(previousEnd))
                .compose(RxHelper.io_main())
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    drawLineChart(r.getItems());
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "completed");
                });
    }

    @OnClick(R.id.detail_right_img)
    public void nextChart() {

        //查看增加一个小时之后，是否超过现如今的时间
        if (currentHour + 1 > LocalTime.now().getHour()) {
            return;
        }
        closeTodaySubscribe();
        currentHour = currentHour + 1;
        redrawChart(mLineChart, currentHour);
        if (currentHour == LocalTime.now().getHour()) {
            openTodaySubscribe();
        } else {
            LocalDateTime previousBegin = LocalDateTime.now().withHour(currentHour).withMinute(0).withSecond(0);
            LocalDateTime previousEnd = previousBegin.plusHours(1);

            HttpMethods.getInstance().getService(SacredsunService.class)
                    .getLineChartData(assertsCode, TimeHelper.localDateTimeTransFormStr(previousBegin), TimeHelper.localDateTimeTransFormStr(previousEnd))
                    .compose(RxHelper.io_main())
                    .subscribe((r) -> {
                        Log.d(LOG_TAG, "onNext");
                        drawLineChart(r.getItems());
                    }, (e) -> {
                        e.printStackTrace();
                    }, () -> {
                        Log.d(LOG_TAG, "completed");
                    });
        }
    }


    private void initTodayChart() {

        Map<String, String> beginAndEnd = TimeHelper.getBeginAndEndTime(LocalDateTime.now());

        HttpMethods.getInstance().getService(SacredsunService.class)
                .getLineChartData(assertsCode, beginAndEnd.get("begin"), beginAndEnd.get("end"))
                .compose(RxHelper.io_main())
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    if (!r.getItems().isEmpty()) {
                        int lastIndex = r.getItems().size();

                        drawLineChart(r.getItems());

                        refreshTodayChart(r.getItems().get(lastIndex - 1).getStamp());
                    }
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "completed");
                });

    }

    /**
     * 初始化 温度的设定值
     */
    private void initTodaySettingValue() {
        Map<String, String> beginAndEnd = TimeHelper.getBeginAndEndTime(LocalDateTime.now());
        presetSubscription = HttpMethods.getInstance()
                .getService(SacredsunService.class)
                .getPresetValue(assertsCode, beginAndEnd.get("begin"), beginAndEnd.get("end"))
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.MINUTES)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.MINUTES))
                .subscribe((r) -> {
                    if (!r.getItems().isEmpty()) {
                        resetSettingChart(mLineChart);
                        drawLineChart(r.getItems());
                    }
                }, (e) -> {

                });
    }

    private void refreshTodayChart(String stamp) {

        beginStamp = stamp;

        todaySubscription = HttpMethods.getInstance().getService(SacredsunService.class)
                .getLineChartData(assertsCode, beginStamp, TimeHelper.getEndTime(beginStamp))
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(1, TimeUnit.MINUTES)))
                .repeatWhen(completed -> completed.delay(1, TimeUnit.MINUTES))
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    if (!r.getItems().isEmpty()) {
                        int lastIndex = r.getItems().size();
                        drawLineChart(r.getItems());
                        beginStamp = r.getItems().get(lastIndex - 1).getStamp();
                        //在需要的时候重新绘制
                        redrawChartWhenNeed(beginStamp, mLineChart);
                    }
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "onCompleted");
                });


    }

    private void resetSettingChart(LineChart lineChart) {
        LineData lineData = lineChart.getLineData();
        if (lineData != null) {
            //TODO 修改一下这个地方
            String labelT = ConstantChart.chartTypeAndDesc.get("temperature");
            String labelH = ConstantChart.chartTypeAndDesc.get("humidity");
            ILineDataSet dataSetT = lineData.getDataSetByLabel(labelT, false);
            ILineDataSet dataSetH = lineData.getDataSetByLabel(labelH, false);

            if(dataSetH != null) {
                dataSetH.clear();
            }
            if(dataSetT != null) {
                dataSetT.clear();
            }
            lineChart.invalidate();
        }
    }

    //依据数据绘制折线图
    private void drawLineChart(List<ChartValueEntity> list) {
        Stream.of(list).map(origin -> transformChartPoint(origin))
                .forEach(result -> addToChart(mLineChart, result));
    }

    private void addToChart(LineChart lineChart, ChartValueDTO dto) {

        LineData lineData = lineChart.getData();
        if (lineData != null) {
            String label = ConstantChart.chartTypeAndDesc.get(dto.getType());
            ILineDataSet dataSet = lineData.getDataSetByLabel(label, false);
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

    private ChartValueDTO transformChartPoint(ChartValueEntity entity) {
        //获得当前日期
        LocalDateTime nowTime = TimeHelper.strTransfromLocalDateTime(entity.getStamp());
        //获得当前时间的秒数(只包括分钟和秒)
        long currentSecond = (long) (nowTime.getMinute() * 60 + nowTime.getSecond());
        ChartValueDTO dto = new ChartValueDTO(currentSecond, TextHelper.strTransFromFloat(entity.getValue()), entity.getField());
        return dto;
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

    private void redrawChartWhenNeed(String beginStamp, LineChart lineChart) {

        LocalDateTime localDateTime = TimeHelper.strTransfromLocalDateTime(beginStamp);
        //需要检测时间是否超过当前小时
        if (localDateTime.getHour() != currentHour) {
            currentHour = localDateTime.getHour();
            redrawChart(lineChart, currentHour);
        }
    }

    private void redrawChart(LineChart lineChart, int mHours) {
        lineChart.clearValues();
        HourAxisValueFormatter newFormatter = new HourAxisValueFormatter(mHours);
        XAxis xl = lineChart.getXAxis();
        xl.setValueFormatter(newFormatter);
        lineChart.invalidate();
    }

    /**
     * 设置阀门状态
     */
    private void setTextStatusValve(String status, TextView mText) {
        if (status.trim().equals("0")) {
            mText.setText(getString(R.string.status_valve_disable));
        } else if (status.trim().equals("1")) {
            mText.setText(getString(R.string.status_valve_able));
        }
    }

    /**
     * 设置阀门状态
     *
     * @param status
     * @param imageView
     * @param ableRes
     * @param disableRes
     */
    private void setImgStatusValve(ImageView imageView, String status, int ableRes, int disableRes) {
        if (status.trim().equals("0")) {
            imageView.setImageResource(disableRes);
        } else if (status.trim().equals("1")) {
            imageView.setImageResource(ableRes);
        }
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick(R.id.compatSwitch)
    public void clickSwitchButton(View view) {
        boolean isWarning = compatSwitch.isChecked();
        int signal = (!isWarning) ? 0 : 1;
        HttpMethods.getInstance().getService(SacredsunService.class)
                .controlWarningSwitch(assertsCode, String.valueOf(signal))
                .compose(RxHelper.io_main())
                .subscribe((r) -> {
                    Log.d(LOG_TAG,"clickSwitchButton");
                }, (e) -> {
                    Log.e(LOG_TAG, "some error happen in switch warning", e);
                    Toast.makeText(DetailActivity.this, "控制警报发生某些错误", Toast.LENGTH_SHORT).show();
                });

    }
}
