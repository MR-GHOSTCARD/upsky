package com.base.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 时间计算辅助类
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: TimeUtil.java.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年2月20日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：
 */
public class TimeUtil implements Serializable{
	
	/**
	 * 获取当前时间(yyyy-MM-dd HH:mm:ss)
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:25:14
	 * @return
	 */
	public static String getCurrentTime(){
		return new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).format(new Date());
	}
	
	/**
	 * 获取当前日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:25:37
	 * @return
	 */
	public static String getCurrentDate(){
		return new SimpleDateFormat(TimeFormateUtil.FORMATE_DATE).format(new Date());
	}
	
	/**
	 * 获取当前月份
	 * 作者：林辉
	 * 创建时间：2016年3月16日 下午2:16:33
	 * @return
	 */
	public static String getCurrentMonth(){
		return new SimpleDateFormat(TimeFormateUtil.FORMATE_MONTH).format(new Date());
	}
	
	/**
	 * 计算指定时间加上指定时间区域数量后的结果
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:25:52
	 * @param date 指定时间
	 * @param part 计算部分
	 * @param num 计算数量
	 * @param ret_format 返回结果格式
	 * @return 计算后的时间
	 */
	public static String getDateStamp(String date,String part,int num,String ret_format){
		Calendar cld = Calendar.getInstance();
		try {
			cld.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(date));
			if(StringUtils.isNotBlank(part)){
				if(part.equals("date") || part.equals("dd"))
					cld.add(Calendar.DATE, num);
				else if(part.equals("year") || part.equals("yyyy"))
					cld.add(Calendar.YEAR, num);
				else if(part.equals("month") || part.equals("MM"))
					cld.add(Calendar.MONTH, num);
				else if(part.equals("hour") || part.equals("HH"))
					cld.add(Calendar.HOUR, num);
				else if(part.equals("minute") || part.equals("mm"))
					cld.add(Calendar.MINUTE, num);
				else if(part.equals("second") || part.equals("ss"))
					cld.add(Calendar.SECOND, num);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(ret_format)){
			return new SimpleDateFormat(ret_format).format(cld.getTime());
		}else{
			return new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).format(cld.getTime());
		}
	}
	
	/**
	 * 获取时间相减差距(time1-time2)
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:26:19
	 * @param time1
	 * @param time2
	 * @param part
	 * @return 差值
	 */
	public static int getTimeStamp(String time1,String time2,String part){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		try {
			cal1.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(time1));
			cal2.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(time2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long s_times = cal1.getTimeInMillis() - cal2.getTimeInMillis();
		if(StringUtils.isNotBlank(part)){
			if(part.equals("date") || part.equals("dd"))
				return (int)(s_times / 1000 / 60 / 60 / 24);
			else if(part.equals("hour") || part.equals("HH"))
				return (int)(s_times / 1000 / 60 / 60);
			else if(part.equals("minute") || part.equals("mm"))
				return (int)(s_times / 1000 / 60);
			else if(part.equals(TimeFormateUtil.AREA_MONTH)){
				return ((Integer.parseInt(time1.substring(0, 4)) - Integer.parseInt(time2.substring(0, 4))) * 12 + (Integer.parseInt(time1.substring(5, 7)) - Integer.parseInt(time2.substring(5, 7))));
			}else if(part.equals("second") || part.equals("ss")){
				return (int)(s_times / 1000);
			}
				
		}else{
			throw new RuntimeException("获取时间差部分异常");
		}
		return 0;
	}
	
	
	/**
	 * 获取该月的最后一天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:27:14
	 * @param date_time
	 * @param rs_format
	 * @return 该月的最后一天
	 */
	public static String getLastDayOfMonth(String date_time,String rs_format){
		if(StringUtils.isBlank(rs_format)){
			rs_format = TimeFormateUtil.FORMATE_TIME;
		}
		Calendar cld = Calendar.getInstance();
		try {
		cld.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(date_time));
		cld.set(Calendar.DAY_OF_MONTH, cld.getActualMaximum(Calendar.DAY_OF_MONTH));
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(rs_format).format(cld.getTime());
	}
	
	/**
	 * 时间比较
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:28:02
	 * @param date_time1
	 * @param date_time2
	 * @return
	 */
	public static int compare(String date_time1,String date_time2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		try {
			cal1.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(date_time1));
			cal2.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(date_time2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal1.compareTo(cal2);
	}
	
	/**
	 * 获取该月的第一天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:28:26
	 * @param date_time
	 * @param rs_format
	 * @return
	 */
	public static String getFirstDayOfMonth(String date_time,String rs_format){
		if(StringUtils.isBlank(rs_format)){
			rs_format = TimeFormateUtil.FORMATE_TIME;
		}
		Calendar cld = Calendar.getInstance();
		try {
			cld.setTime(new SimpleDateFormat(TimeFormateUtil.FORMATE_TIME).parse(date_time));
			cld.set(Calendar.DAY_OF_MONTH, cld.getActualMinimum(Calendar.DAY_OF_MONTH));
			cld.set(Calendar.HOUR_OF_DAY, 0);
			cld.set(Calendar.MINUTE, 0);
			cld.set(Calendar.SECOND, 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(rs_format).format(cld.getTime());
	}
	
	/**
	 * 获得指定日期的前一天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:28:35
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		String dayBefore = "";
		try {
			if(StringUtils.isNotBlank(specifiedDay)){
				SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
				Calendar c = Calendar.getInstance();
				Date date = dateFormat.parse(specifiedDay);
	
				c.setTime(date);
				int day = c.get(Calendar.DATE);
				c.set(Calendar.DATE, day - 1);
				int hour = c.get(Calendar.HOUR);
				c.set(Calendar.HOUR, hour);
				dayBefore = dateFormat.format(c.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayBefore;
	}

	/**
	 * 获得指定日期的前n天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:28:46
	 * @param specifiedDay
	 * @param n
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay, int n) {
		String dayBefore = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
			Calendar c = Calendar.getInstance();
			Date date = dateFormat.parse(specifiedDay);

			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - n);
			int hour = c.get(Calendar.HOUR);
			c.set(Calendar.HOUR, hour);

			dayBefore = dateFormat.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:28:57
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		String dayAfter = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day + 1);
			dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayAfter;
	}

	/**
	 * 获得指定日期的后n天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:08
	 * @param specifiedDay
	 * @param n
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay, int n) {
		String dayAfter = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;

			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day + n);

			dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayAfter;
	}

	public static String SicenToComm(double value) { // 保留小数点后3位（四舍五入），且不按科学计数法输出
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		retValue = df.format(value);
		retValue = retValue.replaceAll(",", "");
		return retValue;
	}

	/**
	 * 获得某年某月的第几天
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:19
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getMonthDay(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		return dateFormat.format(c.getTime());
	}

	/**
	 * 获取日期年份
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:24
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getYear(String date) throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 转化时间
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:31
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private static String parseDate(String date) throws ParseException {
		if (StringUtils.isNotBlank(date)) {
			if (date.length() < 8) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
				SimpleDateFormat f = new SimpleDateFormat(
						Constant.DATETIME_STORE_DATE_MINUTE_FORMAT);
				date = dateFormat.format(f.parse(date));
			}
		}
		return date;
	}

	/**
	 * 获取日期月份
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:42
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(String date) throws ParseException {
		date = parseDate(date);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		calendar.setTime(dateFormat.parse(date));
		return (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 获取日期号
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:47
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getDay(String date) throws ParseException {
		date = parseDate(date);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取月份起始日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:52
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(String date) throws ParseException {
		date = parseDate(date);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime()) + " 00:00:00";
	}

	/**
	 * 获取月份最后日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:29:58
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(String date) throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime()) + " 23:59:59";
	}

	/**
	 * 根据指定月份获取月份起始日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:04
	 * @param date
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDateByMonth(String date, Integer month)
			throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		date = parseDate(date);
		String m = date.substring(4, 7);
		date = date.replaceFirst(m, "-" + month);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 根据指定月份获取月份最后日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:09
	 * @param date
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDateByMonth(String date, Integer month)
			throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		String m = date.substring(4, 7);
		date = date.replaceFirst(m, "-" + month);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取下个月份最后日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:15
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNextMaxMonthDate(String date) throws ParseException {
		date = parseDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		String m = date.substring(5, 7);
		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) + 1));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取下个月份起始日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:20
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getNextMinMonthDate(String date) throws ParseException {
		date = parseDate(date);
		String m = date.substring(5, 7);
		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) + 1));
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取上个月份最后日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:25
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMaxMonthDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		date = parseDate(date);
//		String m = date.substring(5, 7);
//		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) - 1));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		calendar.set(Calendar.DAY_OF_MONTH,
//				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取上个月份起始日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:30
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMinMonthDate(String date) throws ParseException {
		date = parseDate(date);
//		String m = date.substring(5, 7);
//		date = date.replaceFirst("-" + m, "-" + (Integer.valueOf(m) - 1));
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		calendar.set(Calendar.DAY_OF_MONTH,
//				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 两个时间相差数
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:39
	 * @param start
	 * @param end
	 * @return
	 */
	public static int twoDateCalculatingTheNumber(String start, String end) {
		int quot = 0;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_DATE_FORMAT);
			Date date1 = dateFormat.parse(end);
			Date date2 = dateFormat.parse(start);
			long quot1 = (date1.getTime() - date2.getTime());
			quot1 = (quot1) / 1000 / 60 / 60 / 24;
			quot = (int) quot1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**
	 * 获得两个时间段之间的小时差
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:30:53
	 * @param start
	 * @param end
	 * @return
	 */
	public static double twoDateCalculatingToHour(String start, String end) {
		double quot = 0;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_FORMAT);
			Date date1 = dateFormat.parse(end);
			Date date2 = dateFormat.parse(start);
			long quot1 = (date1.getTime() - date2.getTime());
			quot = (quot1) / 1000.0 / 60.0 / 60.0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**
	 * 获取某个时间的前N个小时
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:00
	 * @param date
	 * @param hour
	 * @return
	 */
	public static String getOneHoursAgoTime(String date,Integer hour) {
		String oneHoursAgoTime = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_FORMAT);
			long msec = dateFormat.parse(date).getTime() - hour*3600000;
	        oneHoursAgoTime = new SimpleDateFormat(Constant.DATETIME_FORMAT).format(msec);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return oneHoursAgoTime;
    } 
	
	/**
	 * 转换不同格式的日期
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:05
	 * @param oldDate
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 */
	public static String translateFormat(String oldDate,String oldFormat,String newFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date date;
        String newDate;
		try {
			date = sdf.parse(oldDate);
			newDate = new SimpleDateFormat(newFormat).format(date);
		} catch (ParseException e) {
			newDate = oldDate;
		}
        
        return newDate;
	}
	
	/**
	 * 获取时间段内的月份列表
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:10
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static List<String> getMonthsFromTo(String fromDate,String toDate){
		List<String> listStr = null;
		try {
			int fromYear = Integer.parseInt(fromDate.split("-")[0]);
			int toYear = Integer.parseInt(toDate.split("-")[0]);
			int fromMonth = Integer.parseInt(fromDate.split("-")[1]);
			int toMonth = Integer.parseInt(toDate.split("-")[1]);
			//如果开始日期小于结束日期
			if(fromYear == toYear && fromMonth <= toMonth || fromYear < toYear){
				int months = (toYear - fromYear) * 12 + (toMonth - fromMonth);
				listStr = new ArrayList<String>();
				for(int i = fromMonth; i <= (fromMonth + months); i++){
					listStr.add((fromYear + i / 13) + "-" + (((i - 1) % 12 + 1) < 10 ? ("0" + ((i - 1) % 12 + 1)) : ((i - 1) % 12 + 1)));
				}
			}else{
				throw new RuntimeException("结束日期不能小于开始日期");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listStr;
	}
	
	/**
	 * 获取两个时间的实际天数差（例如：2014-03-21 12:00:00 到 2014-03-23 01:00:00 天数差为3天）
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:16
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int actualCalculatingDay(String startTime,String endTime){
		SimpleDateFormat format = new SimpleDateFormat(Constant.DATETIME_FORMAT);
		int actual_days = 1;
		try {
			if(endTime.length() == 19 && startTime.length()==19){
				long end = format.parse(endTime).getTime();
				long start = format.parse(startTime).getTime();
				if(end > start){
					actual_days = actual_days + ((int)(end/86400000) - (int)(start/86400000));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actual_days;
	}
	
	/**
	 * 比较时间大小(time > time2 返回 true)
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:22
	 * @param time
	 * @param time2
	 * @param date_format
	 * @return
	 */
	public static boolean casinoWar(String time,String time2,String date_format){
		boolean bool = false;
		SimpleDateFormat format = new SimpleDateFormat(date_format);
		try {
			if(format.parse(time).getTime()>=format.parse(time2).getTime()){
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
	
	/**
	 * 测试函数
	 * @author linjian 
	 * @create_date 2013-6-20 下午3:07:38
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(getLastDayOfMonth("2016-11-01 00:00:00",null));
		
//		Map<String, Object> res = new HashMap<>();
//		res.put("status", "repeatSubmit");
//		res.put("msg", "禁止重复提交");
//		JSONObject json = JSONObject.fromObject(res); 
//		System.out.println(json);
//		System.out.println("data:image/jpeg;base64,asda".replaceAll("data:image/(.*);base64,",""));
//		System.out.println(TimeUtil.actualCalculatingDay("2014-07-29 17:05:25", "2015-07-29 11:39:28"));
//		String  plainText="D6E8-208";
//		String re_md5 = new String();
//		F97F30EA384A5A08561AA3DDE1B99630
//     F97F30EA384A5A08561AA3DDE1B99630
//     F97F30EA384A5A08561AA3DDE1B99630
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(plainText.getBytes());
//            byte b[] = md.digest();
// 
//            int i;
// 
//            StringBuffer buf = new StringBuffer("");
//            for (int offset = 0; offset < b.length; offset++) {
//                i = b[offset];
//                if (i < 0)
//                    i += 256;
//                if (i < 16)
//                    buf.append("0");
//                buf.append(Integer.toHexString(i));
//            }
// 
//            re_md5 = buf.toString();
// 
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        System.out.println(re_md5);
	}
	
	/**
	 *  获取两个时间小时差(不足一小时是算一小时)
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:35
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static int dateDiff(String startTime, String endTime,   
            String format) {   
        // 按照传入的格式生成一个simpledateformate对象   
        SimpleDateFormat sd = new SimpleDateFormat(format);   
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数   
        long nh = 1000 * 60 * 60;// 一小时的毫秒数   
        long nm = 1000 * 60;// 一分钟的毫秒数   
        long diff;   
        long day = 0;   
        long hour = 0;   
        long min = 0;   
        // 获得两个时间的毫秒时间差异   
        try {   
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();   
            day = diff / nd;// 计算差多少天   
            hour = diff % nd / nh + day * 24;// 计算差多少小时   
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟   
            //不足一小时以一小时计算
            if(min%60>0){
            	hour = hour +1;
            } 
        } catch (ParseException e) {   
            e.printStackTrace();   
        }   
          return (int)hour;
    }
	
	/**
	 * 获取两个时间的天数差(不足一天算一天)
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:42
	 * @param time1
	 * @param time2
	 * @param format
	 * @return
	 */
	public static int daysBetween(String time1, String time2, String format) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		long days = 0;
		try {
			cal1.setTime(new SimpleDateFormat(format).parse(time1));
			cal2.setTime(new SimpleDateFormat(format).parse(time2));
			// 间隔天数
			long s_times = cal1.getTimeInMillis() - cal2.getTimeInMillis();
			days = s_times / (24 * 60 * 60 * 1000);
			if (s_times % (24 * 60 * 60 * 1000) > 0) {
				days += 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return (int)days;
	} 
	/**
	 * 返回两个日期相差的天数，不比较时间部分。
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:49
	 * @param start
	 * @param end
	 * @return
	 */
	public static int daysDiff(String start, String end){
		if(StringUtils.isBlank(start) || StringUtils.isBlank(end)
				|| start.length() < 10 || end.length() < 10) {
			throw new RuntimeException("日期格式不正确，无法比较。请使用指定格式：yyyy-MM-dd");
		}
		
		if(start.length() > 10) {
			start = start.substring(0, 10);
		}
		start += " 00:00:00";
		
		if(end.length() > 10) {
			end = end.substring(0, 10);
		}
		end += " 00:00:00";
		
		int diff = 0;
		try {
			diff = TimeUtil.getTimeStamp(start, end, TimeFormateUtil.AREA_DAY);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("日期格式不正确，无法比较。请使用指定格式：yyyy-MM-dd");
		}
		return diff;
	}
	
	/**
	 * 获取一年后的时间
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:31:56
	 * @param date
	 * @return
	 */
	public static String getNextYearDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat(TimeFormateUtil.FORMATE_DATE);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, 365);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获得指定日期的前N个月
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:32:01
	 * @param specifiedDay
	 * @param mth
	 * @return
	 */
	public static String getSpecifiedMonthBefore(String specifiedDay,int mth) {
		String monthBefore = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;
			date = new SimpleDateFormat("yy-MM").parse(specifiedDay);

			c.setTime(date);
			int month = c.get(Calendar.MONTH);
			c.set(Calendar.MONTH, month - mth);
			monthBefore = new SimpleDateFormat("yyyy-MM").format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return monthBefore;
	}
}
