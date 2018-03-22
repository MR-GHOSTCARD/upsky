package com.etc.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

public class WordTest {
	
	public static void main(String[] args) {
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage("aa.doc");
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
			String text2007 = extractor.getText();
			System.out.println(text2007);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
