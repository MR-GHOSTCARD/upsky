package com.base.util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;

/**操作阿里云OSS的工具类
 * @author chensz
 * @version 1.0
 * @create_date 2015-09-18
 */
public class SaveFileToOss {

	//阿里云OSS的地址
	private static  String  END_POINT = "http://oss-cn-beijing.aliyuncs.com";
	//阿里云OSS账号
	private static final String ACCESS_ID = "xGlKTY1sxvWesrFZ";
	//阿里云OSS账号密码
	private static final String ACCESS_KEY = "ff9KzrgMSzeCBqq8JnW0YH3Scx6VWW";
	//阿里云OSS上保存文件的具体区域名
	private static  String BUCKET_NAME = "eys-files";
	//连接阿里云OSS的本地代理
	private static  OSSClient client = new OSSClient(END_POINT, ACCESS_ID, ACCESS_KEY);
	
	/**将文件保存到阿里云OSS
	 * @author chensz
	 * @version 1.0
	 * @create_date 2015-09-18
	 */
	public static String save(InputStream inputStream,String key) throws Exception {
		key=checkKey(key);
		if(client.doesObjectExist(BUCKET_NAME, key)){
			throw new IOException("文件已经存在");
		}
		client.putObject(BUCKET_NAME,key,inputStream,null);	
		return "http://files.eyunshu.com/"+key;
	}
		
	public static String save(byte[] b,String key) throws Exception {
		InputStream inputStream=null;
		try{
			inputStream=new ByteArrayInputStream(b);
			return save(inputStream,key);
		}finally{
			if(inputStream!=null){
				try{inputStream.close();}catch(IOException ex){}
			}
		}
	}
	
	public static  void delete(String key){
		try{
			key=checkKey(key);
			if(key.startsWith("http://")){
				key=key.replaceFirst("http://files.eyunshu.com/", "");
			}
			
			if(client.doesObjectExist(BUCKET_NAME, key)){
				client.deleteObject(BUCKET_NAME, key);
			}
		}catch(Exception ex){}
	}
	
	
	private static String checkKey(String key) throws IOException{
		if(key==null){
			throw new IOException("文件名为空");
		}
		key=key.trim();
		if(key.length()==0){
			throw new IOException("文件名为空");
		}
		
		return key;
	}
	
}
