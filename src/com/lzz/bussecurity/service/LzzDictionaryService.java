package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzDictionary;


public class LzzDictionaryService{

	// Singleton functions ( construction is private)
	private static LzzDictionaryService mSelf;	
	public static LzzDictionaryService self(){
		if(null==mSelf) mSelf = new LzzDictionaryService();
		
		return mSelf;
	}

	private LzzDictionaryService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzDictionary(Object obj) {
		((LzzDictionary)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzDictionary)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzDictionaryCacheMgr.self().saveLzzDictionary(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzDictionary(Object obj) {
		LzzDictionaryCacheMgr.self().delLzzDictionary(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzDictionaryById(String id) {
		LzzDictionary obj = new LzzDictionary();
		obj.setId(id);
		delLzzDictionary(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzDictionary(Object obj) {
		((LzzDictionary)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzDictionaryCacheMgr.self().updateLzzDictionary(obj);
	}
	
	private List<LzzDictionary> getAllLzzDictionary() {
		return LzzDictionaryCacheMgr.self().getAllLzzDictionary();
	}
	
	public List<LzzDictionary> getAllValidLzzDictionary() {
		List<LzzDictionary> array_all = getAllLzzDictionary();
		List<LzzDictionary> rslt = new ArrayList<LzzDictionary>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzDictionary> getAllLzzDictionaryIgnoreDr() {
		return getAllLzzDictionary();
	}
	
	public LzzDictionary getLzzDictionaryById(String id){
		return LzzDictionaryCacheMgr.self().getLzzDictionaryById(id);
	}




	public List<LzzDictionary> getLzzDictionaryListByCategory(String category){
		List<LzzDictionary> array_all = getAllValidLzzDictionary();
		List<LzzDictionary> rslt = new ArrayList<LzzDictionary>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCategory()
				&& array_all.get(i).getCategory().equals(category)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}


	public LzzDictionary getLzzDictionaryByCategoryAndValue(String category, String value){
		List<LzzDictionary> array_all = getAllValidLzzDictionary();
		LzzDictionary rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCategory()
				&& array_all.get(i).getCategory().equals(category)
				&& null!=array_all.get(i).getValue()
				&& array_all.get(i).getValue().equals(value)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzDictionary getLzzDictionaryByCategoryAndInnerLevel(String category, String innerLevel){
		List<LzzDictionary> array_all = getAllValidLzzDictionary();
		LzzDictionary rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCategory()
				&& array_all.get(i).getCategory().equals(category)
				&& null!=array_all.get(i).getInnerLevel()
				&& array_all.get(i).getInnerLevel().equals(innerLevel)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}




}



