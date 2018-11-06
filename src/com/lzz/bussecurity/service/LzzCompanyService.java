package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzCompany;


public class LzzCompanyService{

	// Singleton functions ( construction is private)
	private static LzzCompanyService mSelf;	
	public static LzzCompanyService self(){
		if(null==mSelf) mSelf = new LzzCompanyService();
		
		return mSelf;
	}

	private LzzCompanyService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCompany(Object obj) {
		((LzzCompany)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzCompany)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCompanyCacheMgr.self().saveLzzCompany(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCompany(Object obj) {
		LzzCompanyCacheMgr.self().delLzzCompany(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCompanyById(String id) {
		LzzCompany obj = new LzzCompany();
		obj.setId(id);
		delLzzCompany(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzCompany(Object obj) {
		((LzzCompany)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCompanyCacheMgr.self().updateLzzCompany(obj);
	}
	
	private List<LzzCompany> getAllLzzCompany() {
		return LzzCompanyCacheMgr.self().getAllLzzCompany();
	}
	
	public List<LzzCompany> getAllValidLzzCompany() {
		List<LzzCompany> array_all = getAllLzzCompany();
		List<LzzCompany> rslt = new ArrayList<LzzCompany>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzCompany> getAllLzzCompanyIgnoreDr() {
		return getAllLzzCompany();
	}
	
	public LzzCompany getLzzCompanyById(String id){
		return LzzCompanyCacheMgr.self().getLzzCompanyById(id);
	}




	public List<LzzCompany> getLzzCompanyListByParentId(String parentId){
		List<LzzCompany> array_all = getAllValidLzzCompany();
		List<LzzCompany> rslt = new ArrayList<LzzCompany>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getParentId()
				&& array_all.get(i).getParentId().equals(parentId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





}



