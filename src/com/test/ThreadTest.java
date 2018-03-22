package com.test;

public class ThreadTest extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long beginTime=System.currentTimeMillis();
		int count=0;
		for(int i=0;i<50000000;i++){
			count=i+1;
//			Thread.yield();
		}
		long endTime=System.currentTimeMillis();
		System.out.println("用时："+(endTime-beginTime)+":"+count);
	}
	
	public static void main(String[] args) {
		ThreadTest test=new ThreadTest();
		test.start();
	}
	
}
