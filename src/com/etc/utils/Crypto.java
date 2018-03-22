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
	 * MD5���� ��ͨ����Կ�޷���ת
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:50:54
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
	 * ���ַ�������Base64����
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:51:06
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
	 * ȥ���ַ����Ļ��з���
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:51:16
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
	 * ���ַ�������URLEecoder����
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:51:25
	 * @param src
	 * @return
	 */
	public String getURLEncode(String src){
		String requestValue="";
		
		requestValue=URLEncoder.encode(src);
		return requestValue;
	}
	
	/**
	 * ���ַ�������URLDecoder����
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:51:44
	 * @param src
	 * @return
	 */
	public String getURLDecoderdecode(String src){
		String requestValue="";
		requestValue=URLDecoder.decode(src);
		
		return requestValue;
	}
	
	//��ȡ3-DES��Կ
	private byte[] getEnKey(String spKey){
		byte[] desKey=null;
		//��ԭʼkey����md5����������key
		byte[] desKey1=md5(spKey);
		desKey=new byte[24];
		int i=0;
		//���ֻȡkey��ǰ24λ
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
	
	//3-DES����
	private byte[] encrypt(byte[] src,byte[] enKey){
		byte[] encrytedData=null;
		try {
			//����һ��DESede ��Կ��Ĭ����enKey��ǰ24���ֽ�Ϊ��Կ����
			DESedeKeySpec dks=new DESedeKeySpec(enKey);
			//������Կ����������һ������ָ���淶����Կ��������
			//��Կ�������ڽ�һ��������Կ,����һ����Կ�淶
			SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DESede");
			//ͨ���ѵõ�����Կ�淶����һ����Կ����
			SecretKey key=keyFactory.generateSecret(dks);
			//�õ�һ��ָ��ת����cipher���� ����ʵ�ּ��ܽ��ܲ���
			Cipher cipher=Cipher.getInstance("DESede");
			//��ָ��������Կ�Ͳ���ģʽ��ʼ������
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//���ܻ�����ɲ���ģʽ����
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
	 * ����3-DES����
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:52:07
	 * @param src
	 * @param spkey
	 * @return
	 */
	public String get3DESEncrypt(String src,String spkey){
		String requestValue="";
		try {
			//�õ�3-DES��Կ
			byte[] enKey=getEnKey(spkey);
			//����������ת��Ϊ�ֽ�
			byte[] src2=src.getBytes("utf-8");
			//��ȡ���ܺ���ֽ�
			byte[] encryptedData=encrypt(src2, enKey);
			
			//�Լ��ܺ�����ݽ���BASE64����
			String base64String=getBase64Encode(encryptedData);
			//�����ȥ�����з�
			String base64Encrypt=filter(base64String);
			
			//��base64�����е�HTML���������ת��
			requestValue=getURLEncode(base64Encrypt);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestValue;
	}
	
	//3-DES����
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
	 * ����3-DES����
	 * ���ߣ�����ͦ
	 * ����ʱ�䣺2016��11��24�� ����1:52:33
	 * @param src
	 * @param spkey
	 * @return
	 */
	public String get3DESDecrypt(String src,String spkey){
		String requestValue="";
		
		//������תΪ�ֽ�
		try {
			//������ת��
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
