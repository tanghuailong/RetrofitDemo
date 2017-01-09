package com.vstar.sacredsun_android.util;

import com.vstar.sacredsun_android.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanghuailong on 2017/1/9.
 */

public class StatusMap {
    public  static final Map<String, Integer> statusAndView = new HashMap<String,Integer>() {{
        put("idle", R.drawable.status_bar_idle);
        put("preheating",R.drawable.status_bar_preheating);
        put("presolidify",R.drawable.status_bar_presolidify);
        put("solidify",R.drawable.status_bar_solidify);
        put("dry",R.drawable.status_bar_dry);
        put("warning",R.drawable.status_bar_warning);
    }};
}
