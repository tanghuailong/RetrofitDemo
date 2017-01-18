package com.vstar.sacredsun_android.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
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
import com.vstar.sacredsun_android.util.StatusMap;
import com.vstar.sacredsun_android.util.chart.ConstantChart;
import com.vstar.sacredsun_android.util.chart.HourAxisValueFormatter;
import com.vstar.sacredsun_android.util.chart.MyMarkerView;
import com.vstar.sacredsun_android.util.rest.HttpMethods;
import com.vstar.sacredsun_android.util.rxjava.RxHelper;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;
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
    LineChart mLineChart;                      //折线图
    @BindView(R.id.device_code)
    TextView deviceCode;                       //设备编号
    @BindView(R.id.detail_product_model)
    TextView detailProductModel;               //产品型号
    @BindView(R.id.detail_order_num_value)
    TextView detailOrderNumValue;              //订单数量
    @BindView(R.id.stove_left_time)
    TextView stoveLeftTime;                   //剩余时间
    @BindView(R.id.detail_run_value)
    TextView detailRunValue;                  //运行状态
    @BindView(R.id.detail_product_stage_title)
    TextView detailProductStageTitle;         //生产阶段
    @BindView(R.id.detail_program_num_value)
    TextView detailProgramNumValue;           //程序号
    @BindView(R.id.first_setting)
    TextView firstSetting;                    //温度一设定值
    @BindView(R.id.first_actual)
    TextView firstActual;                     //温度一实际值
    @BindView(R.id.second_setting)
    TextView secondSetting;                   //温度二设定值
    @BindView(R.id.second_actual)
    TextView secondActual;                    //温度二实际值
    @BindView(R.id.third_setting)
    TextView thirdSetting;                    //湿度一设定值
    @BindView(R.id.third_actual)
    TextView thirdActual;                     //湿度一实际值
    @BindView(R.id.four_setting)
    TextView fourSetting;                     //湿度二的设定值
    @BindView(R.id.four_actual)
    TextView fourActual;                      //湿度二的实际值
    @BindView(R.id.detail_left_img)
    ImageView detailLeftImg;                  //向前
    @BindView(R.id.detail_right_img)
    ImageView detailRightImg;                 //向后
    @BindView(R.id.detail_reduce_temperate_value)
    TextView detailReduceTemperateValue;      //降温风门
    @BindView(R.id.detail_stream_heating_value)
    TextView detailStreamHeatingValue;        //蒸汽加热
    @BindView(R.id.detail_exhaust_humidity_value)
    TextView detailExhaustHumidityValue;      //排湿风门
    @BindView(R.id.detail_stream_humidity_value)
    TextView deatailStreamHumidityValue;      //蒸汽加湿
    @BindView(R.id.detail_cycle_blower_value)
    TextView detailCycleBlowerValue;          //循环风机
    @BindView(R.id.detail_water_valve)
    TextView detailWaterValve;                //雾化水阀


    private static final String LOG_TAG = "DetailActivity";
    private static final String TAG = "assetsCode";

    private Subscription presetSubscription = null;
    private Subscription todaySubscription = null;
    private Subscription deviceSubscription = null;
    private String stamp = "";
    private String assertsCode = "";
    private LocalDate currentDate = LocalDate.now();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_stove);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        assertsCode = intent.getStringExtra(TAG);

        initChart(mLineChart);

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

        HourAxisValueFormatter formatter = new HourAxisValueFormatter();
        LimitLine temperateLimited = new LimitLine(50f, "设定温度");
        temperateLimited.setLineColor(Color.RED);
        temperateLimited.setLineWidth(2f);
        temperateLimited.setTextColor(Color.WHITE);
        temperateLimited.setTextSize(10f);
        LimitLine humidityLimited = new LimitLine(60f, "设定湿度");
        humidityLimited.setLineColor(Color.RED);
        humidityLimited.setLineWidth(2f);
        humidityLimited.setTextColor(Color.WHITE);
        humidityLimited.setTextSize(10f);

        XAxis xl = lineChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.WHITE);
        xl.setValueFormatter(formatter);
        xl.setAxisMinimum(0f);
        xl.setAxisMaximum(86400f);
        xl.setLabelCount(10);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawLabels(true);
        xl.setDrawAxisLine(true);
        xl.setEnabled(true);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.addLimitLine(temperateLimited);
        leftAxis.addLimitLine(humidityLimited);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setMinWidth(0f);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //当为空或者不为空并且未订阅的时候，开始订阅
        if (todaySubscription == null || todaySubscription.isUnsubscribed()) {
            if (!TextUtils.isEmpty(assertsCode)) {
                initTodayChart();
            }
        }
        //当为空或者不为空并且未订阅的时候,开始订阅
        if(presetSubscription == null || presetSubscription.isUnsubscribed()) {
            drawPresetInChart();
        }

        if(deviceSubscription == null || deviceSubscription.isUnsubscribed()) {
            initDeviceDetailInfo();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解除订阅
        if (todaySubscription != null && !todaySubscription.isUnsubscribed()) {
            todaySubscription.unsubscribe();
        }
        if (presetSubscription != null && !presetSubscription.isUnsubscribed()) {
            presetSubscription.unsubscribe();
        }
        if (deviceSubscription != null && !deviceSubscription.isUnsubscribed()) {
            deviceSubscription.unsubscribe();
        }

    }

    private void initDeviceDetailInfo() {

       deviceSubscription =  HttpMethods.getInstance().getService(SacredsunService.class)
                .getDeviceDetailData(assertsCode)
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.SECONDS)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS))
                .subscribe((r) -> {
                    Log.d(LOG_TAG,"onNext");
                    initDetailPage(r.getItem());
                },(e) -> {
                    e.printStackTrace();
                },() ->{
                    Log.d(LOG_TAG,"onCompleted");
                });
    }

    //初始化设备信息的界面
    private void initDetailPage(DeviceDetailEntity entity) {

        deviceCode.setText(entity.getAssetsCode());
        detailOrderNumValue.setText(entity.getOrderQuantity());
        detailProductModel.setText(entity.getMaterialCode());
        stoveLeftTime.setText(entity.getResidualTime());
        detailRunValue.setText(StatusMap.abbreAndDesc.get(entity.getAssetsState().name()));
        firstSetting.setText(entity.getTemperature());
        firstActual.setText(entity.getTemperature1());
        secondSetting.setText(entity.getTemperature());
        secondActual.setText(entity.getTemperature2());
        thirdSetting.setText(entity.getHumidity());
        thirdActual.setText(entity.getHumidity1());
        fourSetting.setText(entity.getHumidity());
        fourActual.setText(entity.getHumidity2());
        detailProductStageTitle.setText(entity.getProductionStage() + "阶段");
//        detailProductStageTitle.setText(entity.getAssetsState() +" "+entity.getProductionStage() +"阶段");
        detailProgramNumValue.setText(entity.getProgramNumber());
        detailCycleBlowerValue.setText(entity.getCirculatingFan());
        detailWaterValve.setText(entity.getWaterValve());
        detailReduceTemperateValue.setText(entity.getCoolingDamper());
        detailExhaustHumidityValue.setText(entity.getHumidityDamper());
        detailStreamHeatingValue.setText(entity.getSteamHeating());
        deatailStreamHumidityValue.setText(entity.getStreamHumidity());
    }

    //点击查看前一天图表数据
    @OnClick(R.id.detail_left_img)
    public void previousChart() {
        //获得当前日期
        currentDate = currentDate.minus(1,ChronoUnit.DAYS);
        HttpMethods.getInstance().getService(SacredsunService.class)
                .getChartDate(assertsCode, currentDate.toString(), "")
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

    //查看后一天图表数据
    @OnClick(R.id.detail_right_img)
    public void nextChart() {

        currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        if(currentDate.equals(LocalDate.now())) {
            initTodayChart();
        }else {
            HttpMethods.getInstance().getService(SacredsunService.class)
                    .getChartDate(assertsCode, currentDate.toString(), "")
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

        //获取今日的数据
        HttpMethods.getInstance().getService(SacredsunService.class)
                .getChartDate(assertsCode,LocalDate.now().toString(),"")
                .compose(RxHelper.io_main())
                .subscribe((r) -> {
                    Log.d(LOG_TAG,"onNext");
                    if(!r.getItems().isEmpty()) {
                        int lastIndex = r.getItems().size();
                        //绘制今日的数据
                        drawLineChart(r.getItems());
                        //刷新实时的数据
                        refreshTodayChart(r.getItems().get(lastIndex).getStamp());
                    }
                },(e) -> {
                    e.printStackTrace();
                },() -> {
                    Log.d(LOG_TAG,"completed");
                });

    }

    private void refreshTodayChart(String realTime) {

        stamp = realTime;

        if(todaySubscription != null && !todaySubscription.isUnsubscribed()) {
            todaySubscription.unsubscribe();
        }

        todaySubscription = HttpMethods.getInstance().getService(SacredsunService.class)
                .getChartDate(assertsCode,LocalDate.now().toString(),stamp)
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS)))
                .repeatWhen(completed -> completed.delay(5, TimeUnit.SECONDS))
                .subscribe((r) -> {
                    Log.d(LOG_TAG,"onNext");
                    if(!r.getItems().isEmpty()) {
                        int lastIndex = r.getItems().size();
                        stamp = r.getItems().get(lastIndex).getStamp();
                        drawLineChart(r.getItems());
                    }
                },(e) -> {
                    e.printStackTrace();
                },() -> {
                    Log.d(LOG_TAG,"onCompleted");
                });

    }

    //在折线图上开始绘制设定值
    private void drawPresetInChart() {

        presetSubscription = HttpMethods.getInstance().getService(SacredsunService.class)
                .getPresetValue(assertsCode, LocalDate.now().toString())
                .compose(RxHelper.io_main())
                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(10, TimeUnit.MINUTES)))
                .repeatWhen(completed -> completed.delay(10, TimeUnit.MINUTES))
                .subscribe((r) -> {
                    Log.d(LOG_TAG, "onNext");
                    addPresetValueToChart(r.getItems());
                }, (e) -> {
                    e.printStackTrace();
                }, () -> {
                    Log.d(LOG_TAG, "completed");
                });
    }

    //转换数据，并且开始绘制折线图
    private void drawLineChart(List<ChartValueEntity> valueList) {

        Stream.of(valueList).map(l -> new ChartValueDTO(l))
                .forEach(dto -> {
                    addToChart(dto);
                });

    }

    private void addPresetValueToChart(List<ChartValueEntity> valueList) {

       List<ChartValueDTO> dtoList =  Stream.of(valueList)
               .map(l -> new ChartValueDTO(l))
               .collect(Collectors.toList());

        LineData lineData = mLineChart.getData();
        if(lineData != null) {
            ILineDataSet dataSet1 = lineData.getDataSetByIndex(4);
            ILineDataSet dataSet2 = lineData.getDataSetByIndex(5);
            if (dataSet1 == null) {
                dataSet1 = createSet(ConstantChart.chartTypeAndDesc.get("temperature"));
                lineData.addDataSet(dataSet1);
            }
            if(dataSet2 == null) {
                dataSet2 = createSet(ConstantChart.chartTypeAndDesc.get("humidity"));
                lineData.addDataSet(dataSet2);
            }
            dataSet1.clear();
            dataSet2.clear();
            Stream.of(dtoList).forEach(dto -> addToChart(dto));


        }
    }

    //往折线图上添加点
    private void addToChart(ChartValueDTO dto) {

        LineData lineData = mLineChart.getData();
        if (lineData != null) {
            int indexOfDateSet = ConstantChart.chartTypeAndIndex.get(dto.getChartType());
            ILineDataSet dataSet = lineData.getDataSetByIndex(indexOfDateSet);
            if (dataSet == null) {
                dataSet = createSet(ConstantChart.chartTypeAndDesc.get(dto.getChartType()));
                lineData.addDataSet(dataSet);
            }
            lineData.addEntry(new Entry(dto.getStamp(), dto.getValue()), indexOfDateSet);
            lineData.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.moveViewToX(lineData.getEntryCount());
        }
    }

    //创建折线
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

}
