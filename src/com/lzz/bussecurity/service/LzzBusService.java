package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzBus;


public class LzzBusService{

	// Singleton functions ( construction is private)
	private static LzzBusService mSelf;	
	public static LzzBusService self(){
		if(null==mSelf) mSelf = new LzzBusService();
		
		return mSelf;
	}

	private LzzBusService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzBus(Object obj) {
		((LzzBus)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzBus)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzBusCacheMgr.self().saveLzzBus(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzBus(Object obj) {
		LzzBusCacheMgr.self().delLzzBus(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzBusById(String id) {
		LzzBus obj = new LzzBus();
		obj.setId(id);
		delLzzBus(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzBus(Object obj) {
		((LzzBus)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzBusCacheMgr.self().updateLzzBus(obj);
	}
	
	public void saveOrUpdateLzzBus(Object obj){
		String id = ((LzzBus)obj).getId();
		LzzBus obj_check = getLzzBusById(id);
		if(null==obj_check){
			saveLzzBus(obj);
		}else{
			updateLzzBus(obj);
		}
	}
	
	private List<LzzBus> getAllLzzBus() {
		return LzzBusCacheMgr.self().getAllLzzBus();
	}
	
	public List<LzzBus> getAllValidLzzBus() {
		List<LzzBus> array_all = getAllLzzBus();
		List<LzzBus> rslt = new ArrayList<LzzBus>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzBus> getAllLzzBusIgnoreDr() {
		return getAllLzzBus();
	}
	
	public LzzBus getLzzBusById(String id){
		return LzzBusCacheMgr.self().getLzzBusById(id);
	}



	public LzzBus getLzzBusByCarNum(String carNum){
		List<LzzBus> array_all = getAllValidLzzBus();
		LzzBus rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCarNum()
				&& array_all.get(i).getCarNum().equals(carNum)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}


	public List<LzzBus> getLzzBusListByLineId(String lineId){
		List<LzzBus> array_all = getAllValidLzzBus();
		List<LzzBus> rslt = new ArrayList<LzzBus>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getLineId()
				&& array_all.get(i).getLineId().equals(lineId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzBus> getLzzBusListByStatusId(String statusId){
		List<LzzBus> array_all = getAllValidLzzBus();
		List<LzzBus> rslt = new ArrayList<LzzBus>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getStatusId()
				&& array_all.get(i).getStatusId().equals(statusId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





}



