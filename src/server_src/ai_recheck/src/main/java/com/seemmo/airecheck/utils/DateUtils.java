package com.seemmo.airecheck.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.seemmo.airecheck.constant.BaseConstant;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends DateUtil {

    public static final Long ONE_HOUR_TIME_MILLIS = 1800000L;

    /**
     * 一天的总毫秒数
     */
    public static final long DAY_MILLISECONDS = 86400000L;

    /**
     * 年月日时分秒格式串
     */
    public static final String DAY_PATTERNS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日时分秒毫秒格式串（无特定格式）
     */
    public static final String DETAIL_DAY_PATTERNS = "yyyyMMddHHmmssSSS";


    /**
     * 返回天的毫秒数
     */
    public static final long getMillisecondsForDay() {
        return DAY_MILLISECONDS;
    }


    /**
     * 获取今天的日期，格式yyyyMMdd
     *
     * @return
     */
    public static String getTodayYYYYMMDD() {
        return DatePattern.PURE_DATE_FORMAT.format(new DateTime());
    }

    /**
     * 获取今天的日期，格式yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTodayYyyyMMddHHmmss() {
        return DatePattern.PURE_DATETIME_FORMAT.format(new DateTime());
    }

    /**
     * 获取相对今日的日期，格式yyyyMMdd
     *
     * @param offset
     * @return
     */
    public static String getOffsetDayYYYYMMDD(int offset) {
        LocalDate localDate = LocalDate.now().plusDays(offset);
        return localDate.format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATE_PATTERN));
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }


    /**
     * 将毫秒时间戳格式化为年月日字符串，如：1475035970000 格式化为：20160928
     *
     * @param timeMiles
     * @return
     */
    public static final String formatTimeToYMD(long timeMiles) {
        return formatTimeToStr(timeMiles,DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 将毫秒时间戳按指定格式化输出
     *
     * @param timeMiles
     * @param formatStr
     * @return
     */
    public static final String formatTimeToStr(long timeMiles, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String ymd = sdf.format(new Date(timeMiles));
        return ymd;
    }

    /**
     * 根据时间字符串获取13位时间戳
     *
     * @param strDate
     * @param formatStr
     * @return long 返回
     * @throws ParseException
     * @author z00562 2016年5月25日
     */
    public static long parseDateTimeStr(String strDate, String formatStr) throws ParseException {
        Date oDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        oDate = formatter.parse(strDate);
        return oDate.getTime();
    }

    /**
     * 根据时间字符串获取13位时间戳
     *
     * @param strDate
     * @return long 返回
     * @throws ParseException
     * @author z00562 2016年5月25日
     */
    public static long parseDateTimeStr(String strDate) throws ParseException {
        return parseDateTimeStr(strDate, "yyyyMMdd");
    }


    /**
     * 获取当天的开始时间
     */
    public static long getDayStartMiilliSeconds() {
        return LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当天的结束时间
     */
    public static long getDayEndMilliSeconds() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 获取当前时间，单位为毫秒
     *
     * @return
     */
    public static long getCurrentMilliSeconds() {
        return System.currentTimeMillis();
    }

    /**
     * 获取根据时间戳偏移后的时间戳(向前偏移)
     *
     * @param milliTime
     * @param offsetMilli
     * @return
     */
    public static long getPreOffsetMilliByTime(long milliTime, long offsetMilli) {
        return milliTime - offsetMilli;
    }

    /**
     * 根据时间戳获取当天0点的时间戳
     *
     * @param milliTime
     * @return
     */
    public static long getDayStartTime(long milliTime) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(milliTime), ZoneId.systemDefault());
        return localDateTime.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 根据时间戳获取当天23:59:59的时间戳
     *
     * @param milliTime
     * @return
     */
    public static long getDayEndTime(long milliTime) {
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(milliTime), ZoneId.systemDefault());
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当天开始时间
     *
     * @param date
     * @return
     */
    public static Date dateStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, BaseConstant.CONST0);
        calendar.set(Calendar.MINUTE, BaseConstant.CONST0);
        calendar.set(Calendar.SECOND, BaseConstant.CONST0);
        calendar.set(Calendar.MILLISECOND, BaseConstant.CONST0);
        return calendar.getTime();
    }
}
