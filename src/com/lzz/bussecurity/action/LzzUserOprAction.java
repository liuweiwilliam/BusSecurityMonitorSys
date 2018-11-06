package com.lzz.bussecurity.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Transaction;

import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzDicMgr;
import com.lzz.bussecurity.mgr.LzzUserOprMgr;
import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.service.LzzSensorDataService;

public class LzzUserOprAction extends LzzBaseManagerAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4572813359765672593L;

	public String getUserOpr() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			JSONArray oprs = LzzUserOprMgr.getUserOpr(curUser.getId());
			rslt.put("data", oprs);
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
	
	public String oprType;
	public String busId;
	
	public String addUserOpr() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			LzzUserOprMgr.addUserSelectBusOpr(curUser.getId(), busId);
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
}
