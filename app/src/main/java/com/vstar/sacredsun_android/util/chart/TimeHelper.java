package com.vstar.sacredsun_android.util.chart;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by tanghuailong on 2017/1/13.
 */

/**
 * 处理时间的工具类
 */
public class TimeHelper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    //给定日期转换为秒数
    public static long dateTimeTransformSecond(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
    //给定str 转换为日期
    public static LocalDateTime strTransfromLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr);
    }
    //把给定日期转换为秒数
    public static long strTransformSecond(String dateTimeStr) {
        return dateTimeTransformSecond(strTransfromLocalDateTime(dateTimeStr));
    }
    //取得给定日期的开始时间
    public static long strTransformStartSecond(String dateTimeStr) {
        return dateTimeTransformSecond(LocalDateTime.of(strTransfromLocalDateTime(dateTimeStr).toLocalDate(), LocalTime.MIDNIGHT));
    }
    //取得给定日期的开始时间
    public static long dateTimeTransformStartSecond(LocalDateTime localDateTime){
        return dateTimeTransformSecond(LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT));
    }

}
