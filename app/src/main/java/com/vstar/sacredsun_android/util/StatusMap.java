package com.vstar.sacredsun_android.util;

import com.vstar.sacredsun_android.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanghuailong on 2017/1/9.
 */

public class StatusMap {
    public  static final Map<String, Integer> statusAndView = new HashMap<String,Integer>() {{
        put("UNUSED", R.drawable.status_bar_idle);
        put("ENDING",R.drawable.status_bar_preheating);
        put("PRE_SOLIDIFICATION",R.drawable.status_bar_presolidify);
        put("SOLIDIFICATION",R.drawable.status_bar_solidify);
        put("DRYING",R.drawable.status_bar_dry);
        put("WARNING",R.drawable.status_bar_warning);
    }};

    public static final Map<String,String> abbreAndDesc = new HashMap<String,String>(){{
        put("UNUSED","闲置");
        put("ENDING","结束");
        put("PRE_SOLIDIFICATION","预固化");
        put("SOLIDIFICATION","固化");
        put("DRYING","干燥");
        put("WARNING","告警");
    }};

}
