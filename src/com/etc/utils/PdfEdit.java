package com.etc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称：PdfEdit.java
 * 修订记录：
 * 序号	日期				作者(操作：具体内容)
 * 1    2017年6月1日			宋少挺(创建：创建文件)
 *==========================================
 *类描述：pdf处理类
 */
public class PdfEdit {
	//pdf读取器
	private PdfReader reader;
	//文件输出流
	private FileOutputStream out;
	//字节输出流
	private ByteArrayOutputStream bos;
	//pdf编辑器
	private PdfStamper pdfStamper;
	//pdf文本域集
	private AcroFields fields;
	//字体
	private BaseFont bfChinese;
	
	public PdfEdit(String path){
		if(ValidateUtil.isAliUrl(path)){
			try {
				File file=new File(new URI("http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/7fea0c0eb14d419aa9428944129090d7.pdf"));
				out=new FileOutputStream(file);
				reader=new PdfReader(new URL(path));
				bos=new ByteArrayOutputStream();
				pdfStamper=new PdfStamper(reader, bos);
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 
				Font chineseFont= new Font(bfChinese, 12, Font.NORMAL);
				fields=pdfStamper.getAcroFields();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public void setField(String key,String value){
		if(StringUtils.isNotBlank(key)){
			try {
				fields.setFieldProperty(key, "textfont", bfChinese, null);
				fields.setField(key, value);
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setFields(Map<String, String> map){
		if(map!=null){
			Set<String> keys=map.keySet();
			String value="";
			try {
				for(String key:keys){
					value=map.get(key);
					fields.setFieldProperty(key, "textfont", bfChinese, null);
					fields.setField(key, value);
				}
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setFieldImg(String key,Image image){
		try {
			PdfWriter writer=pdfStamper.getWriter();
			List<FieldPosition> positions=fields.getFieldPositions(key);
			//image.set
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void outFileToLocal(String path){
		if(StringUtils.isBlank(path)){
			return;
		}
		try {
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			reader.close();
			out=new FileOutputStream(path);
			out.write(bos.toByteArray());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] outFileToDownload(){
		byte[] bytes = null;
		try {
			pdfStamper.setFormFlattening(true);
			pdfStamper.close();
			reader.close();
			bytes=bos.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static void main(String[] args) {
		PdfEdit pdfEdit=new PdfEdit("http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/7fea0c0eb14d419aa9428944129090d7.pdf");
		pdfEdit.setField("fill_1", "易运输");
	//	pdfEdit.setFieldImg("fill_1", null);
		pdfEdit.outFileToLocal("D:\\工作\\pdf\\out.pdf");
	}
}
