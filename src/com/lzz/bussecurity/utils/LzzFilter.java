package com.lzz.bussecurity.utils;

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
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("key", key);
		obj.put("val1", val1);
		obj.put("val2", val2);
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

	private boolean match(JSONObject filter, JSONObject obj) {
		String type = (String)filter.get("type");
		String key = (String)filter.get("key");
		String val1 = (String)filter.get("val1");
		String val2 = null;
		if(null==type || null==key || null==val1) return false;
		
		String key_val = (String)obj.get(key);
		
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
			val2 = (String)filter.get("val2");
			if(null==val2) return false;
			return key_val.compareTo(val1)>0 && key_val.compareTo(val2)<0;
		case "gt-le":
			val2 = (String)filter.get("val2");
			if(null==val2) return false;
			return key_val.compareTo(val1)>0 && key_val.compareTo(val2)<=0;
		case "ge-le":
			val2 = (String)filter.get("val2");
			if(null==val2) return false;
			return key_val.compareTo(val1)>=0 && key_val.compareTo(val2)<=0;
		case "ge-lt":
			val2 = (String)filter.get("val2");
			if(null==val2) return false;
			return key_val.compareTo(val1)>=0 && key_val.compareTo(val2)<0;
		case "contains":
			return key_val.contains(val1);
			
		}
		return true;
	}
}
