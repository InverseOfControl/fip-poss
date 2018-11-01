package com.ipaylinks.poss.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 日期转换器
 * @author zhaoyang
 * @date 2018年8月14日
 */
@Component
public class DateConverter implements Converter<String, Date> {


	private Logger log = LoggerFactory.getLogger(DateConverter.class);
	
	private static final List<String> formarts = new ArrayList<>(4);
    static{
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }


    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public Date convert(String source) {
        String value = source.trim();
        log.debug("需要转换的时间：-----{}",source);
        if ("".equals(value)) {
            return null;
        }
        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return parseDate(source, formarts.get(0));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return parseDate(source, formarts.get(1));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formarts.get(2));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formarts.get(3));
        }else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

    /**
     * 格式化日期
     * @param dateStr String 字符型日期
     * @param format String 格式
     * @return Date 日期
     */
    public  Date parseDate(String dateStr, String format) {
        Date date=null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            log.error("时间格式转换异常", e);
        }
        log.debug("{}转换后的时间：--{}", dateStr,date);
        return date;
    }
	
//	@Override
//	public Date convert(String source) {
//	    log.info("原日期：{}"+source);
//		Date date = null;
//		for (String pattern : patterns) {
//			try {
//				sdf.applyPattern(pattern);
//				date = sdf.parse(source);
//				break;
//			} catch (Exception e) {
//			    log.error("日期格式转换异常", e);
//			}
//		}
//		if (date == null) {
//			throw new RuntimeException("日期格式转换异常");
//		}
//		log.info("转换后的日期：{}", date);
//		return date;
//	}

}
