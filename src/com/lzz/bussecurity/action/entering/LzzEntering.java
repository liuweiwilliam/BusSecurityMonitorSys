
package com.lzz.bussecurity.action.entering;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.bussecurity.action.LzzBaseManagerAction;
import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzIDMgr;

import com.lzz.bussecurity.pojo.LzzDictionary;
import com.lzz.bussecurity.service.LzzDictionaryService;

import com.lzz.bussecurity.pojo.LzzFileGroup;
import com.lzz.bussecurity.service.LzzFileService;

import com.lzz.bussecurity.pojo.LzzFileInfo;
import com.lzz.bussecurity.service.LzzFileService;

import com.lzz.bussecurity.pojo.LzzBus;
import com.lzz.bussecurity.service.LzzBusService;

import com.lzz.bussecurity.pojo.LzzBusLine;
import com.lzz.bussecurity.service.LzzBusLineService;

import com.lzz.bussecurity.pojo.LzzCompany;
import com.lzz.bussecurity.service.LzzCompanyService;

import com.lzz.bussecurity.pojo.LzzSensor;
import com.lzz.bussecurity.service.LzzSensorService;

import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.service.LzzSensorDataService;

import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.service.LzzUserService;

import com.lzz.bussecurity.pojo.LzzCamera;
import com.lzz.bussecurity.service.LzzCameraService;


import com.lzz.bussecurity.utils.lzzClassUtils;

