package com.vstar.sacredsun_android.util.chart;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tanghuailong on 2017/1/14.
 */

public class ConstantChart {

    public static final long SECOND_OF_DAY = 84000;

 public static final Map<String, String> chartTypeAndDesc = new TreeMap<String, String>() {{
        put("temperature1", "�¶�һ");
        put("temperature2", "�¶ȶ�");
        put("humidity1", "ʪ��һ");
        put("humidity2", "ʪ�ȶ�");
        put("temperature","�趨�¶�");
        put("humidity","�趨ʪ��");
    }};
    public static final Map<String,Integer> chartTypeAndIndex = new HashMap<String,Integer>(){{
        put("temperature1",0);
        put("temperature2",1);
        put("humidity1", 2);
        put("humidity2", 3);
        put("temperature",4);
        put("humidity",5);
    }};

}
