package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzSensor;


public class LzzSensorService{

	// Singleton functions ( construction is private)
	private static LzzSensorService mSelf;	
	public static LzzSensorService self(){
		if(null==mSelf) mSelf = new LzzSensorService();
		
		return mSelf;
	}

	private LzzSensorService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensor(Object obj) {
		((LzzSensor)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzSensor)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzSensorCacheMgr.self().saveLzzSensor(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzSensor(Object obj) {
		LzzSensorCacheMgr.self().delLzzSensor(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzSensorById(String id) {
		LzzSensor obj = new LzzSensor();
		obj.setId(id);
		delLzzSensor(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzSensor(Object obj) {
		((LzzSensor)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzSensorCacheMgr.self().updateLzzSensor(obj);
	}
	
	private List<LzzSensor> getAllLzzSensor() {
		return LzzSensorCacheMgr.self().getAllLzzSensor();
	}
	
	public List<LzzSensor> getAllValidLzzSensor() {
		List<LzzSensor> array_all = getAllLzzSensor();
		List<LzzSensor> rslt = new ArrayList<LzzSensor>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzSensor> getAllLzzSensorIgnoreDr() {
		return getAllLzzSensor();
	}
	
	public LzzSensor getLzzSensorById(String id){
		return LzzSensorCacheMgr.self().getLzzSensorById(id);
	}




	public List<LzzSensor> getLzzSensorListByBusId(String busId){
		List<LzzSensor> array_all = getAllValidLzzSensor();
		List<LzzSensor> rslt = new ArrayList<LzzSensor>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getBusId()
				&& array_all.get(i).getBusId().equals(busId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





}



