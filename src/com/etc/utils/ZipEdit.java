package com.etc.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称：PdfEdit.java
 * 修订记录：
 * 序号	日期				作者(操作：具体内容)
 * 1    2017年6月1日			宋少挺(创建：创建文件)
 *==========================================
 *类描述：pdf处理类
 */
public class ZipEdit {
	public static boolean fileToZip(String sourceFilePath,OutputStream outputStream){
		ZipOutputStream zos=new ZipOutputStream(outputStream);
		BufferedInputStream bis=null;
		
		if(StringUtils.isBlank(sourceFilePath)){
			return false;
		}
		try {
			int i=1;
			String[] sources=sourceFilePath.split(",");
			for(String source:sources){
				File file=new File(new URI(source));
				//统一资源
				URL url=new URL(source);
				//连接
				URLConnection urlConnection=url.openConnection();
				//http连接
				HttpURLConnection httpURLConnection=(HttpURLConnection)urlConnection;
				//设定请求方式
//				httpURLConnection.setRequestMethod("POST");
				//设置编码
				httpURLConnection.setRequestProperty("Charset", "UTF-8");
				//打开url
				httpURLConnection.connect();
				//files.add(file);
				//创建zip实体
				ZipEntry zipEntry=new ZipEntry(i+".jpg");
			//	zipEntry
				zos.putNextEntry(zipEntry);
				//将文件写入流
				bis=new BufferedInputStream(httpURLConnection.getInputStream());
				byte[] bytes=new byte[bis.available()];
				int read=0;
				while((read=bis.read(bytes,0,bytes.length))!=-1){
					zos.write(bytes, 0, read);
				}
				i++;
				
			}
			bis.close();
			zos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static void main(String[] args) {
//		try {
//			File file=new File("D:\\dd\\ds.zip");
//			FileOutputStream fis=new FileOutputStream(file);
//			fileToZip("http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/72a9eb6fb8724ac0a97217240461984c,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/d9c12136b7af48acb3d6d4ad11569eb1,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/711bc2f1ce944df2bf0de4c18c6713dd,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/beec79a5ae5646dfa91a214d1c804653,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/cfe5ada7d8384a09a930b3e1d6fddde1,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/943053a5f1d24604a6caf72e1fd33900,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/884447ab293e4df0920956ba486df915,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/23482d30c3f84988a4abb755669638b5,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/cf08d39f5918450f89f9a87eaa06c2ff,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/691529ca9a5b4076aff06988a0c9877f,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/28352856c5024d5bb467440aef8d73fd,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/29f4ae9a8bbd453fa65264a91c540d26,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/60ce81aff9e24cadb93e9e20dcad8af8,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/0ffab83b57704dbc91f0156b057805a2", fis);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		File file=new File("D:\\dd\\cqcgps.xlsx");
		try {
			XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
