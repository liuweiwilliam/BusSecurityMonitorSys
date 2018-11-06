package com.lzz.bussecurity.action.bus;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.lzz.bussecurity.action.LzzBaseManagerAction;
import com.lzz.bussecurity.cachemgr.LzzBusCacheMgr;
import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzBusMgr;
import com.lzz.bussecurity.mgr.LzzDicMgr;
import com.lzz.bussecurity.mgr.LzzLogMgr;

public class LzzBusAction extends LzzBaseManagerAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6065078211299805543L;

	/**
	 * 获取用户能管理的公交树状数据
	 * @return
	 */
	public String getUserMgrBusList() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONObject data = new JSONObject();
		boolean success = true;
		try {
			data = LzzBusMgr.getUserMgrBusList(curUser);
			
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
	
	/**
	 * 获取用户管理车辆列表
	 * @return
	 */
	public String getUserMgrBuses() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			data = LzzBusMgr.getUserMgrBuses(curUser);
			
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
	
	/**
	 * 获取报警车辆列表
	 * @return
	 */
	public String getAlarmedBuses() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			data = LzzBusMgr.getUserMgrBuses(curUser);
			for(int i=0; i<data.size(); i++){
				JSONObject sin = data.getJSONObject(i);
				if(!"1".equals(sin.getString("status"))){
					data.remove(i);
					i--;
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
	
	/**
	 * 获取车辆报警信息
	 * @return
	 */
	public String getBusAlarmMsg() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		boolean success = true;
		try {
			String bus_num = LzzBusMgr.getBusNum(busId);
			String bus_status = LzzBusMgr.getBusStatus(busId);
			String status_val = LzzDicMgr.getValueById(bus_status);
			String data = "车辆 " + bus_num + " 发生报警，报警类型 " + status_val;
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
	
	public String busIds;
	/**
	 * 获取bus位置
	 * @return
	 */
	public String getBusPos() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			if(null!=busIds){
				String[] ids = busIds.split(",");
				for(int i=0; i<ids.length; i++){
					JSONObject sin_data = LzzBusMgr.getBusPos(ids[i]);
					data.add(sin_data);
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
	
	public String busId;
	public String flag;
	/**
	 * 设置公交忽略警告
	 * @return
	 */
	public String setBusAlarmIgnore() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		boolean success = true;
		try {
			LzzBusMgr.setBusAlermIgnore(busId, flag);
			if("1".equals(flag)){
				LzzLogMgr.setPrevAlarmHandled(busId);
			}
			ts.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			
			LzzBusCacheMgr.self().reloadCache();
			
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
	 * 获取公交忽略警告标志
	 * @return
	 */
	public String getBusAlarmIgnore() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		boolean success = true;
		try {
			boolean flag = LzzBusMgr.getBusAlarmIgnoreFlag(busId);
			rslt.put("data", flag);
			
			ts.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ts.rollback();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			
			LzzBusCacheMgr.self().reloadCache();
			
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
}
