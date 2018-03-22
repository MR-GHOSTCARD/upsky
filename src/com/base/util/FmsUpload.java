package com.base.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import sun.misc.BASE64Decoder;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: AccountUtil.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年3月11日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：上传
 */
public class FmsUpload {
	/**
	 * 生成图片(保存至阿里云)
	 * 作者：林辉
	 * 创建时间：2016年3月15日 下午4:18:44
	 * @param imgStr
	 * @return
	 */
	public static String savePic(String imgStr){
		
		if (imgStr == null) // 图像数据为空
            return "";
		imgStr = imgStr.replaceAll("data:image/(.*);base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            
            // 生成jpeg图片
//            OutputStream out = new FileOutputStream("/home/linh/log/b.jpg");
            InputStream inputStream = new ByteArrayInputStream(bytes); 
            String uploadPath=UUID.randomUUID().toString().replaceAll("-","");
            String uri = SaveFileToOss.save(inputStream,uploadPath);
            return uri;
//            out.write(bytes);
//            out.flush();
//            out.close();
            
            
//            return "/home/linh/log/b.jpg";
        } catch (Exception e) {
            return "";
        }
	}
	
	public static String savePicByByte(byte[] bytes){
		
        try {
            // Base64解码
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            
            // 生成jpeg图片
//            OutputStream out = new FileOutputStream("/home/linh/log/b.jpg");
            InputStream inputStream = new ByteArrayInputStream(bytes); 
            String uploadPath=UUID.randomUUID().toString().replaceAll("-","");
            String uri = SaveFileToOss.save(inputStream,uploadPath);
            return uri;
//            out.write(bytes);
//            out.flush();
//            out.close();
            
            
//            return "/home/linh/log/b.jpg";
        } catch (Exception e) {
            return "";
        }
	}
}
