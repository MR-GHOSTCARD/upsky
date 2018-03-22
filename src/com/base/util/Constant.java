package com.base.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: Constant.java.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年2月20日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：
 */
public final class Constant implements Serializable {

	public static final String MONTH_COUNT_DATE = "3014-06";
	/********************* 时间格式化 ********************/
	/**
	 * yyyy-MM-dd'T'HH:mm:ss
	 */
	public static final String DATETIME_FORMAT_T = "yyyy-MM-dd'T'HH:mm:ss"; // 系统全局显示日期+时间格式
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 系统全局显示日期+时间格式
	/**
	 * yyyy
	 */
	public static final String DATETIME_YYYY = "yyyy"; // 系统全局显示日期格式
	/**
	 * yyyyMM
	 */
	public static final String DATETIME_YYYYMM = "yyyyMM"; // 系统全局显示日期格式
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATETIME_DATE_FORMAT = "yyyy-MM-dd"; // 系统全局显示日期格式
	/**
	 * HH:mm:ss
	 */
	public static final String DATETIME_TIME_FORMAT = "HH:mm:ss"; // 系统全局显示时间格式
	/**
	 * MM-dd
	 */
	public static final String DATETIME_SINGLE_DATE_FORMAT = "MM-dd"; // 系统全局显示无年份日期格式
	/**
	 * yyyy-MM-dd HH:00
	 */
	public static final String DATETIME_DATETIME_HOUR_FORMAT = "yyyy-MM-dd HH:00"; // 系统全局显示日期+小时格式
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String DATETIME_MINUTE_FORMAT = "yyyy-MM-dd HH:mm"; // 系统全局显示日期+小时分钟格式
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String DATETIME_STORE_DATETIME_FORMAT = "yyyyMMddHHmmss"; // 系统全局存储日期+时间格式
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATETIME_STORE_DATE_FORMAT = "yyyy-MM-dd"; // 系统全局存储日期格式
	/**
	 * HHmmss
	 */
	public static final String DATETIME_STORE_TIME_FORMAT = "HHmmss"; // 系统全局存储时间格式
	/**
	 * MMdd
	 */
	public static final String DATETIME_STORE_SINGLE_DATE_FORMAT = "MMdd"; // 系统全局存储无年份日期格式
	/**
	 * yyyyMMddHH
	 */
	public static final String DATETIME_STORE_DATETIME_HOUR_FORMAT = "yyyyMMddHH"; // 系统全局存储日期+小时格式
	/**
	 * yyyyMMddHHmm
	 */
	public static final String DATETIME_STORE_DATETIME_MINUTE_FORMAT = "yyyyMMddHHmm"; // 系统全局存储日期+小时分钟格式
	/**
	 * yyyy-MM
	 */
	public static final String DATETIME_STORE_DATE_MINUTE_FORMAT = "yyyy-MM"; // 系统全局存储日期+小时分钟格式
	/**
	 * 正则表达式 邮箱
	 */
	public static final String REGULAR_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/**
	 * 正则表达式 IP地址
	 */
	public static final String REGULAR_IP = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
	/**
	 * 正则表达式 日期时间，解决润月
	 */
	public static final String REGULAR_DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
	/**
	 * 正则表达式 手机
	 */
	public static final String REGULAR_MODILE_PHONE = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
	/**
	 * 正则表达式 以字母开头,字母数字混合的字符串
	 */
	public static final String REGULAR_LETTER_NUMBER = "^[a-zA-Z][a-zA-Z0-9]+";
	/**
	 * 正则表达式 数字
	 */
	public static final String REGULAR_NUMBER = "^[1-9][0-9]+";


	public static final Map<String, String> SMS_PARAMS = new HashMap<String, String>();
	
	
	/**
	 * 客户session标识
	 */
	public static String LOGIN_CUSTOMER_ACL = "customer_acl";
	
	/**
	 * 展示面板
	 */
	public final static String CURRENT_PANEL="current_panel";
	public final static String COMPANY_OWNER="企业面板";
	public final static String PERSON_OWNER="个人面板";
	public final static String DRIVER_OWNER="司机面板";
	
	
	public final static String CURRENT_DEVICE="current_device";
	public final static String CURRENT_DEVICE_ANDROID="android";
	public final static String CURRENT_DEVICE_IOS="ios";
	
	public final static String SYSTEM="fms";
	
	//图形验证码
	public final static String VALIDATE_CODE="validateCode";
	//短信验证码
	public final static String MOBILE_VALIDATE_CODE="mobileValidateCode";
	//忘记密码安全码
	public final static String SEC_CODE="secCode";
	
	public final static String OPER_ADMIN="管理员";
	
	//改绑手机标识码
	public final static String MOBILE_CHANGE="mobileBindIdx";
	//忘记密码标识码
	public final static String FORGET_PASSWORD="forgetPasswordIdx";
	
	/**
	 * 登录用户未认证(初次登录)
	 */
	public final static String CUSTOMER_AUTH_NONE_FIRST="0";
	/**
	 * 登录用户未认证
	 */
	public final static String CUSTOMER_AUTH_NONE="-1";
}
