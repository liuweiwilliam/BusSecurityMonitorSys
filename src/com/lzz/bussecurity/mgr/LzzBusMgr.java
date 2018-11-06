package com.lzz.bussecurity.mgr;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.bussecurity.pojo.LzzBus;
import com.lzz.bussecurity.pojo.LzzBusLine;
import com.lzz.bussecurity.pojo.LzzCompany;
import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.service.LzzBusLineService;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.service.LzzCompanyService;
import com.lzz.bussecurity.service.LzzDictionaryService;

public class LzzBusMgr {
	/**
	 * 获取用户能管理的公交列表
	 * @param curUser
	 * @return 包含公司结构信息
	 */
	public static JSONObject getUserMgrBusList(LzzUser curUser) {
		JSONObject rslt = new JSONObject();
		if(null==curUser) return rslt;
		
		String company_id = curUser.getCompanyId();
		
		return getBusLineByCompany(company_id);
	}
	
	/**
	 * 获取用户管理的用脚对象列表
	 * @param curUser
	 * @return
	 */
	public static List<String> getUserMgrBusesIDList(LzzUser curUser){
		List<String> rslt = new ArrayList<String>();
		if(null==curUser) return rslt;
		
		String company_id = curUser.getCompanyId();
		
		return getBusesIDByCompany(rslt, company_id);
	}
	
	/**
	 * 通过公交公司获取名下线路和下级公司
	 * @param company_id
	 * @return
	 */
	private static JSONObject getBusLineByCompany(String company_id){
		JSONObject rslt = new JSONObject();
		
		LzzCompany company = LzzCompanyService.self().getLzzCompanyById(company_id);
		if(null==company) return rslt;
		
		rslt.put("name", company.getName());
		
		//获取公交公司名下线路
		List<LzzBusLine> lines = LzzBusLineService.self().getLzzBusLineListByCompanyId(company_id);
		JSONArray lines_arr = new JSONArray();
		for(int i=0; i<lines.size(); i++){
			LzzBusLine line = lines.get(i);
			JSONObject sin_line = new JSONObject();
			
			sin_line.put("lineNum", line.getLineNum());
			JSONArray buses_arr = new JSONArray();
			
			List<LzzBus> buses = LzzBusService.self().getLzzBusListByLineId(line.getId());
			for(int j=0; j<buses.size(); j++){
				LzzBus bus = buses.get(j);
				JSONObject sin_bus = new JSONObject();
				sin_bus.put("id", bus.getId());
				sin_bus.put("carNum", bus.getCarNum());
				String status = LzzDicMgr.getValueById(bus.getStatusId());
				sin_bus.put("status", null==status?"":status);
				buses_arr.add(sin_bus);
			}
			
			sin_line.put("buses", buses_arr);
			lines_arr.add(sin_line);
		}
		rslt.put("lines", lines_arr);
		
		//获取公交公司下级公司
		List<LzzCompany> companys_under = LzzCompanyService.self().getLzzCompanyListByParentId(company_id);
		JSONArray company_arr = new JSONArray();
		for(int i=0; i<companys_under.size(); i++){
			JSONObject sin_com = getBusLineByCompany(companys_under.get(i).getId());
			company_arr.add(sin_com);
		}
		
		rslt.put("companys", company_arr);
		
		return rslt;
	}
	
	/**
	 * 通过公交公司名下的所有公交
	 * @param company_id
	 * @return
	 */
	private static List<String> getBusesIDByCompany(List<String> rslt, String company_id){
		LzzCompany company = LzzCompanyService.self().getLzzCompanyById(company_id);
		if(null==company) return null;
		
		//获取公交公司名下线路
		List<LzzBusLine> lines = LzzBusLineService.self().getLzzBusLineListByCompanyId(company_id);
		for(int i=0; i<lines.size(); i++){
			LzzBusLine line = lines.get(i);
			List<LzzBus> buses = LzzBusService.self().getLzzBusListByLineId(line.getId());
			for(int j=0; j<buses.size(); j++){
				LzzBus bus = buses.get(j);
				rslt.add(bus.getId());
			}
		}
		//获取公交公司下级公司
		List<LzzCompany> companys_under = LzzCompanyService.self().getLzzCompanyListByParentId(company_id);
		for(int i=0; i<companys_under.size(); i++){
			rslt = getBusesIDByCompany(rslt, companys_under.get(i).getId());
		}
		
		return rslt;
	}
	
