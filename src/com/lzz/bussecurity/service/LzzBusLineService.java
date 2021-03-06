package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzBusLine;


public class LzzBusLineService{

	// Singleton functions ( construction is private)
	private static LzzBusLineService mSelf;	
	public static LzzBusLineService self(){
		if(null==mSelf) mSelf = new LzzBusLineService();
		
		return mSelf;
	}

	private LzzBusLineService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzBusLine(Object obj) {
		((LzzBusLine)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzBusLine)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzBusLineCacheMgr.self().saveLzzBusLine(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzBusLine(Object obj) {
		LzzBusLineCacheMgr.self().delLzzBusLine(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzBusLineById(String id) {
		LzzBusLine obj = new LzzBusLine();
		obj.setId(id);
		delLzzBusLine(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzBusLine(Object obj) {
		((LzzBusLine)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzBusLineCacheMgr.self().updateLzzBusLine(obj);
	}
	
	public void saveOrUpdateLzzBusLine(Object obj){
		String id = ((LzzBusLine)obj).getId();
		LzzBusLine obj_check = getLzzBusLineById(id);
		if(null==obj_check){
			saveLzzBusLine(obj);
		}else{
			updateLzzBusLine(obj);
		}
	}
	
	private List<LzzBusLine> getAllLzzBusLine() {
		return LzzBusLineCacheMgr.self().getAllLzzBusLine();
	}
	
	public List<LzzBusLine> getAllValidLzzBusLine() {
		List<LzzBusLine> array_all = getAllLzzBusLine();
		List<LzzBusLine> rslt = new ArrayList<LzzBusLine>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzBusLine> getAllLzzBusLineIgnoreDr() {
		return getAllLzzBusLine();
	}
	
	public LzzBusLine getLzzBusLineById(String id){
		return LzzBusLineCacheMgr.self().getLzzBusLineById(id);
	}




	public List<LzzBusLine> getLzzBusLineListByCompanyId(String companyId){
		List<LzzBusLine> array_all = getAllValidLzzBusLine();
		List<LzzBusLine> rslt = new ArrayList<LzzBusLine>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCompanyId()
				&& array_all.get(i).getCompanyId().equals(companyId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}


	public LzzBusLine getLzzBusLineByCompanyIdAndLineNum(String companyId, String lineNum){
		List<LzzBusLine> array_all = getAllValidLzzBusLine();
		LzzBusLine rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCompanyId()
				&& array_all.get(i).getCompanyId().equals(companyId)
				&& null!=array_all.get(i).getLineNum()
				&& array_all.get(i).getLineNum().equals(lineNum)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}




}



