package com.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: ValidateUtil.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年3月1日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：完整性验证辅助类
 */
public class ValidateUtil {
	
	private static final String onlyNum = "^[0-9]*{1}";
	/** 
	* 验证身份证号码 
	* @param id_number 
	* @return 
	*/  
	public static Boolean checkNID(String id_number){  
		if(id_number.length() != 15 && id_number.length() != 18){  
			return false;  
		}  
		String string = id_number.substring(0, id_number.length() - 1);  
		if(!string.matches(onlyNum)){  
			return false;  
		}  
		return true;  
	}  
	
    /**
     * 验证小数
     * 作者：林辉
     * 创建时间：2016年3月1日 下午1:59:08
     * @param number
     * @return
     */
    public static boolean isDecimal(String number){
    	String num = "^(([0-9]*).([0-9]{1,2}))|(([0-9]*))$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(number);
    	return m.matches();
    }
    
    /**
     * 验证重量
     * 作者：林辉
     * 创建时间：2016年3月1日 下午1:59:16
     * @param number
     * @return
     */
    public static boolean isWeight(String number){
    	String num = "^(([0-9]*).([0-9]{1,3}))|(([0-9]*))$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(number);
    	return m.matches();
    }
	
	
	/**
	 * 验证邮箱   
	 * 作者：林辉
	 * 创建时间：2016年3月1日 下午1:59:22
	 * @param email
	 * @return
	 */
	public static Boolean checkEmail(String email) {  
		String check = "^[a-zA-Z0-9_-]+\\@{1}[a-zA-Z0-9_-]+(\\.{1}[a-zA-Z0-9_-]+)+$";  
		Pattern regex = Pattern.compile(check);  
		Matcher matcher = regex.matcher(email);  
		boolean isMatched = matcher.matches();  
		return isMatched;  
	}
	
	/**
	 * 手机号码验证
	 * 作者：林辉
	 * 创建时间：2016年3月1日 下午1:59:27
	 * @param mobile
	 * @return
	 */
	public static Boolean checkMoblie(String mobile){
		String mobileRegex="^\\d{11}$";
		Pattern regex = Pattern.compile(mobileRegex);
		Matcher matcher = regex.matcher(mobile);
		return matcher.matches();
	}
	
	/**
	 * 电话号码验证
	 * 作者：林辉
	 * 创建时间：2016年3月1日 下午1:59:33
	 * @param phonenumber
	 * @return
	 */
    public static boolean isTelephone(String phonenumber) {
    	String phone = "^(0\\d{2,3}-?)?\\d{7,8}$";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }
    
    /**
     *  验证金额
     * 作者：林辉
     * 创建时间：2016年3月1日 下午1:59:39
     * @param moneyNum
     * @return
     */
    public static boolean isMoney(String moneyNum){
    	String money = "(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
    	Pattern p = Pattern.compile(money);
    	Matcher m = p.matcher(moneyNum);
    	return m.matches();
    }
    
    
    /**
     * 验证数字
     * 作者：林辉
     * 创建时间：2016年3月1日 下午1:59:45
     * @param number
     * @return
     */
    public static boolean isNumber(String number){
    	String num = "^[1-9]\\d*$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(number);
    	return m.matches();
    }
    
   /**
    * 验证时间	
    * 作者：林辉
    * 创建时间：2016年3月1日 下午1:59:51
    * @param time
    * @return
    */
    public static boolean isTime(String time){
    	String t = "^\\d{4}-(0?[1-9]|[1][012])-(0?[1-9]|[12][0-9]|[3][01])[\\s]+([0-1][0-9]|2?[0-3]):([0-5][0-9]):([0-5][0-9])$";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(time);
    	return m.matches();
    }
    
    /**
     * 验证时间 格式:yyyy-MM-dd
     * 作者：林辉
     * 创建时间：2016年3月1日 下午2:00:00
     * @param time
     * @return
     */
    public static boolean isDate(String time){
    	String t = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(time);
    	return m.matches();
    }
    
