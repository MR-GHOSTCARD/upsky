package com.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class RetrivePage {
	private static HttpClient httpClient=new HttpClient();
	//设置代理服务器
	static{
		//httpClient.getHostConfiguration().setProxy("10.59.2.118", 5608);
	}
	
	public static boolean downloadPage(String path) throws org.apache.commons.httpclient.HttpException, IOException{
		InputStream input=null;
		OutputStream output=null;
		//得到post方法
		PostMethod postMethod=new PostMethod(path);
		//设置post方法参数
		NameValuePair[] postData=new NameValuePair[2];
		postData[0]=new NameValuePair("name","lietu");
//		postData[1]=new 
		postMethod.addParameters(postData);
		int statusCode=httpClient.executeMethod(postMethod);
		if(statusCode==HttpStatus.SC_OK){
			input=postMethod.getResponseBodyAsStream();
			//得到文件名
			String filename=path.substring(path.lastIndexOf('/')+1);
			System.out.println(filename);
			//获得文件输出流
			output=new FileOutputStream(filename);
			
			//得到输出文件
			int tempByte=-1;
			while((tempByte=input.read())>0){
				output.write(tempByte);
			}
			//关闭输入输出流
			if(input!=null){
				input.close();
			}
			if(output!=null){
				output.close();
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
//		try {
//			RetrivePage.downloadPage("http://www.lietu.com/");
//		} catch (org.apache.commons.httpclient.HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#");
		for(int i=0;i<200;i++){
			System.out.println(df.format(Math.random()*6));
		}
	}

}
