package com.lzz.bussecurity.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *处理分页
 */
public class LzzPaging {
	/**
	 *获取分页的结果
	 *list：需要进行分页的数据
	 *pageNo：被获取的页码
	 *pageSize：页码长度
	 */
	public static List get(List list,int pageNo,int pageSize){
		
		if(list!=null){
			List rsl = new ArrayList();
			int start = (pageNo-1)*pageSize;
			int end  = pageNo*pageSize-1;
			for(int i=start;i<=end;i++){
				if(i>=0&&i<list.size()){
					rsl.add(list.get(i));
				}
			}
			return rsl;
		}
		return null;
	}
	
	/**
	 *获取分页的结果
	 *list：需要进行分页的数据
	 *page_info：分页信息
	 */
	public static JSONArray get(JSONArray list, JSONObject page_info){
		int pageNo = page_info.getInt("pageNo");
		int pageSize = page_info.getInt("pageSize");
		
		JSONArray rslt = new JSONArray();
		if(list!=null){
			page_info.put("totalSize", list.size());
			
			int start = (pageNo-1)*pageSize;
			int end  = pageNo*pageSize-1;
			for(int i=start;i<=end;i++){
				if(i>=0&&i<list.size()){
					rslt.add(list.get(i));
				}
			}
			page_info.put("realSize", list.size());
			page_info.put("totalPage", getPageNum(list, pageSize));
		}
		
		return rslt;
	}
	
	/**
	 *获取数据的页数
	 *list:需要分页的数据
	 *pageSize：页码长度
	 */
	public static int getPageNum(List list, int pageSize){
		if(list==null || list.size()==0){
			return 1;
		}else{
			int size = list.size();
			double num = size*1.0/pageSize;
			int zhengshu = (int) num;
			if(num-zhengshu>0){
				zhengshu++;
			}
			return zhengshu;
		}
	}
}