    /**
     * 检验车牌号
     * 作者：林辉
     * 创建时间：2016年3月1日 下午2:00:05
     * @param vehicle_no
     * @return
     */
    public static boolean isVehicle(String vehicle_no) {  
    	String t = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9_\u4e00-\u9fa5]{5}$";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(vehicle_no);
    	return m.matches();
    	
    } 
   
    /**
     * 校验银行卡卡号  
     * 作者：林辉
     * 创建时间：2016年3月1日 下午2:00:11
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {  
    	char  bit = getBankCardCheckCode(cardId.substring( 0 , cardId.length() -  1 ));  
    	if (bit ==  'N' ){  
    		return   false ;  
    	}  
    	return  cardId.charAt(cardId.length() -  1 ) == bit;  
    } 
    
   /**
    * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位 
    * 作者：林辉
    * 创建时间：2016年3月1日 下午2:00:17
    * @param nonCheckCodeCardId
    * @return
    */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){  
        if (nonCheckCodeCardId ==  null  || nonCheckCodeCardId.trim().length() ==  0   
                || !nonCheckCodeCardId.matches("\\d+" )) {  
         //如果传的不是数据返回N   
            return   'N' ;  
        }  
        char [] chs = nonCheckCodeCardId.trim().toCharArray();  
        int  luhmSum =  0 ;  
        for ( int  i = chs.length -  1 , j =  0 ; i >=  0 ; i--, j++) {  
            int  k = chs[i] -  '0' ;  
            if (j %  2  ==  0 ) {  
                k *= 2 ;  
                k = k / 10  + k %  10 ;  
            }  
            luhmSum += k;             
        }  
        return  (luhmSum %  10  ==  0 ) ?  '0'  : ( char )(( 10  - luhmSum %  10 ) +  '0' );  
    }
    
   /**
    * 验证企业拓展
    * 作者：林辉
    * 创建时间：2016年3月1日 下午2:00:30
    * @param email
    * @return
    */
	public static Boolean checkExtension(String email) {  
		String check = "^@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+com";  
		Pattern regex = Pattern.compile(check);  
		Matcher matcher = regex.matcher(email);  
		boolean isMatched = matcher.matches();  
		return isMatched;  
	}
	
	/**
	 * 验证字符串是否有包含中文符号
	 * 作者：林辉
	 * 创建时间：2016年3月1日 下午2:00:36
	 * @param name
	 * @return
	 */
    public static boolean ChineseSign(String name){
    	String t = "[\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b]";
    	Pattern p = Pattern.compile(t);
    	Matcher m = p.matcher(name);
    	return m.find();
    }
    
    /**
     * 判断输入的字符串是否满足时间格式 ： yyyy-MM-dd HH:mm:ss
     * @param patternString 需要验证的字符串
     * @return 合法返回 true ; 不合法返回false
     */
    public static boolean isTimeLegal(String patternString) {
           
        Pattern a=Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$"); 
    
        Matcher b=a.matcher(patternString); 
        if(b.matches()) {
              return true;
         } else {
               return false;
         }
    }
    
    /**
     * 验证组织机构代码
     * 作者：林辉
     * 创建时间：2016年3月1日 下午2:00:49
     * @param org_code
     * @return
     */
    public static boolean isOrg_code(String org_code){
    	String num = "^[0-9A-Z]{8}-{1}[0-9A-Z]{1}$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(org_code);
    	return m.matches();
    }
    
   /**
    * 验证营业执照号
    * 作者：林辉
    * 创建时间：2016年3月1日 下午2:00:55
    * @param license_no
    * @return
    */
    public static boolean isLicense_no(String license_no){
    	String num = "^[0-9]{15}$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(license_no);
    	return m.matches();
    }
    
    /**
     * 验证税务登记号
     * 作者：林辉
     * 创建时间：2016年3月1日 下午2:01:01
     * @param tax_no
     * @return
     */
    public static boolean isTax_no(String tax_no){
    	String num = "^[0-9A-Z]{15}$";
    	Pattern p = Pattern.compile(num);
    	Matcher m = p.matcher(tax_no);
    	return m.matches();
    }
    
   /**
    * 验证传真号码
    * 作者：林辉
    * 创建时间：2016年3月1日 下午2:01:06
    * @param fax_no
    * @return
    */
    public static boolean isFax_no(String fax_no){
    	String fax = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$";
    	Pattern p = Pattern.compile(fax);
    	Matcher m = p.matcher(fax_no);
    	return m.matches();
    }
    
    /**
     * 判断网络是否正常连接
     * 作者：林辉
     * 创建时间：2016年3月1日 下午2:01:11
     * @param addr
     * @return
     */
    public static boolean isConnect(String addr){
    	URL url = null;  
        try {  
            url = new URL(addr);  
            try {  
                InputStream in = url.openStream();  
                in.close();  
                return true; 
            } catch (IOException e) {  
            	e.printStackTrace();
                return false;
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
            return false;
        }  
    }
    
    /**
     * 判断url是否为阿里云路径
     * 作者：林辉
     * 创建时间：2016年3月16日 上午9:29:49
     * @param number
     * @return
     */
    public static boolean isAliUrl(String data){
    	if(StringUtils.isNotBlank(data)&&data.contains("http")){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 验证密码格式
     * 作者：林辉
     * 创建时间：2016年3月21日 下午3:45:00
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
    	if(StringUtils.isBlank(password)){
    		return false;
    	}
    	String pwd = "^[0-9A-Za-z]{6,20}$";
    	Pattern p = Pattern.compile(pwd);
    	Matcher m = p.matcher(password);
    	return m.matches();
    }
    
    
    public static void main(String[] args) { 
    System.out.println(isDate(null));
    }
    
    /**
     * 手机号码显示处理
     * 作者：郑建璋
     * 创建时间：2016年5月21日 下午4:55:01
     * @param mobile
     * @return
     */
    public static String mobileProcess(String mobile){
    	if(checkMoblie(mobile)){
    		return mobile.substring(0,3)+"*****"+mobile.substring(mobile.length()-3,mobile.length());
    	}else{
    		return "";
    	}
    }
    
    /**
     * 对传入字符串添加星号水印处理
     * 作者：林辉
     * 创建时间：2016年9月21日 下午2:42:17
     * @param undeal
     * @return
     */
    public static String addStar(String undeal){
    	if(StringUtils.isNotBlank(undeal)&&undeal.length()>3){
    		return undeal.substring(0,3)+"*****"+undeal.substring(undeal.length()-3,undeal.length());
    	}else{
    		return undeal;
    	}
    }
}
