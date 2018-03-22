package com.base.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式常量
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: TimeFormateUtil.java.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年2月20日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：
 */
public class TimeFormateUtil implements Serializable {
	/**年月时分秒**/
	public static String FORMATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 年月日
	 */
	public static String FORMATE_DATE = "yyyy-MM-dd";
	
	/**
	 * 年月
	 */
	public static String FORMATE_MONTH = "yyyy-MM"; 
	
	/**
	 * 日
	 */
	public static String AREA_DAY = "dd";
	/**
	 * 年
	 */
	public static String AREA_YEAR = "yyyy";
	/**
	 * 月
	 */
	public static String AREA_MONTH = "MM";
	
	/**
	 * 小时
	 */
	public static String AREA_HOUR = "HH";
	
	/**
	 * 分钟
	 */
	public static String AREA_MINUTE = "mm";
	/**
	 * 秒
	 */
	public static String AREA_SECOND = "ss";
	
/**
 * 格式化日期
 * 作者：林辉
 * 创建时间：2016年2月20日 下午3:23:29
 * @param date
 * @param format
 * @return
 */
	public static String getFormatDate(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 获取日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:23:57
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getDateByString(String str,String format){
		SimpleDateFormat sf=new SimpleDateFormat(format);
		Date date=null;
		try {
			date=sf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 比较日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:24:28
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareDate(Date beginDate,Date endDate){
		return beginDate.getTime()<endDate.getTime();
	}
	
	/**
	 * 比较日期字符串
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:24:36
	 * @param beginDate
	 * @param endDate
	 * @param format
	 * @return
	 */
	public static boolean compareDate(String beginDate,String endDate,String format){
		Date d1=getDateByString(beginDate, format);
		Date d2=getDateByString(endDate, format);
		return compareDate(d1, d2);
	}
	
	
}
