package com.test;

import com.test.proxy.BusinessClass;
import com.test.proxy.BusinessProxy;

public class TTest {
	public static void main(String[] args) {
		BusinessProxy businessProxy=new BusinessProxy(new BusinessClass());
		businessProxy.doSomething();
	}
}
