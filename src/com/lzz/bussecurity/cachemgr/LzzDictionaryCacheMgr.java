package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzDictionary;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzDictionaryCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzDictionary> mLzzDictionarys;
	private boolean mLzzDictionarysLoaded;
	private Hashtable<String, LzzDictionary> mLzzDictionaryHash;


	// Singleton functions ( construction is private)
	private final static LzzDictionaryCacheMgr singleton = new LzzDictionaryCacheMgr ();	
	public static LzzDictionaryCacheMgr self(){
		return singleton;
	}
	public LzzDictionaryCacheMgr getSelf(){
		return self();
	}
	private LzzDictionaryCacheMgr (){
		dao = new LzzDao();
		mLzzDictionarys = new Vector<LzzDictionary>();
		mLzzDictionarysLoaded = false;
		mLzzDictionaryHash = new Hashtable<String,LzzDictionary>();

	}
	synchronized private boolean loadLzzDictionarys(){

		if(!mLzzDictionarysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzDictionary> array = (List<LzzDictionary>)dao.queryAll("LzzDictionary");
			
			for(int i = 0;i < array.size();i++){
				mLzzDictionarys.add(array.get(i));
				mLzzDictionaryHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzDictionarysLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzDictionarys();	

		return true;
	}

	public boolean clearCache(){
		mLzzDictionarys.clear();
		mLzzDictionarysLoaded = false;
		mLzzDictionaryHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzDictionary(Object obj) {
		loadLzzDictionarys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzDictionary obj2 = (LzzDictionary)obj;
		mLzzDictionarys.add(obj2);
		mLzzDictionaryHash.put(obj2.getId(), obj2);
	}
	public void delLzzDictionary(Object obj) {
		loadLzzDictionarys();

		LzzDictionary obj2 = (LzzDictionary)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzDictionaryHash.get(obj2.getId()));

		for(LzzDictionary msg : mLzzDictionarys){
			if(msg.getId().equals(obj2.getId())){
				mLzzDictionarys.remove(msg);
				break;
			}
		}
		mLzzDictionaryHash.remove(obj2.getId());
		
	}
	public void updateLzzDictionary(Object obj) {
		loadLzzDictionarys();
		
		for(int i = 0; i < mLzzDictionarys.size(); i++){
			if(mLzzDictionarys.get(i).getId().equals(((LzzDictionary)obj).getId())){
					mLzzDictionarys.set(i, (LzzDictionary) obj);
					break;
			}
		}
		LzzDictionary tmp = mLzzDictionaryHash.get(((LzzDictionary)obj).getId());
		tmp.constructWith((LzzDictionary)obj);
		
		//mLzzDictionaryHash.remove(((LzzDictionary)obj).getId());
		//mLzzDictionaryHash.put(((LzzDictionary)obj).getId(), (LzzDictionary)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzDictionary> getAllLzzDictionary() {
		loadLzzDictionarys();
		
		List<LzzDictionary> objects = new ArrayList<LzzDictionary>();
		for(int i = 0;i < mLzzDictionarys.size(); i++)
		{
			objects.add(mLzzDictionarys.get(i).clone());
		}
		return objects;
	}

	public LzzDictionary getLzzDictionaryById(String id){
		if(null==id) return null;
		loadLzzDictionarys();
		if(null==mLzzDictionaryHash.get(id)) return null;
		return mLzzDictionaryHash.get(id).clone();
	}


}
