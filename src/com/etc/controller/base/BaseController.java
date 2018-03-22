package com.etc.controller.base;

public class BaseController {
	
	

	
	
	
	
	
	/**
     * 转换乱码
     * 作者：林辉
     * 创建时间：2016年6月2日 下午2:30:40
     * @param s
     * @return
     */
    public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){
                sb.append(c);
            }else{
                byte[] b;
                try { 
                    b = Character.toString(c).getBytes("utf-8");
                }catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}
