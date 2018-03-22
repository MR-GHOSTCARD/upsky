package com.etc.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Iterator;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public class PdfTest {
	public void fillTemplate(){
		//模板路径
		//String templatePath="D:\\工作\\pdf\\test.pdf";
		String templatePath="http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/e5880579d34648a1b4d9e8baeff13ebd.pdf";
		//生成的新文件路径
		String newPDFPath="D:\\工作\\pdf\\out.pdf";
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		
		try {
			//输出流
			out=new FileOutputStream(newPDFPath);
			//读取pdf模板
			//reader=new PdfReader(templatePath);
			reader=new PdfReader(new URL(templatePath));
			bos=new ByteArrayOutputStream();
			stamper=new PdfStamper(reader, bos);
			 /**
			   * 使用中文字体
			   * 如果是利用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体就行了
			   */
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 
			Font chineseFont= new Font(bfChinese, 12, Font.NORMAL);   
			AcroFields s=stamper.getAcroFields();
			System.out.println("s: " + s);
			System.out.println("AcroFields: " + s.getFields());
			System.out.println("AcroFields.class: " + s.getFields().getClass());
			System.out.println("getSignatureNames: " + s.getSignatureNames());
			System.out.println("getSignatureNames: " + s.getTotalRevisions());
			System.out.println("s: " + s.getBlankSignatureNames());
		   	System.out.println("s: " + s.getFieldCache());
		   	System.out.println("s: " + s.getSubstitutionFonts());
		    int i = 1;
			for (Iterator it = s.getFields().keySet().iterator(); it.hasNext(); i++) { 
				 String name = (String) it.next();
				 String value = s.getField(name);
				 System.out.println("[" + i + "- name:" + name + ", value: "+value+"]");
				 s.setField(""+name.trim(), "aaa");
			 }
			s.setField("Text1", "NOHI");
			s.setField("Text2", "2017-06-01");
			stamper.setFormFlattening(true);
			stamper.close();
			
		//	out.write(bos.toByteArray());
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PdfTest pdfTest=new PdfTest();
		pdfTest.fillTemplate();
	}
}
