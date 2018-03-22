package com.base.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {

	private static final String KEY = EncryptUtil.class.toString();

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

	public static void main(String[] args) {
		
		
	}
}
