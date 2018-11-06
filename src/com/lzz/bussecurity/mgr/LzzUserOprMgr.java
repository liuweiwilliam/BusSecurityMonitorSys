package com.lzz.bussecurity.mgr;

import java.util.Hashtable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户操作管理，用于界面上与地图的互动
 * @author Administrator
 *
 */
public class LzzUserOprMgr {
	private static Hashtable<String, JSONArray> userOprs = new Hashtable<String, JSONArray>();
	
	/**
	 * 获取用户的操作，然后从操作列表中清除
	 * @param userid
	 * @return
	 */
	public static JSONArray getUserOpr(String userid){
		JSONArray rslt = new JSONArray();
		if(userOprs.containsKey(userid)){
			rslt = userOprs.get(userid);
			userOprs.remove(userid);
			return rslt;
		}
		
		return rslt;
	}
	
	/**
	 * 添加用户操作
	 * @param userid
	 * @param opr
	 */
	private static void addUserOpr(String userid, JSONObject opr){
		if(userOprs.containsKey(userid)){
			JSONArray oprs = userOprs.get(userid);
			oprs.add(opr);
			userOprs.put(userid, oprs);
		}else{
			JSONArray oprs = new JSONArray();
			oprs.add(opr);
			userOprs.put(userid, oprs);
		}
	}
	
	/**
	 * 添加用户选择车辆操作
	 * @param userid
	 * @param busid
	 */
	public static void addUserSelectBusOpr(String userid, String busid){
		JSONObject opr = new JSONObject();
		opr.put("opr", "selectBus");
		opr.put("busId", busid);
		addUserOpr(userid, opr);
	}
}
