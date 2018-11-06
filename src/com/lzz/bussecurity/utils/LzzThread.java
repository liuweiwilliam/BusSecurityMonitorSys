package com.lzz.bussecurity.utils;

import java.lang.reflect.Method;

public class LzzThread extends Thread {
	private String className;
	private String funName;
	
	public LzzThread(String className, String funName){
		this.className = className;
		this.funName = funName;
	}
	
	public void run(){
		Class<?> threadClazz;
		try {
			threadClazz = Class.forName(className);
			Method method = threadClazz.getMethod(funName);  
			System.out.println(method.invoke(null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
