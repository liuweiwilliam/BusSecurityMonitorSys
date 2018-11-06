package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzUser;


public class LzzUserService{

	// Singleton functions ( construction is private)
	private static LzzUserService mSelf;	
	public static LzzUserService self(){
		if(null==mSelf) mSelf = new LzzUserService();
		
		return mSelf;
	}

	private LzzUserService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUser(Object obj) {
		((LzzUser)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUser)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUser(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUser(Object obj) {
		LzzUserCacheMgr.self().delLzzUser(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserById(String id) {
		LzzUser obj = new LzzUser();
		obj.setId(id);
		delLzzUser(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUser(Object obj) {
		((LzzUser)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUser(obj);
	}
	
	private List<LzzUser> getAllLzzUser() {
		return LzzUserCacheMgr.self().getAllLzzUser();
	}
	
	public List<LzzUser> getAllValidLzzUser() {
		List<LzzUser> array_all = getAllLzzUser();
		List<LzzUser> rslt = new ArrayList<LzzUser>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUser> getAllLzzUserIgnoreDr() {
		return getAllLzzUser();
	}
	
	public LzzUser getLzzUserById(String id){
		return LzzUserCacheMgr.self().getLzzUserById(id);
	}



	public LzzUser getLzzUserByUname(String uname){
		List<LzzUser> array_all = getAllValidLzzUser();
		LzzUser rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUname()
				&& array_all.get(i).getUname().equals(uname)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}






}



