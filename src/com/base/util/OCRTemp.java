package com.base.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;




public class OCRTemp {
	public final static Map<String, Map<BufferedImage, String>> temp_map=new HashMap<>();
	public static void putTemp(String key,String str,int tempHeight,String fontFamily,int fontStyle,int fontSize,int positionY) throws IOException{
		Map<BufferedImage, String> temp=new HashMap<>();
		BufferedImage tempBi=createTemp(str.replace(",", " "),900, tempHeight, fontFamily, fontStyle, fontSize, positionY);
		OCRUtil.imgOut(tempBi, "temp1", "E:\\codeImage\\test");
		List<BufferedImage> biList=OCRUtil.autoCut(tempBi,0);
    	int i=0;
    	for(BufferedImage b:biList){
    		if(i<str.split(",").length){
    			temp.put(b, str.split(",")[i]);
        		i++;
    		}
    	}
		temp_map.put(key, temp);
	}
	public static void putTemp(String key,String filePath) throws IOException{
		Map<BufferedImage, String> temp=new HashMap<>();
		final File dir = new File(filePath);
		final File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jpg");
			}
		});
		for (final File file : files) {
			temp.put(ImageIO.read(file), file.getName().charAt(0) + "");
		}
		temp_map.put(key, temp);
	}
	public static Map<BufferedImage, String> getTemp(String key){
		return temp_map.get(key);
	}
    /**
     * 创建样本
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:17:27
     * @return
     * @throws IOException
     */
    public static BufferedImage createTemp(String temp,int width,int height,String fontFamily, int fontType, int fontSize,int d_y) throws IOException{
		BufferedImage bui=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g=bui.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
        Font font =new Font(fontFamily, fontType, fontSize);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(temp, 0, d_y);
        return bui;
    }
	 /**
     * 识别图片中的字符（大小写字母、数字）
     * 作者：郑建璋
     * 创建时间：2016年9月27日上午11:33:22
     * @param bi
     * @return
     * @throws IOException
     * @throws FontFormatException
     */
    public static String changeToString(BufferedImage bi,String key) throws IOException{
    	Map<BufferedImage, String> temp=temp_map.get(key);
    	if(temp==null||temp.isEmpty()){
    		throw new RuntimeException("");
    	}
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	double numA=width*height;
    	Map<Double, String> resultList=new HashMap<>();
    	for(BufferedImage tempBi:temp.keySet()){
    		int tempWidth=tempBi.getWidth();
    		int tempHeight=tempBi.getHeight();
    		double numB=tempWidth*tempHeight;
    		int x_width=0;
			int y_hieght=0;
			if(numA>numB){
				x_width=tempWidth;
				y_hieght=tempHeight;
			}else{
				x_width=width;
				y_hieght=height;
			}
			int blackCount=0;
			int count =0;
			for(int x=0;x<x_width;x++){
				for(int y=0;y<y_hieght;y++){
					if(OCRUtil.isBlack(bi.getRGB(x, y))){
						blackCount++;
					}
					if(OCRUtil.isBlack(tempBi.getRGB(x, y))){
						blackCount++;
					}
					if(OCRUtil.isBlack(bi.getRGB(x, y))&&OCRUtil.isBlack(tempBi.getRGB(x, y))){
						count++;
					}
				}
			}
			double rate=count/((double)blackCount/2);
			resultList.put(rate, temp.get(tempBi));
    	}
    	return getMaxRateString(resultList);
    }
    /**
     * 获取匹配度最大的字符
     * 作者：郑建璋
     * 创建时间：2016年9月28日下午4:20:57
     * @param map
     * @return
     */
    public static String getMaxRateString(Map<Double, String> map){
    	List<Double> rateList=new ArrayList<>();
    	for(Double rate:map.keySet()){
    		rateList.add(rate);
    	}
    	Collections.sort(rateList);
    	if(!rateList.isEmpty()){
    		double rate=rateList.get(rateList.size()-1);
    		if(rate>=0.65){
    			return map.get(rate);
    		}
    	}
    	return null;
    }
    
    /**
     * 创建样本
     * 作者：郑建璋
     * 创建时间：2016年10月21日上午9:51:25
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	
//    	Map<String, String> requestHead=new HashMap<>();
//    	requestHead.put("Accept", "image/webp,image/*,*/*;q=0.8");
//    	putTemp("sinopec", "D:\\codeImage\\test");
//		HttpClientTool httpClient=new HttpClientTool();
//		for(int i=0;i<10;i++){
//			byte[] imageByte=httpClient.sendHttpGet("http://m.sinopecsales.com/websso/YanZhengMaServlet?"+Math.random(),requestHead);
//			InputStream input = new ByteArrayInputStream(imageByte);
//			BufferedImage maskBi=ImageIO.read(input);
//			List<BufferedImage> biList=OCRUtil.autoCut(OCRUtil.clearBodor(OCRUtil.grayByBinary(maskBi)),0);
//			for(BufferedImage bi:biList){
//				String s=changeToString(bi, "sinopec");
//				OCRUtil.imgOut(bi, s, "D:\\codeImage\\test1");
//			}
//		}
		try {
//			System.out.println(getMaskStr());
			savePic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    /**
     * 获取
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:14:11
     * @param bi
     * @return
     */
    public static Set<Integer> getColorValue(BufferedImage bi){
    	Set<Integer> colorValieSet=new HashSet<>();
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	for (int x = 0;x<width; x++) {
    		for(int y=0;y<height;y++){
    			if((y==0||y==height-1)||(x==0||x==width-1)){
    				colorValieSet.add(bi.getRGB(x, y));
    			}
    		}
		}
    	return colorValieSet;
    }
    
    public static void savePic() throws IOException{
    	Map<String, String> requsetHead=new HashMap<>();
		HttpClientTool httpClient=new HttpClientTool();
		requsetHead.put("Accept", "image/webp,image/*,*/*;q=0.8");
		
		String filePath="D:\\codeImage\\test";
		String fileName="";
		File dir=new File(filePath);
		if(!dir.exists()&&!dir.isDirectory()){
			dir.mkdirs();
		}
		FileOutputStream fos=null;
		File file=null;
		for(int i=1;i<=100;i++){
			byte[] imageByte=httpClient.sendHttpGet("http://m.sinopecsales.com/websso/YanZhengMaServlet?"+Math.random(),requsetHead);
			fileName="\\f"+i+".tiff";
			file=new File(filePath+fileName);
			fos=new FileOutputStream(file);
			fos.write(imageByte, 0, imageByte.length);
			System.out.println(i+"success");
		}
		
		
    }
    
    public static String getMaskStr() throws Exception{
    	createTemp("D:\\codeImage\\temp");
    	
		Map<String, String> requsetHead=new HashMap<>();
		HttpClientTool httpClient=new HttpClientTool();
		requsetHead.put("Accept", "image/webp,image/*,*/*;q=0.8");
		byte[] imageByte=httpClient.sendHttpGet("http://m.sinopecsales.com/websso/YanZhengMaServlet?"+Math.random(),requsetHead);
		
		String filePath="D:\\codeImage\\test";
		String fileName="\\f2.jpg";
		File dir=new File(filePath);
		if(!dir.exists()&&!dir.isDirectory()){
			dir.mkdirs();
		}
		File file=new File(filePath+fileName);
		FileOutputStream fos=new FileOutputStream(file);
		fos.write(imageByte, 0, imageByte.length);
		
		InputStream input = new ByteArrayInputStream(imageByte);
		BufferedImage maskBi=ImageIO.read(input);
		List<BufferedImage> childBiList=OCRUtil.autoCut(OCRUtil.clearBodor(OCRUtil.grayByBinary(maskBi)),0);
		List<String> strList=new ArrayList<>();
		if(childBiList.size()==5){
			for(BufferedImage childBi:childBiList.subList(0, 3)){
				String str=OCRTemp.changeToString(childBi,"sinopec");
				if(str!=null){
					strList.add(str);
				}
			}
			if(strList.size()==3){
				int a=0;
				int b=0;
				if(ValidateUtil.isNumber(strList.get(0))){
					a=Integer.parseInt(strList.get(0));
				}else{
					return null;
				}
				if(ValidateUtil.isNumber(strList.get(2))){
					b=Integer.parseInt(strList.get(2));
				}else{
					return null;
				}
				if("+".equals(strList.get(1))){
					return String.valueOf(a+b);
				}else if("X".equals(strList.get(1))){
					return String.valueOf(a*b);
				}else{
					return null;
				}
			}
		}
		return null;
	}
    
    public static void createTemp(String filePath) {
		System.out.println("------创建图形验证码识别样板------");
		try {
			String str="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9";
			OCRTemp.putTemp("ncpc", str, 22, Font.DIALOG, Font.BOLD+Font.ITALIC, 16, 17);
			String str1="1,2,3,4,5,6,7,8,9,0";
		    filePath=filePath.split("classes")[0];
		    String  filePath1=filePath+"sinopecTemp";
			OCRTemp.putTemp("sinopec", filePath1);
			String filePath2=filePath+"etcTemper_shandong";
			OCRTemp.putTemp("etc_shandong", filePath2);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("------图形验证码识别样板创建失败------");
		}
	}
}
