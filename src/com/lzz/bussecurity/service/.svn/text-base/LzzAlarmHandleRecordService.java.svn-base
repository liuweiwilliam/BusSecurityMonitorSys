package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzAlarmHandleRecord;


public class LzzAlarmHandleRecordService{

	// Singleton functions ( construction is private)
	private static LzzAlarmHandleRecordService mSelf;	
	public static LzzAlarmHandleRecordService self(){
		if(null==mSelf) mSelf = new LzzAlarmHandleRecordService();
		
		return mSelf;
	}

	private LzzAlarmHandleRecordService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzAlarmHandleRecord(Object obj) {
		((LzzAlarmHandleRecord)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzAlarmHandleRecord)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzAlarmHandleRecordCacheMgr.self().saveLzzAlarmHandleRecord(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzAlarmHandleRecord(Object obj) {
		LzzAlarmHandleRecordCacheMgr.self().delLzzAlarmHandleRecord(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzAlarmHandleRecordById(String id) {
		LzzAlarmHandleRecord obj = new LzzAlarmHandleRecord();
		obj.setId(id);
		delLzzAlarmHandleRecord(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzAlarmHandleRecord(Object obj) {
		((LzzAlarmHandleRecord)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzAlarmHandleRecordCacheMgr.self().updateLzzAlarmHandleRecord(obj);
	}
	
	public void saveOrUpdateLzzAlarmHandleRecord(Object obj){
		String id = ((LzzAlarmHandleRecord)obj).getId();
		LzzAlarmHandleRecord obj_check = getLzzAlarmHandleRecordById(id);
		if(null==obj_check){
			saveLzzAlarmHandleRecord(obj);
		}else{
			updateLzzAlarmHandleRecord(obj);
		}
	}
	
	private List<LzzAlarmHandleRecord> getAllLzzAlarmHandleRecord() {
		return LzzAlarmHandleRecordCacheMgr.self().getAllLzzAlarmHandleRecord();
	}
	
	public List<LzzAlarmHandleRecord> getAllValidLzzAlarmHandleRecord() {
		List<LzzAlarmHandleRecord> array_all = getAllLzzAlarmHandleRecord();
		List<LzzAlarmHandleRecord> rslt = new ArrayList<LzzAlarmHandleRecord>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzAlarmHandleRecord> getAllLzzAlarmHandleRecordIgnoreDr() {
		return getAllLzzAlarmHandleRecord();
	}
	
	public LzzAlarmHandleRecord getLzzAlarmHandleRecordById(String id){
		return LzzAlarmHandleRecordCacheMgr.self().getLzzAlarmHandleRecordById(id);
	}



	public LzzAlarmHandleRecord getLzzAlarmHandleRecordByAlarmId(String alarmId){
		List<LzzAlarmHandleRecord> array_all = getAllValidLzzAlarmHandleRecord();
		LzzAlarmHandleRecord rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getAlarmId()
				&& array_all.get(i).getAlarmId().equals(alarmId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}






}



