package com.ipaylinks.poss.util;


import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具
 * @author Jerry Chen
 * @date 2018年8月28日
 */
public class DateUtil {

	/**yyyyMMdd**/
    public final static String YYYYMMDD = "yyyyMMdd";
    /**yyyy-MM-dd**/
    public final static String YYYYMMDD_LONG = "yyyy-MM-dd";
    /**yyyy-MM-dd HH:mm:ss**/
    public final static String YYYYMMDDHHMMSS_LONG = "yyyy-MM-dd HH:mm:ss";
    /**yyyyMMddHHmmss**/
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String EMPTY_STRING = "";
    
    /**
     * 格式化：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatTime(Date date){
    	return format(date,YYYYMMDDHHMMSS_LONG);
    }
    /**
     * 格式化:yyyyMMdd
     * @param date
     * @return
     */
    public static String formatDate(Date date){
    	return format(date,YYYYMMDD);
    }
    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date,String pattern){
    	if(date == null){
    		return EMPTY_STRING;
    	}
    	if(pattern == null){
    		pattern = YYYYMMDDHHMMSS_LONG;
    	}
    	return DateFormatUtils.format(date, pattern);
    }
}
