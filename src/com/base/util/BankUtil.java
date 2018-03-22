package com.base.util;

import java.util.ArrayList;
import java.util.List;

public class BankUtil {
	public final static List<String> bankName= new ArrayList<String>(){{
		add("中国工商银行");
		add("中国银行");
		add("中国农业银行");
		add("中国建设银行");
		add("交通银行");
		add("招商银行");
		add("兴业银行");
		add("中国民生银行");
		add("其他");
	}};
	
	public static void main(String[] args) {
		int i=0;
		int a=i+2;
		System.out.println(i+","+a);
	}
}
