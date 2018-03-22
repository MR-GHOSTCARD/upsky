package com.etc.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {

	private static final String KEY = EncryptUtil.class.toString();
	private static final String SPKEY="DESede";

	/**
	 * 不可逆加密方式
	 * */
	/*public static String encryptNewMD5(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}

		str = DigestUtils.md5Hex(str + KEY).substring(5, 20);

		return DigestUtils.md5Hex(str + KEY);
	}*/
	
	public static String encryptNewMD5(String str, String key) {
		if (str == null || str.length() == 0) {
			return null;
		}

		str = DigestUtils.md5Hex(str + key).substring(5, 20);

		return DigestUtils.md5Hex(str + key);
	}
	
	public static String encryptMD5(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}

		return DigestUtils.md5Hex(str);
	}
	

	/**
	 * 可逆加密方式，可解密
	 * */
	public static String encrypt(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		
		String result = "";
		if ((value != null) && (!(value.equals("")))) {
			byte[] valueBytes;
			try {
				// 改统一用UTF-8，避免不同环境字符集不同导致的问题
				valueBytes = value.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				valueBytes = value.getBytes();
			}
			int len1 = valueBytes.length / KEY.length();
			String longKey = "";
			for (int i = 0; i <= len1; ++i) {
				longKey = longKey + KEY;
			}
			byte[] longKeyBytes = longKey.getBytes();
			for (int i = 0; i < valueBytes.length; ++i) {
				byte b1 = valueBytes[i];
				byte b2 = longKeyBytes[i];
				String lpResultStr = Integer.toHexString((b1 ^ b2) & 0xFF)
						.toUpperCase();
				if (lpResultStr.length() < 2) {
					lpResultStr = "0" + lpResultStr;
				}
				result = result + lpResultStr;
			}
		}
		return result;
	}

	/**
	 * 解密
	 * */
	public static String decrypt(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		
		String result = "";
		try {
			if ((value != null) && (!(value.equals("")))) {
				int len1 = value.length() / KEY.length();
				String longKey = "";
				for (int i = 0; i <= len1; ++i) {
					longKey = longKey + KEY;
				}
				byte[] longKeyBytes = longKey.getBytes();
				byte[] encValueBytes = new byte[value.length() / 2];

				for (int i = 0; i < encValueBytes.length; ++i) {
					String lpResultStr = value.substring(i * 2, i * 2 + 2);
					encValueBytes[i] = (byte) Integer.parseInt(lpResultStr, 16);
				}
				byte[] lpResultBytes = new byte[encValueBytes.length];
				for (int i = 0; i < encValueBytes.length; ++i) {
					byte b1 = encValueBytes[i];
					byte b2 = longKeyBytes[i];
					lpResultBytes[i] = (byte) (b1 ^ b2);
				}
				result = new String(lpResultBytes, "utf-8");
			}
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	//3des秘钥转换
	public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException{
		byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
		 
		/*
		 * 执行数组拷贝
		* System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		*/
		if(key.length > temp.length){
		//如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
		System.arraycopy(temp, 0, key, 0, temp.length);
		}else{
		//如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
		System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
	
	//3des加密
	public static String encode3DES(String value,String key){
		String result="";
		try {
			//生成秘钥
			SecretKey secretKey=new SecretKeySpec(build3DesKey(key), SPKEY);
			//加密
			Cipher cipher=Cipher.getInstance(SPKEY);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			result=Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
//			result=new String(cipher.doFinal(value.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
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
		return result;
	}
	
	//3des解密
	public static String decode3Des(String value,String key){
		Base64 base64=new Base64();
		byte[] src=base64.decode(value);
		String result="";
		try {
			SecretKey secretKey=new SecretKeySpec(build3DesKey(key), SPKEY);
			//解密
			Cipher cipher=Cipher.getInstance(SPKEY);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			result=new String(cipher.doFinal(src)); 
		}  catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 验证密码是否有效
	 * 作者：宋少挺
	 * 创建时间：2017年3月1日 上午9:03:09
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password){
        String pwd = "^(?![^a-zA-Z]+$)(?!\\D+$).{6,}$";
        Pattern p = Pattern.compile(pwd);
        Matcher m = p.matcher(password);
        return m.matches();
	}
	
	public static void main(String[] args) {
		String value=EncryptUtil.encode3DES("1234741", "123");
		System.out.println(value);
		System.out.println(EncryptUtil.decode3Des(value, "123"));
		
	}
}
