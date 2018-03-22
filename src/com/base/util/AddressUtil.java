package com.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: AddressUtil.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年8月1日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：地址工具类
 */
public class AddressUtil {

	/**
	 * 根据手机号码，获取手机号码归属地
	 * 作者：林辉
	 * 创建时间：2016年8月1日 下午2:17:51
	 * @param mobileNumber
	 * @return
	 * @throws Exception
	 */
	  public static String getAddressByMobile(String mobileNumber) {  
	    	Document doc;
	    	String result="";  
			try {
				doc = Jsoup.connect("http://www.ip138.com:8080/search.asp?action=mobile&mobile="+mobileNumber).timeout(2000).get();
		    	Elements tds = doc.select("td.tdc2");
				for(int i=0;i<tds.size();i++){
					if(i==1){
						result = tds.get(i).html();
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			  return result.replace("<!-- <td></td> -->", "").replace("&nbsp;", " ").replace("市", "");  
	    }  
	  
	  
		/**
		 * 获取地址
		 * @param params
		 * @param encoding
		 * @return
		 * @throws Exception
		 */
		public static String getAddressByIP(String ip){
			String queryUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="+ip+"&resource_id=6006";
	        String result="";
	          try {
	                // 根据地址获取请求
	                HttpGet request = new HttpGet(queryUrl);//这里发送get请求
	                request.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,2000);     
	                // 获取当前客户端对象
	                HttpClient httpClient = new DefaultHttpClient();
	                // 通过请求对象获取响应对象
	                HttpResponse response = httpClient.execute(request);
	                // 判断网络连接状态码是否正常(0--200都数正常)
	                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    result= EntityUtils.toString(response.getEntity(),"utf-8");
	                } 
	                request.abort();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	       String data =  JSONObject.fromObject(result).getString("data");
	       String cityStr = data.replace("[", "").replace("]", "");
	       if(StringUtils.isNotBlank(cityStr)){
	    	   String city =  JSONObject.fromObject(cityStr).getString("location");
	    	   String[] cityArr = city.split(" ");
	    	   String area = cityArr[0].replace("省", " ").replace("市", " ");
	    	   return area.trim();
	       }
			return "";
			
//			String path = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
//			String returnStr = getRs(path, ip, "utf-8");
//			JSONObject json=null;
//			if(returnStr != null){
//				json =JSONObject.fromObject(returnStr);
//				if("0".equals(json.get("code").toString())){
//					String  province = decodeUnicode(json.optJSONObject("data").getString("region"));
//					if(StringUtils.isNotBlank(province)){
//						province =province.replace("省", "").replace("市", "");
//					}
//					String city = decodeUnicode(json.optJSONObject("data").getString("city"));//市区
//					if(StringUtils.isNotBlank(city)){
//						city =city.replace("市", "");
//					}
//					return province+" "+city;
//				}else{
//					return "获取地址失败";
//				}
//			}
//			return null;
		}
		
		
		/**
		 * 查询ip地址所在地区（百度）
		 * 作者：林辉
		 * 创建时间：2016年10月25日 下午3:04:13
		 * @param ip
		 * @return
		 */
		public static String getIPAddressByBaidu(String ip){
			String queryUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="+ip+"&resource_id=6006";
	        String result="";
	          try {
	                // 根据地址获取请求
	                HttpGet request = new HttpGet(queryUrl);//这里发送get请求
	                request.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,2000);     
	                // 获取当前客户端对象
	                HttpClient httpClient = new DefaultHttpClient();
	                // 通过请求对象获取响应对象
	                HttpResponse response = httpClient.execute(request);
	                // 判断网络连接状态码是否正常(0--200都数正常)
	                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    result= EntityUtils.toString(response.getEntity(),"utf-8");
	                } 
	                request.abort();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	       String data =  JSONObject.fromObject(result).getString("data");
	       String cityStr = data.replace("[", "").replace("]", "");
	       if(StringUtils.isNotBlank(cityStr)){
	    	   String city =  JSONObject.fromObject(cityStr).getString("location");
	    	   String[] cityArr = city.split(" ");
	    	   String area = cityArr[0].replace("省", " ").replace("市", " ");
	    	   return area.trim();
	       }
			return "";
		}
		
		/**
		 * 从url获取结果
		 * @param path
		 * @param params
		 * @param encoding
		 * @return
		 */
		public static String getRs(String path, String params, String encoding){
			URL url = null;
			HttpURLConnection connection = null;
			try {
				url = new URL(path);
				connection = (HttpURLConnection)url.openConnection();// 新建连接实例
				connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫�?
				connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫�?
				connection.setDoInput(true);// 是否打开输出�? true|false
				connection.setDoOutput(true);// 是否打开输入流true|false
				connection.setRequestMethod("GET");// 提交方法POST|GET
				connection.setUseCaches(false);// 是否缓存true|false
				connection.connect();// 打开连接端口
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = reader.readLine())!= null) {
					buffer.append(line);
				}
				reader.close();
				return buffer.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				connection.disconnect();// 关闭连接
			}
			return null;
		}
		
		/**
		 * 字符转码
		 * @param theString
		 * @return
		 */
		public static String decodeUnicode(String theString){
			char aChar;
			int len = theString.length();
			StringBuffer buffer = new StringBuffer(len);
			for (int i = 0; i < len;) {
				aChar = theString.charAt(i++);
				if(aChar == '\\'){
					aChar = theString.charAt(i++);
					if(aChar == 'u'){
						int val = 0;
						for(int j = 0; j < 4; j++){
							aChar = theString.charAt(i++);
							switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
							val = (val << 4) + aChar - '0';
							break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
							val = (val << 4) + 10 + aChar - 'a';
							break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
							val = (val << 4) + 10 + aChar - 'A';
							break;
							default:
							throw new IllegalArgumentException(
								"Malformed      encoding.");
						    }
						}
						buffer.append((char) val);
						}else{
							if(aChar == 't'){
								aChar = '\t';
							}
							if(aChar == 'r'){
								aChar = '\r';
							}
							if(aChar == 'n'){
								aChar = '\n';
							}
							if(aChar == 'f'){
								aChar = '\f';
							}
							buffer.append(aChar);
						}
					}else{
						buffer.append(aChar);
					}
				}
			return buffer.toString();
		}
		
		
		public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
		    String ip = request.getHeader("x-forwarded-for");
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getRemoteAddr();
		    }
		    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		}
		
		
		
		public static void main(String[] args) throws Exception {
			
//			String address = AddressUtil.getAddressByMobile("18562651030");
//			if(StringUtils.isNotBlank(address)){
//				System.out.println("通过手机号获取："+address);
//			}
			String ip="112.226.112.235";
			try {
				//获取根据ip、手机号地址
				String address = AddressUtil.getAddressByIP(ip);
				System.out.println("通过淘宝ip库获取："+address);
//				if(StringUtils.isNotBlank(address)&&address.split(" ").length==2){
//					System.out.println("通过ip获取："+address);
//				}else{
//					address = AddressUtil.getAddressByMobile("18562651030");
//					if(StringUtils.isNotBlank(address)){
//						System.out.println("通过手机号获取："+address);
//					}
//				}
				
				String city = getIPAddressByBaidu(ip);
				System.out.println("根据百度ip获取的地址为："+city);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
