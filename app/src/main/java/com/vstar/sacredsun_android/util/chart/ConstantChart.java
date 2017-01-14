package com.vstar.sacredsun_android.util.chart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanghuailong on 2017/1/14.
 */

public class ConstantChart {

    public static final long SECOND_OF_DAY = 84000;

    public static final Map<String, String> chartTypeAndDesc = new HashMap<String, String>() {{
        put("temperature1", "温度一");
        put("temperature2", "温度二");
        put("humidity1", "湿度一");
        put("humidity2", "湿度二");
    }};
    public static final Map<String,Integer> chartTypeAndIndex = new HashMap<String,Integer>(){{
        put("temperature1",0);
        put("temperature2",1);
        put("humidity1", 2);
        put("humidity2", 3);
    }};

}
