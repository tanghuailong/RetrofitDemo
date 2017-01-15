package com.vstar.sacredsun_android.util.chart;

import org.threeten.bp.LocalDate;
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
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINA);
    private static final DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("HH:mm:ss");
    //给定日期转换为秒数
    public static long dateTimeTransformSecond(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
    //给定str 转换为日期
    public static LocalDateTime strTransfromLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr,formatter);
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
    //把秒数转换为localDateTime
    public static LocalDateTime secondTransformLocalDateTime(long second) {
       return LocalDateTime.ofEpochSecond(second,0,null);
    }
    //把秒数转换为 localTime
    public static LocalTime secondTransformLocalTime(long second) {
        return LocalTime.ofSecondOfDay(second);
    }
    //把localDate 转换为 str
    public static String localDateTransformStr(LocalDate localDate) {
        return localDate.format(formatter2);
    }
    //把 秒数转换为 时间格式 HH:mm:ss
    public static String secondTransformStr(long localTime) {
        return LocalTime.ofSecondOfDay(localTime).format(formatter3);
    }
}
