package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzSensorData;


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




	public List<LzzSensorData> getLzzSensorDataListBySensorId(String sensorId){
		List<LzzSensorData> array_all = getAllValidLzzSensorData();
		List<LzzSensorData> rslt = new ArrayList<LzzSensorData>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getSensorId()
				&& array_all.get(i).getSensorId().equals(sensorId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
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





}



