package com.etc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class RequestUtil {
	/**
	 * 发起https请求并获取结果
	 * 作者：林辉
	 * 创建时间：2016年4月19日 上午8:35:38
	 * @param requestUrl 请求地址
	 * @param requestM 请求方法(get/post)
	 * @param outStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("utf-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			System.out.println(buffer);
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}
	
	/**
	 * http请求
	 * 作者：宋少挺
	 * 创建时间：2018年1月9日 上午11:31:01
	 * @param url
	 * @param method
	 * @param output
	 * @return
	 */
	public static String httpRequest(String requestUrl,String method,String output){
		StringBuffer buffer=null;
		
		try {
			URL url=new URL(requestUrl);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method);
			if ("GET".equalsIgnoreCase(method)){
				conn.connect();
			}
			//往服务器端写内容
			if(StringUtils.isNotBlank(output)){
				OutputStream os=conn.getOutputStream();
				os.write(output.getBytes("utf-8"));
				os.close();
			}
			//读取返回内容
			InputStream is=conn.getInputStream();
			InputStreamReader isr=new InputStreamReader(is, "utf-8");
			BufferedReader br=new BufferedReader(isr);
			buffer=new StringBuffer();
			String line="";
			while((line=br.readLine())!=null){
				buffer.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 生成随机字符串
	 * 作者：林辉
	 * 创建时间：2016年4月19日 上午9:22:44
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	}
	
	public static void main(String[] args) {
		Map<String, Object> params=new HashMap<String,Object>();
//		params.put("salesmanNo", "201801171617514");
		params.put("idCard", "422825199203251080");
		params.put("carFrameNo", "LFV2A2BS7F4511361");
		params.put("outMoneyDate", "2018-01-15");
		Gson gson=new Gson();
		String jsonObject=httpRequest("http://10.59.2.118:8094/cqc/pc/walletGoodCar/outOrIn/getRepayJson.htm", "POST", gson.toJson(params));
		System.out.println(jsonObject);
//		Map<String, Object> jsonMap=gson.fromJson(jsonObject,Map.class);
//		if(StringUtils.isNotBlank((String)params.get("salesmanNo"))){
//			jsonObject=EncryptUtil.decode3Des((String)jsonMap.get("returnContent"),(String)params.get("salesmanNo"));
//		}else if(StringUtils.isNotBlank((String)params.get("idCard"))){
//			jsonObject=EncryptUtil.decode3Des((String)jsonMap.get("returnContent"),(String)params.get("idCard"));
//		}else{
//			jsonObject=EncryptUtil.decode3Des((String)jsonMap.get("returnContent"), "123");
//		}
//		System.out.println(new String(Base64.decodeBase64(jsonObject)));
	}
	
}
