package com.ipaylinks.poss.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具
 * @author Jerry Chen
 * @date 2018年9月10日
 */
public class StringUtil {

	//转变的依赖字符
	public static final char UNDERLINE ='_';


	private StringUtil () {}
	/**
	 * str转BigDecimal
	 * @param str
	 * @return
	 */
	public static BigDecimal str2BigDecimal(String str){
		if(str == null || StringUtils.isBlank(str = str.trim())){
			return null;
		}
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}
	/**
	 * str转Long
	 * @param str
	 * @return
	 */
	public static Long str2Long(String str){
		if(str == null || StringUtils.isBlank(str = str.trim())){
			return null;
		}
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return 0L;
		}
	}
	/**
	 * str转Accountingday
	 * @param str
	 * @return
	 */
	public static Integer str2AccountingDay(String str){
		if(str == null || StringUtils.isBlank(str = str.trim())){
			return null;
		}
		try {
			return Integer.valueOf(str.replaceAll("-", ""));
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * 2018-05-20 -> 2018-05-20 00:00:01
	 * @param str
	 * @return
	 */
	public static String str2StartDate(String str){
		if(str == null || StringUtils.isBlank(str = str.trim())){
			return null;
		}
		return str + " 00:00:01";
	}
	/**
	 * 2018-05-20 -> 2018-05-20 23:59:59
	 * @param str
	 * @return
	 */
	public static String str2EndDate(String str){
		if(str == null || StringUtils.isBlank(str = str.trim())){
			return null;
		}
		return str + " 23:59:59";
	}


	/**
	 * 将驼峰转换成"_"
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param){
		if (StringUtils.isBlank(param)){
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i=0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
