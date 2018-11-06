package com.lzz.bussecurity.action.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.lzz.bussecurity.action.LzzBaseManagerAction;
import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzBusMgr;
import com.lzz.bussecurity.mgr.LzzDicMgr;
import com.lzz.bussecurity.mgr.LzzLogMgr;
import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.service.LzzSensorDataService;
import com.lzz.bussecurity.utils.LzzFilter;
import com.lzz.bussecurity.utils.LzzPaging;

public class LzzLogAction extends LzzBaseManagerAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6578139374345733548L;
	
	/**
	 * 想要获取的目标车辆IDs
	 */
	public String busIds;
	/**
	 * 起始时间段
	 */
	public String startTime;
	/**
	 * 结束时间段
	 */
	public String endTime;
	
	/**
	 * 分页信息
	 */
	public String pageInfo;
	/**
	 * 获取公交传感器数据
	 * @return
	 */
	public String getSensorDataList() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			System.out.println("get started :" + busIds);
			
			List<String> ids = new ArrayList<String>();
			if(null!=busIds){//获取指定车辆的
				ids = stringToArray(busIds);
			}else{//获取名下管理车辆的
				ids = LzzBusMgr.getUserMgrBusesIDList(curUser);
			}
			
			for(int i=0; i<ids.size(); i++){
				List<LzzSensorData> sensor_datas = LzzSensorDataService.self().getLzzSensorDataListByBusId(ids.get(i));
				for(int j=sensor_datas.size()-1; j>=0; j--){
					LzzSensorData sensor_data = sensor_datas.get(j);
					JSONObject obj = JSONObject.fromObject(sensor_data);
					obj.put("statusId", LzzDicMgr.isAlarmStatus(sensor_data.getStatusId())?"1":"0");
					data.add(obj);
				}
			}
			
			LzzFilter filter = new LzzFilter();
			if(null!=startTime){
				filter.addFilter("ge", "time", startTime, null);
			}
			
			if(null!=endTime){
				filter.addFilter("lt", "time", endTime, null);
			}
			filter.filter(data);
			
			JSONObject pageInfoObj = JSONObject.fromObject(pageInfo);
			if(null!=pageInfo){
				data = LzzPaging.get(data, pageInfoObj);
			}
			
			rslt.put("data", data);
			rslt.put("pageInfo", pageInfoObj);
			
			System.out.println("get finished");
			ts.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			success = false;
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				rslt.put("success", success);
				response.setCharacterEncoding("UTF-8");
				writer = response.getWriter();
				writer.append(rslt.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	/**
	 * 获取公交传感器数据
	 * @return
	 */
	public String setPrevAlarmHandled() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			if(null!=busIds){
				List<String> ids = stringToArray(busIds);
				for(int i=0; i<ids.size(); i++){
					LzzLogMgr.setPrevAlarmHandled(ids.get(i));
				}
			}
			
			rslt.put("data", data);
			ts.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			success = false;
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				rslt.put("success", success);
				response.setCharacterEncoding("UTF-8");
				writer = response.getWriter();
				writer.append(rslt.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private List<String> stringToArray(String str_ids) {
		str_ids = str_ids.replaceAll("\\[", "");
		str_ids = str_ids.replaceAll("\\]", "");
		str_ids = str_ids.replaceAll(" ", "");
		String[] ids = str_ids.split(",");
		List<String> rslt = new ArrayList<String>();
		for(int i=0; i<ids.length; i++){
			rslt.add(ids[i]);
		}
		return rslt;
	}
}
