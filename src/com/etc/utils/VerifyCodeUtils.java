package com.etc.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.etc.utils.modules.VerifyCode;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.org.jvnet.staxex.Base64EncoderStream;

import sun.misc.BASE64Encoder;


public class VerifyCodeUtils {
	public static final String VERIFY_CODES="23456789ABCDEFGHIJKLMNPQRSTUVMXYZ";
	private static Random random=new Random();
	
	/**
	 * 使用系统默认字符源生成验证码
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午2:38:34
	 * @param verifySize
	 * @return
	 */
	private String generateVerifyCode(int verifySize){
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}
	
	/**
	 * 使用指定源生成验证码
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午2:38:21
	 * @param verifySize
	 * @param sources
	 * @return
	 */
	private String generateVerifyCode(int verifySize,String sources){
		if(sources==null||sources.length()==0){
			sources=VERIFY_CODES;
		}
		int codesLen=sources.length();
		Random random=new Random();
		StringBuilder verifyCode=new StringBuilder(verifySize);
		for(int i=0;i<verifySize;i++){
			verifyCode.append(sources.charAt(random.nextInt(codesLen-1)));
		}
		return verifyCode.toString();
	}
	
	/**
	 * 输出验证码图片文件并返回验证码
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午3:39:38
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param verifySize
	 * @return
	 */
	public String outputVerifyImage(int w,int h,File outputFile,int verifySize){
		String verifyCode=generateVerifyCode(verifySize);
		outputImage(w, h, outputFile, verifyCode);
		return verifyCode;
	}
	
	/**
	 * 输出验证码图片流，并返回验证码值
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午3:37:59
	 * @param w
	 * @param h
	 * @param os
	 * @param verifySize
	 * @return
	 */
	public String outputVerifyImage(int w,int h,OutputStream os,int verifySize){
		String verifyCode=generateVerifyCode(verifySize);
		outputImage(w, h, os, verifyCode);
		return verifyCode;
	}
	
	public VerifyCode outputVerifyImageByBase64(int w,int h,OutputStream os,int verifySize){
		VerifyCode verifyCode=new VerifyCode();
		String code=generateVerifyCode(verifySize);
		verifyCode.setCode(code);
		outputImage(w, h, os, code);
		ByteArrayOutputStream byteArrayOutputStream=(ByteArrayOutputStream)os;
		String base64Img=base64Encoder(byteArrayOutputStream.toByteArray());
		verifyCode.setUrl(base64Img);
		return verifyCode;
	}
	
