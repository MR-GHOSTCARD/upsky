package com.base.util;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class TessDemo {
	public static void main(String[] args) {
		File imageFile=new File("D:\\codeImage\\test\\f1.jpg");
		Tesseract instance=Tesseract.getInstance();
		
		File tessDataFolder=LoadLibs.extractTessResources("tessdata");
		
		instance.setLanguage("eng");
		
		instance.setDatapath(tessDataFolder.getAbsolutePath());
		
		String result="";
		try {
			result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
