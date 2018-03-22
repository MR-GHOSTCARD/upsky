package com.base.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: HttpClientTool.java
 * 修订记录：
 * 序号		日期							作者(操作:具体内容)
 * 1		2016年10月19日 上午11:28:52		郑建璋(创建:创建文件)
 *====================================================
 * 类描述：httpClent请求封装
 */
public class HttpClientTool {
	private CloseableHttpClient httpClient;
	private boolean isMobile=false;
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	private static final int MAX_TIMEOUT = 60*1000;
    public static final String DEFAULT_ENCODING="utf-8";
    private RequestConfig requestConfig;
	/**
	 * 构造函数
	 */
	public HttpClientTool(){
		httpClient=HttpClients.custom().build();
		requestConfig=RequestConfig.custom()  
		// 设置连接超时
        .setSocketTimeout(MAX_TIMEOUT)  
        // 设置读取超时
        .setConnectTimeout(MAX_TIMEOUT)
        //关闭自动跳转
        .setRedirectsEnabled(false)
        //关闭循环地址跳转
        .setCircularRedirectsAllowed(false)
        //关闭相对地址跳转
        .setRelativeRedirectsAllowed(false)
        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
        // 设置从连接池获取连接实例的超时
        .setConnectionRequestTimeout(MAX_TIMEOUT)
        .build();  
		
	}
	/** 
     * 发送Post请求 
     * @param httpPost 
     * @return 
     */  
    private byte[] sendHttpPost(HttpPost httpPost,Map<String, String> requestHead) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;  
        byte[] responseContent = null;
        try {  
        	httpPost.setConfig(requestConfig);
        	setRequestHead(httpPost);
        	if(requestHead!=null){
        		for(String key:requestHead.keySet()){
        			httpPost.addHeader(key, requestHead.get(key));
        		}
        	}
            // 执行请求  
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toByteArray(entity);
            EntityUtils.consume(entity);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {
                    response.close();  
                }  
            } catch (IOException e) {
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @throws UnsupportedEncodingException 
     */  
    public String sendHttpPost(String httpUrl,String charset,Map<String, String> requestHead) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return new String(sendHttpPost(httpPost,requestHead), charset==null?"UTF-8":charset);
    }  
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param maps 参数 
     * @throws UnsupportedEncodingException 
     */  
    public String sendHttpPost(String httpUrl, Map<String, String> maps,String charset,Map<String, String> requestHead) throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost   
        // 创建参数队列    
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        for (String m_key : maps.keySet()) {  
            nameValuePairs.add(new BasicNameValuePair(m_key, maps.get(m_key)));  
        }  
        try {  
        	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, getparamsCharset(requestHead)));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return new String(sendHttpPost(httpPost,requestHead), charset==null?"UTF-8":charset);  
    }
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param maps 参数 
     * @throws UnsupportedEncodingException 
     */  
    public byte[] sendHttpPost(String httpUrl, Map<String, String> maps,Map<String, String> requestHead) throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost   
        // 创建参数队列    
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        for (String m_key : maps.keySet()) {  
            nameValuePairs.add(new BasicNameValuePair(m_key, maps.get(m_key)));  
        }  
        try {  
        	
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, getparamsCharset(requestHead)));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sendHttpPost(httpPost,requestHead);
    }
    /** 
     * 发送 post请求（带文件） 
     * @param httpUrl 地址 
     * @param maps 参数 
     * @param fileLists 附件 
     * @throws UnsupportedEncodingException 
     */  
    public String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists,String charset,Map<String, String> requestHead) throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();  
        for (String m_key : maps.keySet()) {  
            meBuilder.addPart(m_key, new StringBody(maps.get(m_key), ContentType.TEXT_PLAIN));  
        }  
        for(File file : fileLists) {  
            FileBody fileBody = new FileBody(file);  
            meBuilder.addPart("files", fileBody);  
        }  
        HttpEntity reqEntity = meBuilder.build();  
        httpPost.setEntity(reqEntity);  
        return new String(sendHttpPost(httpPost,requestHead), getparamsCharset(requestHead));  
    }
    /** 
     * 发送Get请求 
     * @param httpPost 
     * @return 
     */  
    private byte[] sendHttpGet(HttpGet httpGet,Map<String, String> requestHead) {  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        byte[] responseContent = null;  
        try {  
        	httpGet.setConfig(requestConfig);
        	setRequestHead(httpGet);
        	if(requestHead!=null){
        		for(String key:requestHead.keySet()){
        			httpGet.addHeader(key, requestHead.get(key));
        		}
        	}
            // 执行请求  
            response = httpClient.execute(httpGet);  
            entity = response.getEntity();
            responseContent = EntityUtils.toByteArray(entity);  
            EntityUtils.consume(entity);
        } catch (Exception e) {  
            e.printStackTrace();
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  
    /** 
     * 发送 get请求 
     * @param httpUrl 
     * @throws UnsupportedEncodingException 
     */  
    public String sendHttpGet(String httpUrl,String charset,Map<String, String> requestHead) throws UnsupportedEncodingException {  
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求  
        return new String(sendHttpGet(httpGet,requestHead), charset==null?"UTF-8":charset);  
    }
    /** 
     * 发送 get请求 
     * @param httpUrl 
     * @throws UnsupportedEncodingException 
     */  
    public String sendHttpGet(String httpUrl,Map<String, String> params,String charset,Map<String, String> requestHead) throws UnsupportedEncodingException {
    	String paramsStr=null;
    	if(params!=null&&!(params.isEmpty())){
    		for(String key:params.keySet()){
    			if(paramsStr==null){
    				paramsStr="?"+key+"="+params.get(key);
    			}else{
    				paramsStr+="&"+key+"="+params.get(key);
    			}
    		}
    	}
    	HttpGet httpGet = new HttpGet(httpUrl+(paramsStr==null?"":paramsStr));// 创建get请求  
        return new String(sendHttpGet(httpGet,requestHead), charset==null?"UTF-8":charset);  
    }
    /** 
     * 发送 get请求 
     * @param httpUrl 
     * @throws IOException 
     */  
    public byte[] sendHttpGet(String httpUrl,Map<String, String> requestHead) throws IOException {  
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求  
		return sendHttpGet(httpGet,requestHead);
    }
    public static void main(String[] args) {
    	HttpClientTool httpClient=new HttpClientTool();
        try {  
            org.jsoup.nodes.Document doc = Jsoup.parse(httpClient.sendHttpGet("http://www.card.petrochina.com.cn/", "UTF-8",null));  
            Elements title = doc.select("input");  
            for (org.jsoup.nodes.Element link : title) {
            	String name=link.attr("name");
            	if(name!=null&&!("".equals(name))){
            		System.out.println(link.attr("name")+":"+link.attr("value"));
            	}
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
    private void setRequestHead(HttpRequestBase request){
    	if(isMobile){
    		//移动端请求头
    		request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    		request.addHeader("Accept-Encoding", "gzip, deflate, sdch");
    		request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
    		request.addHeader("Host", request.getURI().getHost());
    		request.addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
    	}else{
    		//模拟谷歌浏览器请求头
    		request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    		request.addHeader("Accept-Encoding", "gzip, deflate, sdch");
    		request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
    		request.addHeader("Host", request.getURI().getHost());
    		request.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
    		request.addHeader("Connection","keep-alive");
    	}
    }
    private String getparamsCharset(Map<String, String> requestHead){
    	if(requestHead!=null&&!(requestHead.isEmpty())){
    		for(String key:requestHead.keySet()){
    			if("Content-Type".equals(key)&&StringUtils.isNotBlank(requestHead.get(key))&&requestHead.get(key).contains("charset")){
    				String returnStr=requestHead.get(key).split("charset")[1].replace("=", "").trim();
    				if(StringUtils.isNotBlank(returnStr)){
    					return returnStr;
    				}
    			}
    		}
    	}
    	return DEFAULT_ENCODING;
    }
}
