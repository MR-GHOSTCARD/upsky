package com.etc.utils;

import java.util.UUID;

public class UUIDtest {
	public String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	
	public static void main(String[] args) {
		UUIDtest uuiDtest=new UUIDtest();
		for(int i=0;i<100;i++){
			System.out.println(uuiDtest.getUUID());
		}
	}

}
