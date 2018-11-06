package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzCamera;


public class LzzCameraService{

	// Singleton functions ( construction is private)
	private static LzzCameraService mSelf;	
	public static LzzCameraService self(){
		if(null==mSelf) mSelf = new LzzCameraService();
		
		return mSelf;
	}

	private LzzCameraService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCamera(Object obj) {
		((LzzCamera)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzCamera)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCameraCacheMgr.self().saveLzzCamera(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCamera(Object obj) {
		LzzCameraCacheMgr.self().delLzzCamera(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCameraById(String id) {
		LzzCamera obj = new LzzCamera();
		obj.setId(id);
		delLzzCamera(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzCamera(Object obj) {
		((LzzCamera)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCameraCacheMgr.self().updateLzzCamera(obj);
	}
	
	private List<LzzCamera> getAllLzzCamera() {
		return LzzCameraCacheMgr.self().getAllLzzCamera();
	}
	
	public List<LzzCamera> getAllValidLzzCamera() {
		List<LzzCamera> array_all = getAllLzzCamera();
		List<LzzCamera> rslt = new ArrayList<LzzCamera>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzCamera> getAllLzzCameraIgnoreDr() {
		return getAllLzzCamera();
	}
	
	public LzzCamera getLzzCameraById(String id){
		return LzzCameraCacheMgr.self().getLzzCameraById(id);
	}




	public List<LzzCamera> getLzzCameraListByBusId(String busId){
		List<LzzCamera> array_all = getAllValidLzzCamera();
		List<LzzCamera> rslt = new ArrayList<LzzCamera>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getBusId()
				&& array_all.get(i).getBusId().equals(busId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





}



