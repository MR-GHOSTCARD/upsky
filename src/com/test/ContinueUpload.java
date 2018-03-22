package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.base.exception.FileAccessException;

public class ContinueUpload {
	private static int position=-1;
	
	private static void keepGoing(File source,File target,int position){
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			RandomAccessFile readFile=new RandomAccessFile(source, "rw");
			RandomAccessFile writeFile=new RandomAccessFile(target, "rw");
			readFile.seek(position);
			writeFile.seek(position);
			
			//数据缓冲区
			byte[] buf=new byte[1];
			//数据读写
			while(readFile.read(buf)!=-1){
				writeFile.write(buf);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//源文件与目标文件
		File sourceFile=new File("D:\\test.txt");
		File targetFile=new File("D:\\test1.txt");
		//输入输出流
		FileInputStream fis=null;
		FileOutputStream fos=null;
		//数据缓冲区
		byte[] buf=new byte[1];
		
		try {
			fis=new FileInputStream(sourceFile);
			fos=new FileOutputStream(targetFile);
			//数据读写
			while(fis.read(buf)!=-1){
				fos.write(buf);
				if(targetFile.length()==3){
					position=3;
					throw new FileAccessException();
				}
			}
		}catch (FileAccessException e) {
			// TODO: handle exception
			keepGoing(sourceFile, targetFile, position);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