	/**
	 * 获取公交坐标
	 * @param bus_id
	 * @return
	 */
	public static JSONObject getBusPos(String bus_id){
		JSONObject rslt = new JSONObject();
		if(null==bus_id) return rslt;
		LzzBus bus = LzzBusService.self().getLzzBusById(bus_id);
		if(null==bus) return rslt;
		
		rslt.put("lat", bus.getLat());
		rslt.put("lng", bus.getLng());
		
		return rslt;
	}
	
	/**
	 * 获取公交车牌号
	 * @param bus_id
	 * @return
	 */
	public static String getBusNum(String bus_id){
		String rslt = null;
		if(null==bus_id) return rslt;
		LzzBus bus = LzzBusService.self().getLzzBusById(bus_id);
		if(null==bus) return rslt;
		
		return bus.getCarNum();
	}

	/**
	 * 设置公交忽略警告
	 * @param busId公交ID
	 * @param flag 忽略标志
	 */
	public static boolean setBusAlermIgnore(String busId, String flag) {
		// TODO Auto-generated method stub
		LzzBus bus = LzzBusService.self().getLzzBusById(busId);
		if(null==bus) return false;
		
		bus.setIgnoreAlerm(flag);
		
		LzzBusService.self().updateLzzBus(bus);
		
		return true;
	}

	/**
	 * 获取用户管理的只包含公交的列表
	 * @param curUser
	 * @return
	 */
	public static JSONArray getUserMgrBuses(LzzUser curUser) {
		JSONArray rslt = new JSONArray();
		
		if(null==curUser) return rslt;
		
		String company_id = curUser.getCompanyId();
		
		return getBusByCompany(rslt, company_id);
	}
	
	/**
	 * 通过公交公司获取名下线路和下级公司
	 * @param company_id
	 * @return
	 */
	private static JSONArray getBusByCompany(JSONArray rslt, String company_id){
		LzzCompany company = LzzCompanyService.self().getLzzCompanyById(company_id);
		if(null==company) return rslt;
		
		//获取公交公司名下线路
		List<LzzBusLine> lines = LzzBusLineService.self().getLzzBusLineListByCompanyId(company_id);
		for(int i=0; i<lines.size(); i++){
			LzzBusLine line = lines.get(i);
			
			List<LzzBus> buses = LzzBusService.self().getLzzBusListByLineId(line.getId());
			for(int j=0; j<buses.size(); j++){
				LzzBus bus = buses.get(j);
				JSONObject sin_bus = new JSONObject();
				sin_bus.put("id", bus.getId());
				sin_bus.put("carNum", bus.getCarNum());
				String status = bus.getStatusId();
				sin_bus.put("status", null==status?"":(LzzDicMgr.isAlarmStatus(status)?"1":"0"));
				sin_bus.put("lat", bus.getLat());
				sin_bus.put("lng", bus.getLng());
				rslt.add(sin_bus);
			}
		}
		
		//获取公交公司下级公司
		List<LzzCompany> companys_under = LzzCompanyService.self().getLzzCompanyListByParentId(company_id);
		JSONArray company_arr = new JSONArray();
		for(int i=0; i<companys_under.size(); i++){
			JSONArray sin_com = getBusByCompany(rslt, companys_under.get(i).getId());
			company_arr.add(sin_com);
		}
		
		return rslt;
	}

	/**
	 * 获取公交车忽略警告标志
	 * @param busId
	 * @return
	 */
	public static boolean getBusAlarmIgnoreFlag(String busId) {
		LzzBus bus = LzzBusService.self().getLzzBusById(busId);
		if(null==bus) return true;
		return "1".equals(bus.getIgnoreAlerm());
	}

	/**
	 * 获取车辆状态
	 * @param busId
	 * @return
	 */
	public static String getBusStatus(String busId){
		LzzBus bus = LzzBusService.self().getLzzBusById(busId);
		if(null==bus) return null;
		return bus.getStatusId();
	}
	/**
	 * 判断车辆是否处于报警状态
	 * @param busId
	 * @return
	 */
	public static boolean isAlarmStatus(String busId) {
		String status = getBusStatus(busId);
		if(null==status) return true;
		return LzzDicMgr.isAlarmStatus(status);
	}
	/**
	 * 判断车辆是否处于报警处理中状态
	 * @param busId
	 * @return
	 */
	public static boolean isAlarmHandling(String busId) {
		String status = getBusStatus(busId);
		if(null==status) return true;
		return LzzDicMgr.isAlarmHandlingStatus(status);
	}
}
