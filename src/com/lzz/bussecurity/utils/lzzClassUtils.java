package com.lzz.bussecurity.utils;

import java.lang.reflect.Field;

import net.sf.json.JSONArray;

public class lzzClassUtils {
	public static JSONArray getClassFields(Class c) {
		JSONArray rslt = new JSONArray();
		
        //获取该类的成员变量
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            //获取成员变量的名称
            //System.out.println(field.getName());
            rslt.add(field.getName());
        }
        
        return rslt;
    }
	
}
