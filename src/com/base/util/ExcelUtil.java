package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	public static String importExcelForXLS(InputStream inputStream) {
		HSSFWorkbook workbook=null;
		try {
			//2003版本的excel，用.xls结尾
			workbook=new HSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//判断表头
		HSSFSheet sheet=workbook.getSheetAt(0);
//		HSSFRow rowHead=sheet.getRow(0);
		//获取所有图片
		List<HSSFPictureData> pictures=workbook.getAllPictures();
		int i=0;
		for(HSSFShape shape:sheet.getDrawingPatriarch().getChildren()){
			HSSFClientAnchor anchor=(HSSFClientAnchor) shape.getAnchor();
			if(shape instanceof HSSFPicture){
				HSSFPicture picture=(HSSFPicture)shape;
				System.out.println(i+"--->"+(anchor.getRow1()+1)+":"+(anchor.getCol1()+1));
				int pictureIndex=picture.getPictureIndex()-1;
				HSSFPictureData pictureData=pictures.get(pictureIndex);
//				System.out.println(FmsUpload.savePicByByte(pictureData.getData()));
			}
			i++;
		}
		//获取总行数
//		int totoRows=sheet.getLastRowNum();
//		for(int i=0;i<=totoRows;i++){
//			HSSFRow row=sheet.getRow(i);
//			if(row!=null){
//				
//				HSSFCell cell=null;
//				cell=row.getCell(0);
//				System.out.println(cell.toString());
//			}
//		}
		return "";
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		File file=new File("D:\\dd\\dd.xls");
		importExcelForXLS(new FileInputStream(file));
	}
}
