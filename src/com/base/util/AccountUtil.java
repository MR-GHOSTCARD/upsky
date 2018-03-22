package com.base.util;

import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: AccountUtil.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年3月11日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：账户帮助类
 */
public class AccountUtil {
	
	//账户金额默认格式(带2位小数)
    public static final String MONEY_DEFAULT_FORMAT="#.##";
	
	/**
	 * 根据格式四舍五入
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:20
	 * @param num
	 * @param format
	 * @return
	 */
	public static String format(String num,String format){
		if(StringUtils.isBlank(num))return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(Double.parseDouble(num));
	}
	
	/**
	 * 根据金额加密
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:26
	 * @param balance
	 * @return
	 */
	public static String serByBalance(String balance){
		return encrypt(balance);
	}
	
	public static void main(String[] args) {
//		System.out.println(sum("1979.4950000000001", "134.2", "#.##"));
//		System.out.println("123".equals(null));
//		System.out.println(new DecimalFormat("#.##").format(1979.4950000000001));// 1979.5
		System.out.println(AccountUtil.multiplication("1", "100", "#.##"));
	}
	
	/**
	 * 字符串数字相加
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:36
	 * @param num1
	 * @param num2
	 * @param format
	 * @return
	 */
	public static String sum(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.HALF_UP);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 + d2).toString();
	}
	
	/**
	 * 减法
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:41
	 * @param num1
	 * @param num2
	 * @param format
	 * @return
	 */
	public static String subtraction(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.HALF_UP);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 - d2).toString();
	}
	
	/**
	 * 除法
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:47
	 * @param num1
	 * @param num2
	 * @param format
	 * @return
	 */
	public static String division(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		if(Double.valueOf(num2) == 0d){
			return "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.HALF_UP);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 / d2).toString();
	}
	
	/**
	 * 乘法
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:52
	 * @param num1
	 * @param num2
	 * @param format
	 * @return
	 */
	public static String multiplication(String num1,String num2,String format){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.HALF_UP);
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return df.format(d1 * d2).toString();
	}

	/**
	 * 大于
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:48:56
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean greateThan(String num1,String num2){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return d1>d2;
	}

	/**
	 * 大于等于
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:49:01
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean greateEqual(String num1,String num2){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return d1>=d2;
	}
	
	/**
	 * 等于
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:49:06
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static boolean equal(String num1,String num2){
		if(StringUtils.isBlank(num1))num1 = "0";
		if(StringUtils.isBlank(num2))num2 = "0";
		Double d1 = new Double(num1);
		Double d2 = new Double(num2);
		return d1.equals(d2);
	}
	
	/**
	 * 加密方法
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午8:49:10
	 * @param password
	 * @return
	 */
	public static String encrypt(String password) {

		MessageDigest md;
		try {

			md = MessageDigest.getInstance("MD5");
			
			int size = password.length()/2;
			md.update((password+(size!=0?password.substring(size-1,size):"")).getBytes());

			return Base64.encode(md.digest());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
}
