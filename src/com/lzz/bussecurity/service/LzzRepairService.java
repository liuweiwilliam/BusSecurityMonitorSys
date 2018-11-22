package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzRepair;


public class LzzRepairService{

	// Singleton functions ( construction is private)
	private static LzzRepairService mSelf;	
	public static LzzRepairService self(){
		if(null==mSelf) mSelf = new LzzRepairService();
		
		return mSelf;
	}

	private LzzRepairService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzRepair(Object obj) {
		((LzzRepair)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzRepair)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzRepairCacheMgr.self().saveLzzRepair(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzRepair(Object obj) {
		LzzRepairCacheMgr.self().delLzzRepair(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzRepairById(String id) {
		LzzRepair obj = new LzzRepair();
		obj.setId(id);
		delLzzRepair(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzRepair(Object obj) {
		((LzzRepair)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzRepairCacheMgr.self().updateLzzRepair(obj);
	}
	
	public void saveOrUpdateLzzRepair(Object obj){
		String id = ((LzzRepair)obj).getId();
		LzzRepair obj_check = getLzzRepairById(id);
		if(null==obj_check){
			saveLzzRepair(obj);
		}else{
			updateLzzRepair(obj);
		}
	}
	
	private List<LzzRepair> getAllLzzRepair() {
		return LzzRepairCacheMgr.self().getAllLzzRepair();
	}
	
	public List<LzzRepair> getAllValidLzzRepair() {
		List<LzzRepair> array_all = getAllLzzRepair();
		List<LzzRepair> rslt = new ArrayList<LzzRepair>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzRepair> getAllLzzRepairIgnoreDr() {
		return getAllLzzRepair();
	}
	
	public LzzRepair getLzzRepairById(String id){
		return LzzRepairCacheMgr.self().getLzzRepairById(id);
	}



	public LzzRepair getLzzRepairByBusId(String busId){
		List<LzzRepair> array_all = getAllValidLzzRepair();
		LzzRepair rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getBusId()
				&& array_all.get(i).getBusId().equals(busId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}






}



