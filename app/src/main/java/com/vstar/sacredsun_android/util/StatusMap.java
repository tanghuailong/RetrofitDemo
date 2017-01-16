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
//        put("preheating",R.drawable.status_bar_preheating);
        put("PRE_SOLIDIFICATION",R.drawable.status_bar_presolidify);
        put("SOLIDIFICATION",R.drawable.status_bar_solidify);
        put("DRYING",R.drawable.status_bar_dry);
        put("WARNING",R.drawable.status_bar_warning);
    }};
}
