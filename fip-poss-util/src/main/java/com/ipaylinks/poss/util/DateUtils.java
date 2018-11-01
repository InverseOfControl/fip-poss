/**
 * 启赟金融信息服务（上海）有限公司
 * Copyright (c) 2015-2017 iPayLinks.All Rights Reserved.
 */
package com.ipaylinks.poss.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具模块
 *
 * @author liuzhangxue
 * @date 2017年11月28日 下午6:21:24
 */
public class DateUtils {

    /**
     * 将字符串类型转换为时间类型
     *
     * @param str
     * @return
     */
    public static Date str2Date(String str, String format) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            d = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 将一个日期型转换为指定格式字串
     *
     * @param aDate
     * @param formatStr
     * @return
     */
    public static final String toFormatDateString(Date aDate, String formatStr) {
        if (aDate == null) {
            return StringUtils.EMPTY;
        }
        return new SimpleDateFormat(formatStr).format(aDate);
    }

    /**
     * 获取该日期所在月份的第一天
     *
     * @param date
     * @return
     */
    public static Date monthFirstDate(Date date) {
        //取得系统当前时间
        Calendar cal = Calendar.getInstance();
        //取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate;
    }


    /**
     * 获取指定日期上个月 ，格式类型YYYYMM
     */
    public static String getLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);
        //输出上月
        return toFormatDateString(cal.getTime(), "yyyyMM");
    }

    /**
     * 获取上个月的同一日期
     *
     * @param date
     * @return
     */
    public static Date getLastMonthSameDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);
        int dayLastMonth = cal.get(Calendar.DAY_OF_MONTH);
        if (day <= dayLastMonth) {
            cal.set(Calendar.DAY_OF_MONTH, day);
        }
        return cal.getTime();
    }

    /**
     * 根据结算日判断上一结算周期:未过本月结算日取上月结算周期，过了就取本月;
     *
     * @param intestDay
     * @return YYYYMM
     */
    public static String getIntestMonth(String intestDay) {
        int intest = Integer.parseInt(intestDay);
        Calendar cal = Calendar.getInstance();
        int now = cal.get(Calendar.DAY_OF_MONTH);
        if (now > intest) {
            return toFormatDateString(cal.getTime(), "yyyyMM");
        }

        cal.add(Calendar.MONTH, -1);
        return toFormatDateString(cal.getTime(), "yyyyMM");
    }

    /**
     * 获取当前日期 ，格式类型yyyyMMdd
     */
    public static String getCurrentDate() {
        return toFormatDateString(getNow(), MAIL_DATE_DT_PART_FORMAT);
    }

    /**
     * 获取当前日期类型时间
     */
    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前时间戳
     */
    public static long getNowTimestamp() {
        return getNow().getTime();
    }

    //============================4.时间加减=====================================

    /**
     * 为一个日期加上指定年数
     *
     * @param aDate
     * @param amount 年数
     * @return
     */
    public static final Date addYears(Date aDate, int amount) {
        return addTime(aDate, Calendar.YEAR, amount);
    }

    /**
     * 为一个日期加上指定月数
     *
     * @param aDate
     * @param amount 月数
     * @return
     */
    public static final Date addMonths(Date aDate, int amount) {
        return addTime(aDate, Calendar.MONTH, amount);
    }

    /**
     * 为一个日期加上指定天数
     *
     * @param aDate
     * @param amount 天数
     * @return
     */
    public static final Date addDays(Date aDate, int amount) {
        return addTime(aDate, Calendar.DAY_OF_YEAR, amount);
    }

    public static String getCurrentDate(int year, int month, int day, int beDay) {
        GregorianCalendar newCal = new GregorianCalendar(year, month, day);
        long milSec = newCal.getTimeInMillis() - (long) beDay * 24 * 3600 * 1000;
        GregorianCalendar other = new GregorianCalendar();
        other.setTimeInMillis(milSec);
        String newYear = String.valueOf(other.get(GregorianCalendar.YEAR));
        String newMonth = String.valueOf(other.get(GregorianCalendar.MONTH) + 1);
        newMonth = newMonth.length() == 1 ? "0" + newMonth : newMonth;
        String newDay = String.valueOf(other.get(GregorianCalendar.DAY_OF_MONTH));
        newDay = newDay.length() == 1 ? "0" + newDay : newDay;
        String date = newYear + "-" + newMonth + "-" + newDay;
        return date;
    }

    /**
     * 为一个日期减去一个指定天数
     *
     * @param amount 天数
     * @return yyyy-mm-dd
     */
    public static final String minusDays(int amount) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int moths = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getCurrentDate(year, moths, day, amount - 1);
    }


    /**
     * 为一个日期加上指定小时数
     *
     * @param aDate
     * @param amount 小时数
     * @return
     */
    public static final Date addHours(Date aDate, int amount) {
        return addTime(aDate, Calendar.HOUR, amount);

    }

    /**
     * 为一个日期加上指定分钟数
     *
     * @param aDate
     * @param amount 分钟数
     * @return
     */
    public static final Date addMinutes(Date aDate, int amount) {
        return addTime(aDate, Calendar.MINUTE, amount);
    }

    /**
     * 为一个日期加上指定秒数
     *
     * @param aDate
     * @param amount 秒数
     * @return
     */
    public static final Date addSeconds(Date aDate, int amount) {
        return addTime(aDate, Calendar.SECOND, amount);

    }

    private static final Date addTime(Date aDate, int timeType, int amount) {
        if (aDate == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.add(timeType, amount);
        return cal.getTime();
    }

    public static boolean isDate(String dateStr, String formatStr) {
        if (dateStr == null) {
            return false;
        }
        if (formatStr == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return StringUtils.equals(dateStr, format.format((format.parse(dateStr))));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 把yyyy-MM-dd格式日期字符串增加amount天
     *
     * @param dateTime yyyy-MM-dd格式字符串
     * @param amount   变化的天数
     * @return yyyy-MM-dd格式字符串
     * @throws Exception
     */
    public static String addDay(String dateTime, int amount) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = df.parse(dateTime);
        c.setTime(date);
        c.add(Calendar.DATE, amount);
        return df.format(c.getTime());
    }

    /**
     * getCurrentDay: 获取当前的时间 yyyymmdd 格式
     *
     * @return
     * @throws
     */
    public static final String getCurrentDay() {
        Date aDate = new Date();
        return toFormatDateString(aDate, MAIL_DATE_DT_PART_FORMAT);
    }

    /**
     * getCurrentDdate:获取当前的时间   格式   yyyymmddhhmmss
     *
     * @return
     * @throws
     */
    public static final String getCurrentDdate() {
        Date aDate = new Date();
        return toFormatDateString(aDate, MAIL_DATE_FORMAT);
    }

    /**
     * 初始化表单默认开始日期和结束日期
     *
     * @author hongxu.gao
     * @date 2018/8/30 15:33
     */
    public static final String initFormDate(int month){
        String beginDateStr = DateUtils.toFormatDateString(DateUtils.addMonths(new Date(),month),"yyyy-MM-dd");
        String endDateStr = DateUtils.toFormatDateString(new Date(),"yyyy-MM-dd");
        return beginDateStr + "@" + endDateStr;
    }

    public static final String MONTH_FORMAT = "yyyyMM";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_GBK_FORMAT = "yyyy年MM月dd日";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分";
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String LONG_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String MAIL_DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String MAIL_DATE_HHMM_FORMAT = "HH:mm";
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String FULL_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";
    public static final String FULL_DATE_COMPACT_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String LDAP_DATE_FORMAT = "yyyyMMddHHmm'Z'";
    public static final String US_LOCALE_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String FORMAL_DATE_FORMAT = "yyyyMMdd HH:mm:ss";

    public static final String MAIL_DATE_DT_PART_FORMAT = "yyyyMMdd";
    public static final String MAIL_DATE_HOUR_DT_PART_FORMAT = "yyyyMMddHH";
    public static final String MAIL_MON_DAY_PART_FORMAT = "MMdd";
    public static final String MAIL_TIME_TM_PART_FORMAT = "HHmmss";
    public static final String LONG_DATE_TM_PART_FORMAT = "HH:mm:ss";
    public static final String LONG_DATE_TM_PART_GBK_FORMAT = "HH时mm分ss秒";
    public static final String MAIL_DATA_DTM_PART_FORMAT = "MM月dd日HH:mm";
    public static final String POINT_DATA_DTM_PART_FORMAT = "yyyy.MM.dd";

    public static final String DEFAULT_DATE_FORMAT = US_LOCALE_DATE_FORMAT;

    public static long NANO_ONE_SECOND = 1000;
    public static long NANO_ONE_MINUTE = 60 * NANO_ONE_SECOND;
    public static long NANO_ONE_HOUR = 60 * NANO_ONE_MINUTE;
    public static long NANO_ONE_DAY = 24 * NANO_ONE_HOUR;

    public static final String DASH = "-";
    public static final String COLON = ":";

    private DateUtils() {
    }

}
