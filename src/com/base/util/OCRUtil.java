package com.base.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;


public class OCRUtil{
	/**
	 * 放大缩小(按比例)
	 * 作者：郑建璋
	 * 创建时间：2016年9月26日上午11:06:51
	 * @param bi 原图片
	 * @param scale
	 * @param flag
	 * @return
	 */
	public static BufferedImage scaled(BufferedImage bi,int scale,boolean flag){
		int width = bi.getWidth(); // 得到源图宽
        int height = bi.getHeight(); // 得到源图长
        if (flag) {// 放大
            width = width * scale;
            height = height * scale;
        } else {// 缩小
            width = width / scale;
            height = height / scale;
        }
        Image image=bi.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage newImg=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g=newImg.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        return newImg;
	}
	 /**
	 * 放大缩小(按宽高)
	 * 作者：郑建璋
	 * 创建时间：2016年9月28日上午8:51:17
	 * @param srcImageFile
	 * @param result
	 * @param height
	 * @param width
	 * @param bb
	 */
	public final static BufferedImage scale(BufferedImage bi,int height, int width, boolean bb) {
        double ratio = 0.0; // 缩放比例
        Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // 计算比例
        if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
            if (bi.getHeight() > bi.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / bi.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue() / bi.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform
                    .getScaleInstance(ratio, ratio), null);
            itemp = op.filter(bi, null);
        }
        if (bb) {//补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == itemp.getWidth(null))
                g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                        itemp.getWidth(null), itemp.getHeight(null),
                        Color.white, null);
            else
                g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                        itemp.getWidth(null), itemp.getHeight(null),
                        Color.white, null);
            g.dispose();
            itemp = image;
        }
        return (BufferedImage) itemp;
	}
	public static BufferedImage cut(){
		return null;
	}
	/**
	 * 倾斜矫正
	 * 作者：郑建璋
	 * 创建时间：2016年9月26日下午1:57:12
	 * @param bi
	 * @param radian
	 * @return
	 */
	public static BufferedImage tiltCorrect(BufferedImage bi,double radian){
		double moveUnit=(bi.getHeight()*radian)/bi.getHeight();
		int width=bi.getWidth();
		int height=bi.getHeight();
		BufferedImage newBi=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for(int x=0;x<width;x++){
			for(int y=height-1;y>=0;y--){
				if(y>0&&y<height-1){
					if(x<width-((radian*height)+1)){
						int factor=height-y;
						int move=(int)(moveUnit*factor);
						newBi.setRGB(x, y, bi.getRGB(x+move, y));
					}else{
						newBi.setRGB(x, y, Color.WHITE.getRGB());
					}
				}else{
					newBi.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
		return newBi;
	}
    /**
     * 过滤干扰元素
     * 作者：郑建璋
     * 创建时间：2016年9月26日下午5:32:45
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static BufferedImage grayByBinary(BufferedImage bufferedImage){  
        int h = bufferedImage.getHeight();  
        int w = bufferedImage.getWidth();  
        // 灰度化  
        int[][] gray = new int[w][h];  
        for (int x = 0; x < w; x++)  
        {  
            for (int y = 0; y < h; y++)  
            {  
                int argb = bufferedImage.getRGB(x, y);  
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = (argb >> 0) & 0xFF;
                int grayPixel = (b * 29 + g * 150 + r * 77 + 128) >> 8;
                gray[x][y] = grayPixel;
            }  
        }  
        //二值化  
        int threshold = ostu(gray, w, h);  
        BufferedImage binaryBufferedImage = new BufferedImage(w, h,  
                BufferedImage.TYPE_BYTE_BINARY);  
        for (int x = 0; x < w; x++)
        {  
            for (int y = 0; y < h; y++)  
            {  
                if (gray[x][y] > threshold)  
                {  
                    gray[x][y] |= 0x00FFFF;  
                } else  
                {  
                    gray[x][y] &= 0xFF0000;  
                }  
                binaryBufferedImage.setRGB(x, y, gray[x][y]);  
            }  
        }  
        return binaryBufferedImage;
    }  
    
    public static BufferedImage clearBodor(BufferedImage bi){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	BufferedImage nbi=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    	for(int x=0;x<width;x++){
    		for(int y=0;y<height;y++){
    			if(x==0||x==width-1||y==0|y==height-1){
    				nbi.setRGB(x, y, Color.WHITE.getRGB());
    			}else{
    				nbi.setRGB(x, y, bi.getRGB(x, y));
    			}
    		}
    	}
    	return nbi;
    }
    /**
     * 判断是否是黑色
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:22:25
     * @param colorInt
     * @return
     */
    public static boolean isBlack(int colorInt)  
    {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() <= 500)  
        {  
            return true;  
        }  
        return false;  
    }  
    
    /**
     * 判断是否是白色
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:22:10
     * @param colorInt
     * @return
     */
    public static boolean isWhite(int colorInt)  
    {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() > 500)  
        {  
            return true;  
        }  
        return false;  
    }  
  
    /**
     * 判断是否是黑色或者白色
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:21:31
     * @param colorInt
     * @return
     */
    public static boolean isBlackOrWhite(int colorInt)  
    {  
        if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730)  
        {  
            return true;  
        }  
        return false;  
    }  
  
    /**
     * 获取颜色RGB三基色总值
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:20:43
     * @param colorInt
     * @return
     */
    public static int getColorBright(int colorInt)  
    {  
        Color color = new Color(colorInt);  
        return color.getRed() + color.getGreen() + color.getBlue();  
    }  
  
    /**
     * 二值化
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:20:28
     * @param gray
     * @param w
     * @param h
     * @return
     */
    public static int ostu(int[][] gray, int w, int h)  
    {  
        int[] histData = new int[w * h];  
        for (int x = 0; x < w; x++){  
            for (int y = 0; y < h; y++){  
                int red = 0xFF & gray[x][y];  
                histData[red]++;  
            }  
        }  
        int total = w * h;  
        float sum = 0;  
        for (int t = 0; t < 256; t++) sum += t * histData[t];  
        float sumB = 0;  
        int wB = 0;  
        int wF = 0;  
        float varMax = 0;  
        int threshold = 0;  
        for (int t = 0; t < 256; t++){  
            wB += histData[t];
            if (wB == 0) continue;  
            wF = total - wB;
            if (wF == 0) break;  
            sumB += t * histData[t];  
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF; 
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);  
            if (varBetween > varMax){  
                varMax = varBetween;  
                threshold = t;  
            }  
        }  
        return threshold;  
    }
    /**
     * 按color值过滤干扰像素
     * 作者：郑建璋
     * 创建时间：2016年9月26日下午5:36:41
     * @param bi
     * @param color
     */
    public static void clearInterfere(BufferedImage bi,int color){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	for (int x = 0;x<width; x++) {
    		for(int y=0;y<height;y++){
    			if(bi.getRGB(x, y)==color){
    				bi.setRGB(x, y, Color.WHITE.getRGB());
    			}
    		}
		}
    }
    /**
     * 按Green值过滤干扰像素
     * 作者：郑建璋
     * 创建时间：2016年9月26日下午5:36:41
     * @param bi
     * @param color
     */
    public static void clearInterfereForGreen(BufferedImage bi,boolean flag,int threshold){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	for (int x = 0;x<width; x++) {
    		for(int y=0;y<height;y++){
    			Color color=new Color(bi.getRGB(x, y));
    			if(flag){
    				if(color.getGreen()==threshold){
        				bi.setRGB(x, y, Color.WHITE.getRGB());
        			}
    			}else{
    				if(color.getGreen()!=threshold){
        				bi.setRGB(x, y, Color.WHITE.getRGB());
        			}
    			}
    		}
		}
    }
    /**
     * 按RGB三基色总值过滤干扰像素
     * 作者：郑建璋
     * 创建时间：2016年9月26日下午5:36:41
     * @param bi
     * @param color
     */
    public static void clearInterfereForColorValue(BufferedImage bi,boolean flag,int threshold){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	for (int x = 0;x<width; x++) {
    		for(int y=0;y<height;y++){
    			Color color=new Color(bi.getRGB(x, y));
    			if(flag){
    				if((color.getGreen()+color.getBlue()+color.getRed())<=threshold){
        				bi.setRGB(x, y, Color.WHITE.getRGB());
        			}
    			}else{
    				if((color.getGreen()+color.getBlue()+color.getRed())>threshold){
        				bi.setRGB(x, y, Color.WHITE.getRGB());
        			}
    			}
    		}
		}
    }
    /**
     * 灰化
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:13:50
     * @param bi
     * @return
     */
    public static BufferedImage gray(BufferedImage bi){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	BufferedImage newBi=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    	for (int x = 0;x<width; x++) {
    		for(int y=0;y<height;y++){
    			newBi.setRGB(x, y, bi.getRGB(x, y));
    			Color color=new Color(newBi.getRGB(x, y));
    			if(color.getRed()>130){
    				newBi.setRGB(x, y, Color.WHITE.getRGB());
    			}
    		}
		}
    	return newBi;
    }
    /**
     * 过滤麻点干扰像素
     * 作者：郑建璋
     * 创建时间：2016年9月26日下午5:36:41
     * @param bi
     * @param color
     */
    public static BufferedImage clearDot(BufferedImage bi){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	BufferedImage newBi=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    	try {
        	for (int x = 0;x<width; x++) {
        		for(int y=0;y<height;y++){
        			if(x>0&&x<width-1&&y>0&&y<height-1){
        				if(isBlack(bi.getRGB(x, y))){
            				int count=0;
            				if(isBlack(bi.getRGB(x, y+1))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x+1, y))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x+1, y+1))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x-1, y))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x, y-1))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x-1, y-1))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x-1, y+1))){
            					count++;
            				}
            				if(isBlack(bi.getRGB(x+1, y-1))){
            					count++;
            				}
            				if(count<2){
            					newBi.setRGB(x, y, Color.WHITE.getRGB());
            				}else{
            					newBi.setRGB(x, y, bi.getRGB(x, y));
            				}
            			}else{
            				newBi.setRGB(x, y, bi.getRGB(x, y));
            			}
        			}else{
        				newBi.setRGB(x, y, Color.WHITE.getRGB());
        			}
        			
        		}
    		}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return newBi;
    }
    /**
     * 自动分解图片块（正切）
     * 作者：郑建璋
     * 创建时间：2016年10月10日上午10:08:08
     * @param bi
     * @param num(分割条件：分割线上存在黑色像素点的数量，如果满足则定为分割起始或终止位置)
     * @return
     * @throws IOException
     */
    public static List<BufferedImage> autoCut(BufferedImage bi,int num) throws IOException{
    	List<BufferedImage> biList=new ArrayList<BufferedImage>();
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	int startX=0;
		int endX=0;
    	for(int x=0;x<width;x++){
    		int lineCount=0;
    		for(int y=0;y<height;y++){
    			if(isBlack(bi.getRGB(x, y))){
    				lineCount++;
    			}
    		}
    		if(lineCount>num&&startX==0){
    			startX=x;
    		}
    		if((lineCount<=num&&startX>0)||x==width-1){
    			endX=x;
    		}
    		if(startX>0&&endX>0){
    			BufferedImage childBi=bi.getSubimage(startX, 0, endX-startX, height);
    			startX=0;
        		endX=0;
        		biList.add(childBi);
    		}
    	}
    	return biList;
    }
   
    /**
     * 色值比较
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午9:16:53
     * @param color1
     * @param color2
     * @return
     */
    public static boolean compare(int color1,int color2){
    	if(isBlack(color1)&&isBlack(color2)){
    		return true;
    	}
    	if(isWhite(color1)&&isWhite(color2)){
    		return true;
    	}
    	return false;
    }
    /**
     * 输出图片
     * 作者：郑建璋
     * 创建时间：2016年9月28日上午8:42:26
     * @param bi
     * @param fileName
     * @param filePath
     */
    public static void imgOut(BufferedImage bi,String fileName,String filePath){
    	try {
    		new File(filePath).mkdirs();
    		File file =new File(filePath+File.separatorChar+fileName+".jpg");
       	 	ImageIO.write(bi, "jpg", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 读取图片
     * 作者：郑建璋
     * 创建时间：2016年10月10日上午10:37:03
     * @param filePath
     * @param imgName
     * @return
     */
    public static BufferedImage imgIn(String filePath,String imgName){
    	BufferedImage bi=null;
    	try {
    		File file=new File(filePath+File.separatorChar+imgName);
    		FileInputStream fin = new FileInputStream(file);
    		bi=ImageIO.read(fin);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return bi;
    }
    /**
     * 读取图片列表
     * 作者：郑建璋
     * 创建时间：2016年10月10日上午10:37:03
     * @param filePath
     * @param imgName
     * @return
     */
    public static List<BufferedImage> imgIn(String filePath){
    	List<BufferedImage> biList=new ArrayList<>();
    	File testDataDir = new File(filePath);  
        for (File file : testDataDir.listFiles())
        {  
        	try {
        		FileInputStream fin = new FileInputStream(file);
                BufferedImage bi = ImageIO.read(fin);
                biList.add(bi);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }  
    	return biList;
    }
    /**
     * 读取图片列表
     * 作者：郑建璋
     * 创建时间：2016年10月10日上午10:37:03
     * @param filePath
     * @param imgName
     * @return
     */
    public static Map<BufferedImage,String> imgTempIn(String filePath){
    	Map<BufferedImage,String> tempMap=new HashMap<>();
    	File testDataDir = new File(filePath);  
        for (File file : testDataDir.listFiles())
        {  
        	try {
        		FileInputStream fin = new FileInputStream(file);
                BufferedImage bi = ImageIO.read(fin);
                tempMap.put(bi, file.getName().replace(".jpg", ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
        }  
    	return tempMap;
    }
    /**
     * 消除锯齿（微作用）
     * 作者：郑建璋
     * 创建时间：2016年9月28日下午4:20:36
     * @param bi
     * @return
     */
    public static BufferedImage sawToothOp(BufferedImage bi){
    	BufferedImage distImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
    	Graphics2D graphics2d = distImage.createGraphics();  
    	graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
    	graphics2d.drawImage(bi.getScaledInstance(bi.getWidth(), bi.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
    	return distImage;
    }
    
    /**
     * 斜切
     * 作者：郑建璋
     * 创建时间：2016年10月10日上午10:05:18
     * @param bi
     * @param rate斜度(弧度)
     * @param num(分割条件：分割线上存在黑色像素点的数量，如果满足则定为分割起始或终止位置)
     * @return
     */
    public static List<BufferedImage> autoSlantCut(BufferedImage bi,double rate,int num){
    	int width=bi.getWidth();
    	int height=bi.getHeight();
    	int startX=0;
		int endX=0;
		int cUtil=(int) ((height-2)*rate);
    	List<BufferedImage> biList=new ArrayList<>();
    	for(int x=0;x<width;x++){
    		int lineCount=0;
    		for(int y=0;y<height;y++){
    			if(x>=cUtil-1&&x<=width-cUtil-1){
    				if(y>0&&y<height-1){
    					int util=(int) ((y-1)*rate);
        				if(isBlack(bi.getRGB(x-util, y))){
        					lineCount++;
        				}
        				if(lineCount>num&&startX==0){
        	    			startX=x;
        	    		}
        	    		if((lineCount<=num&&startX>0&&y==height-2)||x==width-1){
        	    			endX=x;
        	    		}
        	    		if(startX>0&&endX>0){
        	    			BufferedImage childBi=new BufferedImage((endX-startX)+cUtil*2, height, BufferedImage.TYPE_BYTE_GRAY);
        	    			int childWidth=childBi.getWidth();
        	    			int chileHeight=childBi.getHeight();
        	    			Graphics2D g= childBi.createGraphics();
        	    	    	g.setColor(Color.WHITE);
        	    	    	g.fillRect(0, 0, childWidth, chileHeight);
        	    			for(int childX=0;childX<childWidth;childX++){
        	    				for(int childY=0;childY<chileHeight;childY++){
        	    					if(childY>0&&childY<chileHeight-2){
        	    						int childUtil=(int) ((childY-1)*rate);
        	    						if(childX>=cUtil&&(startX-cUtil+childX)<=endX){
        	    							childBi.setRGB(childX-childUtil, childY, bi.getRGB((startX-childUtil+(childX-cUtil)), childY));
        	    						}
        	    					}
        	    				}
        	    			}
        	    			startX=0;
        	        		endX=0;
        	        		biList.add(childBi);
        	    		}
    				}
    			}
    		}
    	}
    	return biList;
    }
    
}