public class LzzEntering extends LzzBaseManagerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String subData;
	public String id;
	public String fieldName;
	public String searchValue;
	public String addOrUpdateLzzDictionary() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzDictionaryData_obj = JSONObject.fromObject(subData);
			LzzDictionary obj = null;
			if(LzzDictionaryData_obj.getString("id").equals("")){
				obj = new LzzDictionary();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzDictionaryService.self().getLzzDictionaryById(LzzDictionaryData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzDictionaryData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzDictionaryData_obj.getString("id").equals("")){
				LzzDictionaryService.self().saveLzzDictionary(obj);
			}else{
				LzzDictionaryService.self().updateLzzDictionary(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzDictionary(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzDictionaryService.self().delLzzDictionaryById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzDictionary.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzDictionary(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzDictionary());
			}else{
				LzzDictionary sin = LzzDictionaryService.self().getLzzDictionaryById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzDictionary.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzDictionary() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzDictionary> all = LzzDictionaryService.self().getAllLzzDictionaryIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzDictionary.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzFileGroup() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzFileGroupData_obj = JSONObject.fromObject(subData);
			LzzFileGroup obj = null;
			if(LzzFileGroupData_obj.getString("id").equals("")){
				obj = new LzzFileGroup();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzFileService.self().getLzzFileGroupById(LzzFileGroupData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzFileGroupData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzFileGroupData_obj.getString("id").equals("")){
				LzzFileService.self().saveLzzFileGroup(obj);
			}else{
				LzzFileService.self().updateLzzFileGroup(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzFileGroup(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzFileService.self().delLzzFileGroupById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileGroup.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzFileGroup(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzFileGroup());
			}else{
				LzzFileGroup sin = LzzFileService.self().getLzzFileGroupById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileGroup.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzFileGroup() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzFileGroup> all = LzzFileService.self().getAllLzzFileGroupIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileGroup.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzFileInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzFileInfoData_obj = JSONObject.fromObject(subData);
			LzzFileInfo obj = null;
			if(LzzFileInfoData_obj.getString("id").equals("")){
				obj = new LzzFileInfo();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzFileService.self().getLzzFileInfoById(LzzFileInfoData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzFileInfoData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzFileInfoData_obj.getString("id").equals("")){
				LzzFileService.self().saveLzzFileInfo(obj);
			}else{
				LzzFileService.self().updateLzzFileInfo(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzFileInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzFileService.self().delLzzFileInfoById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileInfo.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzFileInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzFileInfo());
			}else{
				LzzFileInfo sin = LzzFileService.self().getLzzFileInfoById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileInfo.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzFileInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzFileInfo> all = LzzFileService.self().getAllLzzFileInfoIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileInfo.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzBus() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzBusData_obj = JSONObject.fromObject(subData);
			LzzBus obj = null;
			if(LzzBusData_obj.getString("id").equals("")){
				obj = new LzzBus();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzBusService.self().getLzzBusById(LzzBusData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzBusData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzBusData_obj.getString("id").equals("")){
				LzzBusService.self().saveLzzBus(obj);
			}else{
				LzzBusService.self().updateLzzBus(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzBus(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzBusService.self().delLzzBusById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzBus.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzBus(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzBus());
			}else{
				LzzBus sin = LzzBusService.self().getLzzBusById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzBus.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzBus() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzBus> all = LzzBusService.self().getAllLzzBusIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzBus.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzBusLine() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzBusLineData_obj = JSONObject.fromObject(subData);
			LzzBusLine obj = null;
			if(LzzBusLineData_obj.getString("id").equals("")){
				obj = new LzzBusLine();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzBusLineService.self().getLzzBusLineById(LzzBusLineData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzBusLineData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzBusLineData_obj.getString("id").equals("")){
				LzzBusLineService.self().saveLzzBusLine(obj);
			}else{
				LzzBusLineService.self().updateLzzBusLine(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzBusLine(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzBusLineService.self().delLzzBusLineById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzBusLine.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzBusLine(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzBusLine());
			}else{
				LzzBusLine sin = LzzBusLineService.self().getLzzBusLineById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzBusLine.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzBusLine() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzBusLine> all = LzzBusLineService.self().getAllLzzBusLineIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzBusLine.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzCompany() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzCompanyData_obj = JSONObject.fromObject(subData);
			LzzCompany obj = null;
			if(LzzCompanyData_obj.getString("id").equals("")){
				obj = new LzzCompany();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzCompanyService.self().getLzzCompanyById(LzzCompanyData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzCompanyData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzCompanyData_obj.getString("id").equals("")){
				LzzCompanyService.self().saveLzzCompany(obj);
			}else{
				LzzCompanyService.self().updateLzzCompany(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzCompany(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzCompanyService.self().delLzzCompanyById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCompany.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzCompany(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzCompany());
			}else{
				LzzCompany sin = LzzCompanyService.self().getLzzCompanyById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCompany.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzCompany() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzCompany> all = LzzCompanyService.self().getAllLzzCompanyIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCompany.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzSensor() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzSensorData_obj = JSONObject.fromObject(subData);
			LzzSensor obj = null;
			if(LzzSensorData_obj.getString("id").equals("")){
				obj = new LzzSensor();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzSensorService.self().getLzzSensorById(LzzSensorData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzSensorData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzSensorData_obj.getString("id").equals("")){
				LzzSensorService.self().saveLzzSensor(obj);
			}else{
				LzzSensorService.self().updateLzzSensor(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzSensor(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzSensorService.self().delLzzSensorById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzSensor.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzSensor(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzSensor());
			}else{
				LzzSensor sin = LzzSensorService.self().getLzzSensorById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzSensor.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzSensor() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzSensor> all = LzzSensorService.self().getAllLzzSensorIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzSensor.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzSensorData() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzSensorDataData_obj = JSONObject.fromObject(subData);
			LzzSensorData obj = null;
			if(LzzSensorDataData_obj.getString("id").equals("")){
				obj = new LzzSensorData();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzSensorDataService.self().getLzzSensorDataById(LzzSensorDataData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzSensorDataData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzSensorDataData_obj.getString("id").equals("")){
				LzzSensorDataService.self().saveLzzSensorData(obj);
			}else{
				LzzSensorDataService.self().updateLzzSensorData(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzSensorData(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzSensorDataService.self().delLzzSensorDataById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzSensorData.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzSensorData(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzSensorData());
			}else{
				LzzSensorData sin = LzzSensorDataService.self().getLzzSensorDataById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzSensorData.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzSensorData() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzSensorData> all = LzzSensorDataService.self().getAllLzzSensorDataIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzSensorData.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzUser() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserData_obj = JSONObject.fromObject(subData);
			LzzUser obj = null;
			if(LzzUserData_obj.getString("id").equals("")){
				obj = new LzzUser();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserById(LzzUserData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUser(obj);
			}else{
				LzzUserService.self().updateLzzUser(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzUser(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUser.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzUser(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUser());
			}else{
				LzzUser sin = LzzUserService.self().getLzzUserById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUser.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzUser() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUser> all = LzzUserService.self().getAllLzzUserIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUser.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String addOrUpdateLzzCamera() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzCameraData_obj = JSONObject.fromObject(subData);
			LzzCamera obj = null;
			if(LzzCameraData_obj.getString("id").equals("")){
				obj = new LzzCamera();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzCameraService.self().getLzzCameraById(LzzCameraData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzCameraData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzCameraData_obj.getString("id").equals("")){
				LzzCameraService.self().saveLzzCamera(obj);
			}else{
				LzzCameraService.self().updateLzzCamera(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String removeSingleLzzCamera(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzCameraService.self().delLzzCameraById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCamera.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getSingleLzzCamera(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzCamera());
			}else{
				LzzCamera sin = LzzCameraService.self().getLzzCameraById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCamera.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getAllLzzCamera() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzCamera> all = LzzCameraService.self().getAllLzzCameraIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCamera.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private List filterList(List orig) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(null!=fieldName
				&& !"".equals(fieldName)
				&& orig.size()>0){
			Class clazz = orig.get(0).getClass();
			String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method get_method = clazz.getDeclaredMethod("get" + name);
			
			for(int i=0; i<orig.size(); i++){
				String field_value = (String) get_method.invoke(orig.get(i));
				if(!field_value.contains(searchValue)){
					orig.remove(i);
					i--;
				}
			}
		}
		
		return orig;
	}
}

