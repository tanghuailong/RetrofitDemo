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
import com.vstar.sacredsun_android.entity.ChartTypeEntity;
import com.vstar.sacredsun_android.entity.ChartValueEntity;
import com.vstar.sacredsun_android.entity.DeviceDetailEntity;
import com.vstar.sacredsun_android.entity.HttpResult;
import com.vstar.sacredsun_android.service.SacredsunService;
import com.vstar.sacredsun_android.util.chart.ConstantChart;
import com.vstar.sacredsun_android.util.chart.HourAxisValueFormatter;
import com.vstar.sacredsun_android.util.chart.MyMarkerView;
import com.vstar.sacredsun_android.util.chart.TimeHelper;
import com.vstar.sacredsun_android.util.rest.HttpMethods;
import com.vstar.sacredsun_android.util.rxjava.RxHelper;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
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

    private LocalDate periorDate = null;
    private Subscription subscription;
    private String assertsCode = "";

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
        MyMarkerView myMarkerView = new MyMarkerView(DetailActivity.this,R.layout.custom_marker_view);
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
        LimitLine humidityLimited = new LimitLine(60f,"设定湿度");
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
        if (subscription == null || subscription.isUnsubscribed()) {
            if(!TextUtils.isEmpty(assertsCode)) {
                //轮询获取数据
                subscription = HttpMethods.getInstance().getService(SacredsunService.class)
                        .getDeviceDetailData(assertsCode)
                        .compose(RxHelper.io_main())
                        .retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS)))
                        .repeatWhen(completed -> completed.delay(5, TimeUnit.SECONDS))
                        .subscribe((r) -> {
                            initDetailPage(r.getItem());
                        },(e) -> {
                            e.printStackTrace();
                        },() -> {
                            //通知数据改变
                            Log.d(LOG_TAG,"completed");
                        });
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解除订阅
        if(subscription!=null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }

    //初始化设备信息的界面
    private void initDetailPage(DeviceDetailEntity entity) {

        deviceCode.setText(entity.getAssetsCode());
        detailOrderNumValue.setText(entity.getOrderQuantity());
        detailProductModel.setText(entity.getMaterialCode());
        stoveLeftTime.setText(entity.getResidualTime());
        detailRunValue.setText(entity.getAssetsState().name());
        firstSetting.setText(entity.getTemperature());
        firstActual.setText(entity.getTemperature1());
        secondSetting.setText(entity.getTemperature());
        secondActual.setText(entity.getTemperature2());
        thirdSetting.setText(entity.getHumidity());
        thirdActual.setText(entity.getHumidity1());
        fourSetting.setText(entity.getHumidity());
        fourActual.setText(entity.getHumidity2());
        detailProductStageTitle.setText(entity.getAssetsState() +" "+entity.getProductionStage() +"阶段");
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
    public void previousChart(){

    }
    //查看后一天图表数据
    @OnClick(R.id.detail_right_img)
    public void nextChart() {
        ChartTypeEntity chartTypeEntity = new ChartTypeEntity();
        chartTypeEntity.setField("temperature1");
        ChartValueEntity chartValueEntity = new ChartValueEntity();
        chartValueEntity.setStamp("2017-01-14 14:00:00");
        chartValueEntity.setValue("46");
        List<ChartValueEntity> items = new ArrayList<>();
        items.add(chartValueEntity);
        List<HttpResult<ChartTypeEntity, ChartValueEntity>> list = new ArrayList<>();
        HttpResult<ChartTypeEntity, ChartValueEntity> result = new HttpResult<>();
        result.setCode("0");
        result.setMessage("消息接收成功");
        result.setItem(chartTypeEntity);
        result.setItems(items);
        //TODO 并不是一个好的实现方式
        String chartType = result.getItem().getField();
        List<ChartValueEntity> valueList = result.getItems();
        if (valueList.size() > 0) {
            LocalDate localDate = TimeHelper.strTransfromLocalDateTime(valueList.get(0).getStamp()).toLocalDate();
            if (localDate.equals(periorDate)) {
                Stream.of(result.getItems()).map((r) -> {
                    return new ChartValueDTO(r, chartType);
                }).map(r -> {
                    r.setStamp(r.getStamp() - r.getBeginOfTime());
                    return r;
                }).forEach(r -> {
                    addToChart(r);
                });
            } else {
                //TODO 需要测试
                periorDate = localDate;
                LineData mData = mLineChart.getData();
                mData.removeDataSet(ConstantChart.chartTypeAndIndex.get(chartType));
                mData.notifyDataChanged();
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
            }
        }
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
            mLineChart.moveViewToX(lineData.getEntryCount());
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

}
