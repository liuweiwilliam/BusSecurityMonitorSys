package com.lzz.bussecurity.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LzzFilter {
	private JSONArray filters = new JSONArray();
	
	/**
	 * 添加数据过去条件
	 * @param type 过滤类型
	 * @param key 过滤的值
	 * @param val1  过滤的条件1
	 * @param val2  过滤的条件2
	 */
	public void addFilter(String type, String key, String val1, String val2){
		if(null==type || "".equals(type)
				|| null==key || "".equals(key)
				|| null==val1 || "".equals(val1)){
			return;
		}
		
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("key", key);
		obj.put("val1", val1);
		obj.put("val2", val2);
		filters.add(obj);
	}
	
	/**
	 * 执行过滤
	 * @param rslt
	 * @return
	 */
	public JSONArray filter(JSONArray rslt){
		for(int i=0; i<filters.size(); i++){
			for(int j=0; j<rslt.size(); j++){
				if(!match(filters.getJSONObject(i), rslt.getJSONObject(j))){
					rslt.remove(i);
					j--;
				}
			}
		}
		return rslt;
	}
	
	/**
	 * 执行过滤
	 * @param rslt
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public <T> List<T> filterList(List<T> rslt) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		for(int i=0; i<filters.size(); i++){
			for(int j=0; j<rslt.size(); j++){
				if(!match(filters.getJSONObject(i), rslt.get(j))){
					rslt.remove(j);
					j--;
				}
			}
		}
		
		return rslt;
	}

	private boolean match(JSONObject filter, JSONObject obj) {
		String type = (String)filter.get("type");
		String key = (String)filter.get("key");
		String val1 = (String)filter.get("val1");
		String val2 = (String)filter.get("val2");
		if(null==type || null==key || null==val1) return false;
		
		String key_val = (String)obj.get(key);
		
		return match(type, key_val, val1, val2);
	}
	
	private boolean match(JSONObject filter, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String type = (String)filter.get("type");
		String key = (String)filter.get("key");
		String val1 = (String)filter.get("val1");
		String val2 = (String)filter.get("val2");
		if(null==type || null==key || null==val1) return false;
		
		String get_key_fun = key.substring(0, 1).toUpperCase() + key.substring(1);
		Method getMethod = obj.getClass().getMethod("get" + get_key_fun);
		
		String key_val = (String) getMethod.invoke(obj);
		
		return match(type, key_val, val1, val2);
	}
	
	private boolean match(String type, String key_val, String val1, String val2){
		if(null==key_val) return false;
		
		switch(type){
		case "eq":
			return val1.equals(key_val);
		case "lt":
			return key_val.compareTo(val1)<0;
		case "le":
			return key_val.compareTo(val1)<=0;
		case "gt":
			return key_val.compareTo(val1)>0;
		case "ge":
			return key_val.compareTo(val1)>=0;
		case "gt-lt":
			if(null==val2) return false;
			return key_val.compareTo(val1)>0 && key_val.compareTo(val2)<0;
		case "gt-le":
			if(null==val2) return false;
			return key_val.compareTo(val1)>0 && key_val.compareTo(val2)<=0;
		case "ge-le":
			if(null==val2) return false;
			return key_val.compareTo(val1)>=0 && key_val.compareTo(val2)<=0;
		case "ge-lt":
			if(null==val2) return false;
			return key_val.compareTo(val1)>=0 && key_val.compareTo(val2)<0;
		case "contains":
			return key_val.contains(val1);
			
		}
		return true;
	}
}
