package com.vstar.sacredsun_android.util.chart;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tanghuailong on 2017/1/14.
 */

public class ConstantChart {

    public static final long SECOND_OF_DAY = 84000;

 public static final Map<String, String> chartTypeAndDesc = new TreeMap<String, String>() {{
        put("temperature1", "温度一");
        put("temperature2", "温度二");
        put("humidity1", "湿度一");
        put("humidity2", "湿度二");
        put("temperature","设定温度");
        put("humidity","设定湿度");
    }};
    public static final Map<String,Integer> chartTypeAndIndex = new HashMap<String,Integer>(){{
        put("temperature1",0);
        put("temperature2",1);
        put("humidity1", 2);
        put("humidity2", 3);
        put("temperature",4);
        put("humidity",5);
    }};

    /**
     * @see <a href="https://material.io/guidelines/style/color.html#color-color-palette"></a>
     */
    public static final Map<String,Integer> abbreAndColor = new HashMap<String,Integer>(){{
        put("温度一", Color.parseColor("#FF80AB"));
        put("温度二",Color.parseColor("#FF4081"));
        put("湿度一",Color.parseColor("#EA80FC"));
        put("湿度二",Color.parseColor("#D500F9"));
        put("设定温度",Color.parseColor("#AEEA00"));
        put("设定湿度",Color.parseColor("#EEFF41"));
    }};

}
