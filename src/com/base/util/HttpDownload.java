package com.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class HttpDownload {
	 public static final int cache = 10 * 1024;  
	    public static final boolean isWindows;  
	    public static final String splash;  
	    public static final String root;  
	    static {  
	        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {  
	            isWindows = true;  
	            splash = "\\";  
	            root="D:";  
	        } else {  
	            isWindows = false;  
	            splash = "/";  
	            root="/search";  
	        }  
	    }  
	      
	    /** 
	     * 根据url下载文件，文件名从response header头中获取 
	     * @param url 
	     * @return 
	     */  
	    public static String download(String url) {  
	        return download(url, null);  
	    }  
	  
	    /** 
	     * 根据url下载文件，保存到filepath中 
	     * @param url 
	     * @param filepath 
	     * @return 
	     */  
	    public static String download(String url, String filepath) {  
	        try {  
	            DefaultHttpClient client = new DefaultHttpClient();  
	            HttpGet httpget = new HttpGet(url);  
	            HttpResponse response = client.execute(httpget);  
	  
	            HttpEntity entity = response.getEntity();  
	            InputStream is = entity.getContent();  
//	            String pdString=readFdf(null,is);
//	            System.out.println(pdString);
	            
	            if (filepath == null)  
	                filepath = getFilePath(response);  
	            File file = new File(filepath);  
	            file.getParentFile().mkdirs();  
	            FileOutputStream fileout = new FileOutputStream(file);  
	            /** 
	             * 根据实际运行效果 设置缓冲区大小 
	             */  
//	            byte[] buffer=new byte[cache];  
//	            int ch = 0;  
//	            while ((ch = is.read(buffer)) != -1) {  
//	                fileout.write(buffer,0,ch);  
//	            }  
	            int ch = 0; 
	            byte[] buffer=new byte[cache]; 
	     //       InputStream iStream=new ByteArrayInputStream(pdString.getBytes("utf-8"));
	            while ((ch = is.read(buffer)) != -1) {  
	                fileout.write(buffer,0,ch);  
	            } 
	            is.close();  
	      //      iStream.close();
	            fileout.flush();  
	            fileout.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    } 
	    public static String readFdf(File file,InputStream is) throws IOException{
			boolean sort=false;
			int startPage=1;
			int endPage=Integer.MAX_VALUE;
			PDDocument document=null;
//			document=PDDocument.load(file);
			document=PDDocument.load(is);
			PDFTextStripper stripper=new PDFTextStripper();
			stripper.setSortByPosition(sort);
			stripper.setStartPage(startPage);
			stripper.setEndPage(endPage);
			//生成txt文件
			String string=stripper.getText(document);
			document.close();
			return string;
		}
	    /** 
	     * 获取response要下载的文件的默认路径 
	     * @param response 
	     * @return 
	     */  
	    public static String getFilePath(HttpResponse response) {  
	        String filepath = root + splash;  
	        String filename = getFileName(response);  
	  
	        if (filename != null) {  
	            filepath += filename;  
	        } else {  
	            filepath += getRandomFileName();  
	        }  
	        return filepath;  
	    }  
	    /** 
	     * 获取response header中Content-Disposition中的filename值 
	     * @param response 
	     * @return 
	     */  
	    public static String getFileName(HttpResponse response) {  
	        Header contentHeader = response.getFirstHeader("Content-Disposition");  
	        String filename = null;  
	        if (contentHeader != null) {  
	            HeaderElement[] values = contentHeader.getElements();  
	            if (values.length == 1) {  
	                NameValuePair param = values[0].getParameterByName("filename");  
	                if (param != null) {  
	                    try {  
	                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");  
	                        //filename=URLDecoder.decode(param.getValue(),"utf-8");  
	                        filename = param.getValue();  
	                    } catch (Exception e) {  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }  
	        }  
	        return filename;  
	    }  
	    /** 
	     * 获取随机文件名 
	     * @return 
	     */  
	    public static String getRandomFileName() {  
	        return String.valueOf(System.currentTimeMillis());  
	    }  
	    public static void outHeaders(HttpResponse response) {  
	        Header[] headers = response.getAllHeaders();  
	        for (int i = 0; i < headers.length; i++) {  
	            System.out.println(headers[i]);  
	        }  
	    }  
	    public static void main(String[] args) {  
//	      String url = "http://bbs.btwuji.com/job.php?action=download&pid=tpc&tid=320678&aid=216617";  
	        String url="http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/7e222f012a01431cb03c0371630e1f34.pdf";  
//	      String filepath = "D:\\test\\a.torrent";  
	        String filepath = "D:\\ssa.pdf"; 
	        HttpDownload.download(url, filepath);  
	    }  
}
