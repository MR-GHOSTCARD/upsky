package com.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;


public class WeiXinUtil {
	//查询订单信息接口
			private static String query_order_url="https://api.weixin.qq.com/pay/orderquery?access_token=ACCESS_TOKEN";
			
			/**
			 * 获取微信商户订单信息
			 * 作者：林辉
			 * 创建时间：2016年4月19日 上午8:35:27
			 * @param queryOrder
			 * @param access_token
			 * @return
			 */
			public static int req_queryOrder(String queryOrder,String access_token){
				int relust=-1;
				String queryOrderUrl=query_order_url.replace("ACCESS_TOKEN", access_token);
				JSONObject jsonObject=httpRequest(queryOrderUrl, "POST", queryOrder);  
				if(jsonObject!=null){
					if(jsonObject.getJSONObject("order_info").get("trade_state").equals("0")){
						relust=Integer.parseInt(jsonObject.getJSONObject("order_info").get("trade_state").toString()) ;
					}
				}
				return relust;
			}
			
			/**
			 * 发起https请求并获取结果
			 * 作者：林辉
			 * 创建时间：2016年4月19日 上午8:35:38
			 * @param requestUrl 请求地址
			 * @param requestM 请求方法(get/post)
			 * @param outStr 提交的数据
			 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
			 */
			public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
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
						System.out.println();
					}
					bufferedReader.close();
					inputStreamReader.close();
					// 释放资源
					inputStream.close();
					inputStream = null;
					httpUrlConn.disconnect();
					jsonObject = JSONObject.fromObject(buffer.toString());
				} catch (ConnectException ce) {
				} catch (Exception e) {
				}
				return jsonObject;
			}
			
			/**
			 * 获取微信js-sdk的访问票据
			 * 作者：林辉
			 * 创建时间：2016年4月19日 上午8:36:42
			 * @return
			 */
			public static String getAccessToken(){
				String appid = SystemConfig.getWeixinAppId();
				String secret = SystemConfig.getWeixinSecret();
				String queryUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
				JSONObject jsonObject = httpRequest(queryUrl, "GET", null);
				if(jsonObject!=null&&jsonObject.containsKey("access_token")){
					return jsonObject.getString("access_token");
				}
				return null;
			}
			
			/**
			 * 获取jsapi的临时票据
			 * 作者：林辉
			 * 创建时间：2016年4月19日 上午8:41:57
			 * @return
			 */
			public static String getJsapiTicket(String accessToken){
				String queryUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
				JSONObject jsonObject = httpRequest(queryUrl, "GET", null);
				if(jsonObject!=null&&jsonObject.containsKey("ticket")){
					return jsonObject.getString("ticket");
				}
				return null;
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
			
			
			/**
			 * 获取js-sdk签名
			 * 作者：林辉
			 * 创建时间：2016年4月19日 上午8:48:19
			 * @param noncestr
			 * @param timestamp
			 * @param url
			 * @return
			 */
			public static String getSignature(String noncestr,String timestamp,String url,ServletContext context){
				/**
				 * 第一步：获取临时票据
				 */
				String accessToken = "";
				String ticket ="";
				if(context!=null){
					//获取accessToken
					if(context.getAttribute("accessToken")!=null){
						int expires = (int) context.getAttribute("accessTokenExpires");
						String accessTokenCreateTime = (String) context.getAttribute("accessTokenCreateTime");
						//获取当前时间差，判定是否有效范围
						int seconds = TimeUtil.getTimeStamp(TimeUtil.getCurrentTime(), accessTokenCreateTime, "second");
						if(seconds+10<expires){
							accessToken = (String) context.getAttribute("accessToken");
						}else{
							//全局缓存
							accessToken = getAccessToken();
							context.setAttribute("accessTokenCreateTime", TimeUtil.getCurrentTime());
							context.setAttribute("accessToken", accessToken);
							context.setAttribute("accessTokenExpires", 7200);
						}
					}else{
						//首次获取
						accessToken = getAccessToken();
						context.setAttribute("accessTokenCreateTime", TimeUtil.getCurrentTime());
						context.setAttribute("accessToken", accessToken);
						context.setAttribute("accessTokenExpires", 7200);
					}

					//获取临时票据
					if(context.getAttribute("jsapiTicket")!=null){
						int ticketExpires = (int) context.getAttribute("jsapiTicketExpires");
						String jsapiTicketCreateTime = (String) context.getAttribute("jsapiTicketCreateTime");
						//获取当前时间差，判定是否有效范围
						int ticketSeconds = TimeUtil.getTimeStamp(TimeUtil.getCurrentTime(), jsapiTicketCreateTime, "second");
						if(ticketSeconds+10<ticketExpires){
							ticket = (String) context.getAttribute("jsapiTicket");
						}else{
							//全局缓存
							ticket = getJsapiTicket(accessToken);
							context.setAttribute("jsapiTicketCreateTime", TimeUtil.getCurrentTime());
							context.setAttribute("jsapiTicket", ticket);
							context.setAttribute("jsapiTicketExpires", 7200);
						}
					}else{
						//首次获取
						ticket = getJsapiTicket(accessToken);
						context.setAttribute("jsapiTicketCreateTime", TimeUtil.getCurrentTime());
						context.setAttribute("jsapiTicket", ticket);
						context.setAttribute("jsapiTicketExpires", 7200);
					}

				}
				
				if(StringUtils.isNotBlank(accessToken)&&StringUtils.isNotBlank(ticket)){
						/**
						 * 开始签名
						 */
						String unsign = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
						//sha1签名
					    MessageDigest md;
					    StringBuffer sb = new StringBuffer();
						try {
							md = MessageDigest.getInstance("SHA-1");
							md.update(unsign.getBytes("UTF-8"));
							byte[] result = md.digest();
							for (byte b : result) {
						        int i = b & 0xff;
						        if (i < 0xf) {
						            sb.append(0);
						        }
						        sb.append(Integer.toHexString(i));
						    }
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
							return null;
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							return null;
						}
					    if(StringUtils.isNotBlank(sb.toString())){
					    	return sb.toString();
					    }
				}
				return null;
			}
}
