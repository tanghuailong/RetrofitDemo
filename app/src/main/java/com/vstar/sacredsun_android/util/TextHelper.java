package com.vstar.sacredsun_android.util;

/**
 * Created by tanghuailong on 2017/1/19.
 */

import android.text.TextUtils;

/**
 * 处理文字工具类
 */
public class TextHelper {
    public  static float strTransFromFloat(String str){

        if(TextUtils.isEmpty(str)) {
            return 0f;
        }else {
            return Float.parseFloat(str);
        }
    }
}
