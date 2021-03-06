package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzSensorData;

import com.lzz.bussecurity.pojo.LzzSensorAlarmData;


public class LzzSensorDataService{

	// Singleton functions ( construction is private)
	private static LzzSensorDataService mSelf;	
	public static LzzSensorDataService self(){
		if(null==mSelf) mSelf = new LzzSensorDataService();
		
		return mSelf;
	}

	private LzzSensorDataService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensorData(Object obj) {
		((LzzSensorData)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzSensorData)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzSensorDataCacheMgr.self().saveLzzSensorData(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzSensorData(Object obj) {
		LzzSensorDataCacheMgr.self().delLzzSensorData(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzSensorDataById(String id) {
		LzzSensorData obj = new LzzSensorData();
		obj.setId(id);
		delLzzSensorData(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzSensorData(Object obj) {
		((LzzSensorData)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzSensorDataCacheMgr.self().updateLzzSensorData(obj);
	}
	
	public void saveOrUpdateLzzSensorData(Object obj){
		String id = ((LzzSensorData)obj).getId();
		LzzSensorData obj_check = getLzzSensorDataById(id);
		if(null==obj_check){
			saveLzzSensorData(obj);
		}else{
			updateLzzSensorData(obj);
		}
	}
	
	private List<LzzSensorData> getAllLzzSensorData() {
		return LzzSensorDataCacheMgr.self().getAllLzzSensorData();
	}
	
	public List<LzzSensorData> getAllValidLzzSensorData() {
		List<LzzSensorData> array_all = getAllLzzSensorData();
		List<LzzSensorData> rslt = new ArrayList<LzzSensorData>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzSensorData> getAllLzzSensorDataIgnoreDr() {
		return getAllLzzSensorData();
	}
	
	public LzzSensorData getLzzSensorDataById(String id){
		return LzzSensorDataCacheMgr.self().getLzzSensorDataById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensorAlarmData(Object obj) {
		((LzzSensorAlarmData)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzSensorAlarmData)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzSensorDataCacheMgr.self().saveLzzSensorAlarmData(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzSensorAlarmData(Object obj) {
		LzzSensorDataCacheMgr.self().delLzzSensorAlarmData(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzSensorAlarmDataById(String id) {
		LzzSensorAlarmData obj = new LzzSensorAlarmData();
		obj.setId(id);
		delLzzSensorAlarmData(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzSensorAlarmData(Object obj) {
		((LzzSensorAlarmData)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzSensorDataCacheMgr.self().updateLzzSensorAlarmData(obj);
	}
	
	public void saveOrUpdateLzzSensorAlarmData(Object obj){
		String id = ((LzzSensorAlarmData)obj).getId();
		LzzSensorAlarmData obj_check = getLzzSensorAlarmDataById(id);
		if(null==obj_check){
			saveLzzSensorAlarmData(obj);
		}else{
			updateLzzSensorAlarmData(obj);
		}
	}
	
	private List<LzzSensorAlarmData> getAllLzzSensorAlarmData() {
		return LzzSensorDataCacheMgr.self().getAllLzzSensorAlarmData();
	}
	
	public List<LzzSensorAlarmData> getAllValidLzzSensorAlarmData() {
		List<LzzSensorAlarmData> array_all = getAllLzzSensorAlarmData();
		List<LzzSensorAlarmData> rslt = new ArrayList<LzzSensorAlarmData>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzSensorAlarmData> getAllLzzSensorAlarmDataIgnoreDr() {
		return getAllLzzSensorAlarmData();
	}
	
	public LzzSensorAlarmData getLzzSensorAlarmDataById(String id){
		return LzzSensorDataCacheMgr.self().getLzzSensorAlarmDataById(id);
	}




	public List<LzzSensorData> getLzzSensorDataListByBusId(String busId){
		List<LzzSensorData> array_all = getAllValidLzzSensorData();
		List<LzzSensorData> rslt = new ArrayList<LzzSensorData>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getBusId()
				&& array_all.get(i).getBusId().equals(busId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzSensorAlarmData> getLzzSensorAlarmDataListByBusId(String busId){
		List<LzzSensorAlarmData> array_all = getAllValidLzzSensorAlarmData();
		List<LzzSensorAlarmData> rslt = new ArrayList<LzzSensorAlarmData>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getBusId()
				&& array_all.get(i).getBusId().equals(busId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}



	public List<LzzSensorAlarmData> getLzzSensorAlarmDataListByBusIdAndHandled(String busId, String handled){
		List<LzzSensorAlarmData> array_all = getAllValidLzzSensorAlarmData();
		List<LzzSensorAlarmData> rslt = new ArrayList<LzzSensorAlarmData>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getBusId()
				&& array_all.get(i).getBusId().equals(busId)
				&& null!=array_all.get(i).getHandled()
				&& array_all.get(i).getHandled().equals(handled)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}



}



