package com.etc.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypto {

	/**
	 * MD5加密 不通过秘钥无法逆转
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:50:54
	 * @param strSrc
	 * @return
	 */
	public byte[] md5(String strSrc){
		byte[] returnByte=null;
		
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			returnByte=md5.digest(strSrc.getBytes("GBK"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnByte;
	}
	
	/**
	 * 对字符串进行Base64编码
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:51:06
	 * @param src
	 * @return
	 */
	public String getBase64Encode(byte[] src){
		String requestValue="";
		
		BASE64Encoder base64Encoder=new BASE64Encoder();
		requestValue=base64Encoder.encode(src);
		
		return requestValue;
	}
	
	/**
	 * 去掉字符串的换行符号
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:51:16
	 * @param str
	 * @return
	 */
	private String filter(String str){
		String output=null;
		StringBuffer sBuffer=new StringBuffer();
		for(int i=0;i<str.length();i++){
			int asc=str.charAt(i);
			if(asc!=10&&asc!=13)
				sBuffer.append(str.subSequence(i, i+1));
		}
		output=new String(sBuffer);
		return output;
	}
	
	/**
	 * 对字符串进行URLEecoder编码
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:51:25
	 * @param src
	 * @return
	 */
	public String getURLEncode(String src){
		String requestValue="";
		
		requestValue=URLEncoder.encode(src);
		return requestValue;
	}
	
	/**
	 * 对字符串进行URLDecoder解码
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:51:44
	 * @param src
	 * @return
	 */
	public String getURLDecoderdecode(String src){
		String requestValue="";
		requestValue=URLDecoder.decode(src);
		
		return requestValue;
	}
	
	//获取3-DES秘钥
	private byte[] getEnKey(String spKey){
		byte[] desKey=null;
		//将原始key进行md5加密生成新key
		byte[] desKey1=md5(spKey);
		desKey=new byte[24];
		int i=0;
		//最多只取key的前24位
		while(i<desKey1.length&&i<24){
			desKey[i]=desKey1[i];
			i++;
		}
		while(i<24){
			desKey[i]=0;
			i++;
		}
		
		return desKey;
	}
	
	//3-DES加密
	private byte[] encrypt(byte[] src,byte[] enKey){
		byte[] encrytedData=null;
		try {
			//创建一个DESede 密钥，默认以enKey的前24个字节为秘钥内容
			DESedeKeySpec dks=new DESedeKeySpec(enKey);
			//创建秘钥工厂，返回一个具有指定规范的秘钥工厂对象
			//秘钥工厂用于进一步处理秘钥,生成一个秘钥规范
			SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DESede");
			//通过已得到的秘钥规范生成一个秘钥对象
			SecretKey key=keyFactory.generateSecret(dks);
			//得到一个指定转换的cipher对象 用于实现加密解密操作
			Cipher cipher=Cipher.getInstance("DESede");
			//用指定个的秘钥和操作模式初始化对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//加密或解密由操作模式决定
			encrytedData=cipher.doFinal(src);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encrytedData;
	}
	
	/**
	 * 进行3-DES加密
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:52:07
	 * @param src
	 * @param spkey
	 * @return
	 */
	public String get3DESEncrypt(String src,String spkey){
		String requestValue="";
		try {
			//得到3-DES秘钥
			byte[] enKey=getEnKey(spkey);
			//将加密内容转化为字节
			byte[] src2=src.getBytes("utf-8");
			//获取加密后的字节
			byte[] encryptedData=encrypt(src2, enKey);
			
			//对加密后的内容进行BASE64编码
			String base64String=getBase64Encode(encryptedData);
			//编码后去除换行符
			String base64Encrypt=filter(base64String);
			
			//对base64编码中的HTML控制码进行转译
			requestValue=getURLEncode(base64Encrypt);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestValue;
	}
	
	//3-DES解密
	private String deCrypt(byte[] dase,String spKey){
		String strDe=null;
		Cipher cipher=null;
		try {
			cipher=Cipher.getInstance("DESede");
			byte[] key=getEnKey(spKey);
			DESedeKeySpec deSedeKeySpec=new DESedeKeySpec(key);
			SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DESede");
			SecretKey key2=keyFactory.generateSecret(deSedeKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, key2);
			byte[] bytes=cipher.doFinal(dase);
			strDe=new String(bytes,"utf-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strDe;
	}
	
	/**
	 * 进行3-DES解密
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午1:52:33
	 * @param src
	 * @param spkey
	 * @return
	 */
	public String get3DESDecrypt(String src,String spkey){
		String requestValue="";
		
		//将内容转为字节
		try {
			//控制码转译
			String URLValue=getURLDecoderdecode(src);
			
			BASE64Decoder base64Decoder=new BASE64Decoder();
			byte[] base64Value=base64Decoder.decodeBuffer(URLValue);
			requestValue=deCrypt(base64Value, spkey);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestValue;
	}
	
	public static void main(String[] args) {
		Crypto crypto=new Crypto();
		String password="123456";
		String cryPassword=crypto.get3DESEncrypt(password, "121212");
		System.out.println(cryPassword);
		System.out.println(crypto.get3DESDecrypt(cryPassword, "121212"));
	}
}
