package com.lzz.bussecurity.action.user;

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
import com.lzz.bussecurity.mgr.LzzCompanyMgr;
import com.lzz.bussecurity.mgr.LzzDicMgr;
import com.lzz.bussecurity.mgr.LzzEncodeMgr;
import com.lzz.bussecurity.mgr.LzzSensorDataMgr;
import com.lzz.bussecurity.mgr.LzzUserMgr;
import com.lzz.bussecurity.pojo.LzzRole;
import com.lzz.bussecurity.pojo.LzzSensorAlarmData;
import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.pojo.LzzUserRole;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.service.LzzSensorDataService;
import com.lzz.bussecurity.service.LzzUserService;
import com.lzz.bussecurity.utils.LzzFilter;
import com.lzz.bussecurity.utils.LzzPaging;

public class LzzUserAction extends LzzBaseManagerAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6578139374345733548L;
	
	/**
	 * 分页信息
	 */
	public String pageInfo;
	/**
	 * 获取用户列表
	 * @return
	 */
	public String getUserList() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			String company_id = curUser.getCompanyId();
			List<LzzUser> users = LzzUserService.self().getLzzUserListByCompanyId(company_id);
			
			for(int i=0; i<users.size(); i++){
				LzzUser user = users.get(i);
				JSONObject sin = LzzUserMgr.getUserJSONInfo(user);
				sin.put("orderNum", i+1);
				data.add(sin);
			}
			
			if(null!=pageInfo){
				JSONObject pageInfoObj = JSONObject.fromObject(pageInfo);
				if(null!=pageInfo){
					data = LzzPaging.get(data, pageInfoObj);
				}
				rslt.put("pageInfo", pageInfoObj);
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
	 * 获取角色信息
	 * @return
	 */
	public String getRoleList() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONArray data = new JSONArray();
		boolean success = true;
		try {
			List<LzzRole> roles = LzzUserService.self().getAllValidLzzRole();
			
			for(int i=0; i<roles.size(); i++){
				LzzRole role = roles.get(i);
				JSONObject sin = LzzUserMgr.getRoleJSONInfo(role);
				sin.put("orderNum", i+1);
				data.add(sin);
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
	
	public String id;
	/**
	 * 获取用户信息
	 * @return
	 */
	public String getUserInfo() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONObject data = new JSONObject();
		boolean success = true;
		try {
			LzzUser user = LzzUserService.self().getLzzUserById(id);
			data = LzzUserMgr.getUserJSONInfo(user);
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
	
	public String authorityName;
	/**
	 * 获取用户信息
	 * @return
	 */
	public String getUserAuthoritys() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONObject data = new JSONObject();
		boolean success = true;
		try {
			JSONArray authoritys = LzzUserMgr.getUserAuthorityNames(curUser.getId());
			rslt.put("data", authoritys);
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
	 * 删除用户信息
	 * @return
	 */
	public String delUser() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		boolean success = true;
		try {
			LzzUser user = LzzUserService.self().getLzzUserById(id);
			user.setDr("1");
			LzzUserService.self().updateLzzUser(user);
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
	
	public String info;
	/**
	 * 编辑用户信息
	 * @return
	 */
	public String addOrUpdateUser() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		JSONObject rslt = new JSONObject();
		JSONObject data = new JSONObject();
		boolean success = true;
		try {
			JSONObject json_info = JSONObject.fromObject(info);
			String id = (String) json_info.get("id");
			LzzUser user = null;
			if(null==id || "".equals(id)){
				user = new LzzUser();
			}else{
				user = LzzUserService.self().getLzzUserById(id);
			}
			
			String uname = json_info.getString("userName");
			success = LzzUserMgr.checkUname(user, uname);
			if(!success){
				rslt.put("msg", "用户名已存在");
			}
			
			if(success){
				user.setLastName(json_info.getString("lastName"));
				String pwd = json_info.getString("pwd");
				if(null==user.getPwd()
						|| !user.getPwd().equals(pwd)){
					user.setPwd(LzzEncodeMgr.MD5(json_info.getString("pwd"), "utf-8"));
				}
				
				user.setUname(json_info.getString("userName"));
				
				user.setCompanyId(curUser.getCompanyId());
				
				String role_name = json_info.getString("role");
				LzzRole role = LzzUserService.self().getLzzRoleByName(role_name);
				boolean has_role = LzzUserMgr.hasRole(user.getId(), role.getId());
				if(!has_role){
					LzzUserMgr.addUserRole(user.getId(), role.getId());
				}
				
				if(null==id || "".equals(id)){
					LzzUserService.self().saveLzzUser(user);
				}else{
					LzzUserService.self().updateLzzUser(user);
				}
				
			}
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
