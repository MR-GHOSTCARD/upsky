package com.test.proxy;

public class BusinessProxy implements BusinessFacade {
	private BusinessClass businessClass;
	
	public BusinessProxy(BusinessClass businessClass) {
		// TODO Auto-generated constructor stub
		this.businessClass=businessClass;
	}
	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("前拦截");
		businessClass.doSomething();
		System.out.println("后拦截");
	}

}
