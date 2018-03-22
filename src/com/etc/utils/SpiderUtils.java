package com.etc.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class SpiderUtils {
	
	public static void main(String[] args) {
		HttpClient httpClient=new HttpClient();
		
		GetMethod getMethod=new GetMethod("http://www.eyunshu.com/cn/");
		getMethod.setFollowRedirects(true);
	//	PostMethod postMethod=new PostMethod("http://www.eyunshu.com/cn/");
		try {
			int statusCode=httpClient.executeMethod(getMethod);
			System.out.println("response="+getMethod.getResponseBodyAsString());
			getMethod.releaseConnection();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