	/**
	 * 生成指定验证码图像文件
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午3:35:10
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param code
	 */
	public void outputImage(int w,int h,File outputFile,String code){
		if(outputFile==null){
			return;
		}
		File dir=outputFile.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		try {
			outputFile.createNewFile();
			FileOutputStream fileOutputStream=new FileOutputStream(outputFile);
			outputImage(w, h, fileOutputStream, code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出图片流
	 * 作者：宋少挺
	 * 创建时间：2016年11月24日 下午3:30:12
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 */
	private void outputImage(int w,int h,OutputStream os,String code){
		int verifySize=code.length();
		//创建一个具有8位RGB颜色分量的图像缓冲区
		BufferedImage image=new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		
		Random random=new Random();
		
		//创建对应图像的坐标空间 向量
		Graphics2D g2=image.createGraphics();
		//设置首选项 抗锯齿
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color[] colors=new Color[5];
		Color[] colorSpaces=new Color[]{Color.WHITE,Color.CYAN,Color.GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
		float[] fractions=new float[colors.length];
		for(int i=0;i<colors.length;i++){
			//随机获取一组颜色
			colors[i]=colorSpaces[random.nextInt(colorSpaces.length)];
			fractions[i]=random.nextFloat();
		}
		Arrays.sort(fractions);
		
		//设置边框
		g2.setColor(Color.GRAY);
		//绘制矩形
		g2.fillRect(0, 0, w, h);
		
		Color c=getRandColor(200, 250);
		//设置背景
		g2.setColor(c);
		g2.fillRect(0, 0, w, h-4);
		
		//绘制干扰线
		Random random2=new Random();
		for(int i=0;i<20;i++){
			g2.setColor(getRandColor(random.nextInt(100)+50, random.nextInt(50)+151));
			int x=random2.nextInt(w)+1;
			int y=random2.nextInt(10)+1;
			int x1=random2.nextInt(w)+1;
			int y1=h-random2.nextInt(10);
			g2.drawLine(x1, y, x,y1);
		}
		
		//添加噪点
		float yawpRate=0.2f;
		int area=(int)(yawpRate*w*h);
		for(int i=0;i<area;i++){
			g2.setColor(getRandColor(random.nextInt(100)+50, random.nextInt(50)+151));
			int x=random2.nextInt(w);
			int y=random2.nextInt(h);
			int rgb=getRandomIntColor();
			image.setRGB(x, y, rgb);
		}
		
		//使图片扭曲
		shear(g2, w, h, c);
		
		//绘制字体
		int fontSize=h-4;
		Font font=new Font("Algerian",Font.ITALIC,fontSize);
		g2.setFont(font);
		char[] chars=code.toCharArray();
		for(int i=0;i<verifySize;i++){
			g2.setColor(getRandColor(random.nextInt(100)+50, random.nextInt(50)+151));
			AffineTransform affine=new AffineTransform();
			affine.setToRotation(Math.PI/4*random.nextDouble()*(random.nextBoolean()?1:-1),(w/verifySize)*i+fontSize/2,h/2);
			g2.setTransform(affine);
			g2.drawChars(chars, i, 1,((w-10)/verifySize)*i+5,h/2+fontSize/2-10);
		}
		//释放资源
		g2.dispose();
		try {
			ImageIO.write(image, "jpg", os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private  Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
     
    private int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }
     
    private int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }
 
    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }
     
    private static void shearX(Graphics g, int w1, int h1, Color color) {
 
        int period = random.nextInt(2);
 
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
 
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
 
    }
 
    private void shearY(Graphics g, int w1, int h1, Color color) {
 
        int period = random.nextInt(40) + 10; // 50;
 
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                            + (6.2831853071795862D * (double) phase)
                            / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
 
        }
 
    }
    
   /**
    * 将字节进行base64编码
    * 作者：宋少挺
    * 创建时间：2016年11月24日 下午4:46:57
    * @param data
    * @return
    */
    public String base64Encoder(byte[] data){
    	String string="";
    	BASE64Encoder encoder=new BASE64Encoder();
    	string=encoder.encode(data);
    	return string;
    }
    
    public VerifyCode getVerifyCodePic(HttpServletRequest request){
    	VerifyCode verifyCode=new VerifyCode();
    	String filePath=request.getSession().getServletContext().getRealPath("/")+"WEB-INF/images";
    	File dir=new File(filePath);
    	int w=200,h=80;
    	String code=generateVerifyCode(4);
    	verifyCode.setCode(code);
    	String pic=new UUIDtest().getUUID()+".jpg";
    	verifyCode.setUrl(filePath+"/"+pic);
    	File file=new File(dir,pic);
    	outputImage(w,h,file,code);
    
    	return verifyCode;
    }
    public VerifyCode getVerifyCodePic(){
    	VerifyCode verifyCode=new VerifyCode();
    	int w=200,h=80;
    	verifyCode=outputVerifyImageByBase64(w, h, new ByteArrayOutputStream(), 4);
    	return verifyCode;
    }
    public static void main(String[] args) {
		VerifyCodeUtils verifyCodeUtils=new VerifyCodeUtils();
		File dir=new File("d:/yanz");
		int w=200,h=80;
		for(int i=0;i<10;i++){
			String verifyCode=verifyCodeUtils.generateVerifyCode(4);
			File file=new File(dir, verifyCode+".jpg");
			verifyCodeUtils.outputImage(w,h,file,verifyCode);
		}
	}
}
