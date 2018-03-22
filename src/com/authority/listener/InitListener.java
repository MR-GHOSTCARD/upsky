package com.authority.listener;

import java.awt.Font;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.base.util.OCRTemp;


public class InitListener extends ContextLoaderListener  {
	private ContextLoader contextLoader;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("------创建图形验证码识别样板------");
		try {
			String str="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9";
			OCRTemp.putTemp("ncpc", str, 22, Font.DIALOG, Font.BOLD+Font.ITALIC, 16, 17);
			String str1="1,2,3,4,5,6,7,8,9,0";
			String filePath=this.getClass().getClassLoader().getResource("").getPath();
		    filePath=filePath.split("classes")[0];
		    String  filePath1=filePath+"sinopecTemp";
			OCRTemp.putTemp("sinopec", filePath1);
			String filePath2=filePath+"etcTemper_shandong";
			OCRTemp.putTemp("etc_shandong", filePath2);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("------图形验证码识别样板创建失败------");
		}
		this.contextLoader = createContextLoader();
		this.contextLoader.initWebApplicationContext(event.getServletContext());
	}
	
	@Override
	protected ContextLoader createContextLoader() {
		// TODO Auto-generated method stub
		return new ContextLoader();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}
