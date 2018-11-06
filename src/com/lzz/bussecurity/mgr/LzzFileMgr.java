package com.lzz.bussecurity.mgr;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.bussecurity.pojo.LzzFileGroup;
import com.lzz.bussecurity.pojo.LzzFileInfo;
import com.lzz.bussecurity.service.LzzFileService;
import com.lzz.bussecurity.utils.LzzFileUtil;
import com.lzz.bussecurity.utils.LzzProperties;

public class LzzFileMgr {

	public static void addFileInfo(String fileId, String fname,
			String fpath, String url_path) {
		// TODO Auto-generated method stub
		LzzFileInfo info = new LzzFileInfo();
		info.setId(fileId);
		info.setFname(fname);
		info.setFsavepath(fpath);
		info.setFurlpath(url_path);
		info.setFtype(LzzFileUtil.self().typeOf(fname));
		info.setDr("0");
		LzzFileService.self().saveLzzFileInfo(info);
	}
	
	public static String getSingleFileUrlByFileGroupId(String group_id){
		List<LzzFileInfo> files = getFilesByFileGroupId(group_id);
		
		if(files.size()==0) return null;
		
		return LzzProperties.getProjectUrl() + files.get(0).getFurlpath();
	}
	
	/**
	 * 通过group_id获取文件信息JSON对象数组
	 * @param group_id
	 * @return 格式：[{id,fname,furl}]
	 */
	public static JSONArray getFileInfosByFileGroupId(String group_id){
		List<LzzFileInfo> files = getFilesByFileGroupId(group_id);
		
		JSONArray rslt= new JSONArray();
		if(files.size()==0) return rslt;
		
		for(LzzFileInfo sin : files){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("fname", sin.getFname());
			obj.put("furl", LzzProperties.getProjectUrl() + sin.getFurlpath());
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 通过文件组ID获取图片路径JSON数组
	 * @param group_id
	 * @return 格式:[{imgUrl}]
	 */
	public static JSONArray getGoodFileInfosByFileGroupId(String group_id){
		List<LzzFileInfo> files = getFilesByFileGroupId(group_id);
		
		JSONArray rslt= new JSONArray();
		if(files.size()==0) return rslt;
		
		for(LzzFileInfo sin : files){
			JSONObject obj = new JSONObject();
			obj.put("imgUrl", LzzProperties.getProjectUrl() + sin.getFurlpath());
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 通过文件组ID获取图片路径数组
	 * @param group_id
	 * @return 格式:[""]
	 */
	public static JSONArray getUrlArraysByFileGroupId(String group_id){
		List<LzzFileInfo> files = getFilesByFileGroupId(group_id);
		
		JSONArray rslt= new JSONArray();
		if(files.size()==0) return rslt;
		
		for(LzzFileInfo sin : files){
			rslt.add(LzzProperties.getProjectUrl() + sin.getFurlpath());
		}
		
		return rslt;
	}
	
	/**
	 * 通过文件组ID获取图片路径数组,获取评价图片专用
	 * @param group_id
	 * @return 格式:[""]
	 */
	public static JSONArray getUrlArraysByFileGroupIdForEvaluate(String group_id){
		List<LzzFileInfo> files = getFilesByFileGroupId(group_id);
		
		JSONArray rslt= new JSONArray();
		if(files.size()==0) return rslt;
		
		for(LzzFileInfo sin : files){
			rslt.add(sin.getFurlpath());
		}
		
		return rslt;
	}

	/**
	 * 通过group_id获取fileInfo List
	 * @param group_id
	 * @return 格式: List<LzzFileInfo> 
	 */
	public static List<LzzFileInfo> getFilesByFileGroupId(String group_id) {
		// TODO Auto-generated method stub
		List<LzzFileInfo> rslt = new ArrayList<LzzFileInfo>();
		
		if(null==group_id || "".equals(group_id)) return rslt;
		
		LzzFileGroup group = LzzFileService.self().getLzzFileGroupById(group_id);
		String ids = group.getFileIds();
		
		if(null==ids || "".equals(ids)) return rslt;
		
		JSONArray ids_array = JSONArray.fromObject(ids);
		for(int i=0; i<ids_array.size(); i++){
			LzzFileInfo file = LzzFileService.self().getLzzFileInfoById(ids_array.getString(i));
			rslt.add(file);
		}
		
		return rslt;
	}
	
	/**
	 * 通过JSON数组创建文件组  目前主要用于评价图片的存储
	 * @param files
	 * @return
	 */
	public static String createFileGroupByJSONArray(JSONArray files){
		if(null==files
				|| files.size()==0){
			return null;
		}
		
		LzzFileGroup g = new LzzFileGroup();
		g.setId(LzzIDMgr.self().getID());
		JSONArray fileIds = new JSONArray();
		for(int i=0; i<files.size(); i++){
			LzzFileInfo info = new LzzFileInfo();
			info.setId(LzzIDMgr.self().getID());
			info.setFname(files.getString(i));
			info.setFtype(LzzFileUtil.self().typeOf(files.getString(i)));
			info.setFurlpath(files.getString(i));
			LzzFileService.self().saveLzzFileInfo(info);
			fileIds.add(info.getId());
		}
		g.setFileIds(fileIds.toString());
		LzzFileService.self().saveLzzFileGroup(g);
		
		return g.getId();
	}
	
	/**
	 * 通过ids数组创建文件组
	 * @param ids
	 * @return
	 */
	public static String createFileGroupByFileIds(JSONArray ids){
		if(null==ids
				|| ids.size()==0){
			return null;
		}
		
		LzzFileGroup g = new LzzFileGroup();
		g.setId(LzzIDMgr.self().getID());
		g.setFileIds(ids.toString());
		LzzFileService.self().saveLzzFileGroup(g);
		
		return g.getId();
	}
}
