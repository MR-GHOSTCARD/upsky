package com.base.util;

import java.awt.Font;
import java.io.ByteArrayInputStream;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: ImgFontByte.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年3月2日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：图形验证码字体
 */
public class ImgFontByte {
	 public Font getFont(int fontHeight){  
	        try {  
	            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(hex2byte(getFontByteStr())));  
	            return baseFont.deriveFont(Font.PLAIN, fontHeight);  
	        } catch (Exception e) {  
	            return new Font("Arial",Font.PLAIN, fontHeight);  
	        }  
	    }  
	      
	    private  byte[] hex2byte(String str) {   
	        if (str == null)  
	            return null;  
	        str = str.trim();  
	        int len = str.length();  
	        if (len == 0 || len % 2 == 1)  
	            return null;  
	  
	        byte[] b = new byte[len / 2];  
	        try {  
	            for (int i = 0; i < str.length(); i += 2) {  
	                b[i / 2] = (byte) Integer  
	                        .decode("0x" + str.substring(i, i + 2)).intValue();  
	            }  
	            return b;  
	        } catch (Exception e) {  
	            return null;  
	        }  
	    } /** 
	  * ttf字体文件的十六进制字符串 
	  * @return 
	  */  
	 private String getFontByteStr(){ 
		 return null;  
	     //   return str;//字符串太长 在附件中找  
	}  
}

