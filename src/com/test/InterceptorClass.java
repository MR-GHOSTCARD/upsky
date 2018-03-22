package com.test;

public class InterceptorClass {
	public void before(){
		System.out.println("doSomething之前");
	}
	public void after(){
		System.out.println("doSomething之后");
	}
}
