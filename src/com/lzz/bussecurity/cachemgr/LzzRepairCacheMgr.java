package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzRepair;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzRepairCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzRepair> mLzzRepairs;
	private boolean mLzzRepairsLoaded;
	private Hashtable<String, LzzRepair> mLzzRepairHash;


	// Singleton functions ( construction is private)
	private final static LzzRepairCacheMgr singleton = new LzzRepairCacheMgr ();	
	public static LzzRepairCacheMgr self(){
		return singleton;
	}
	public LzzRepairCacheMgr getSelf(){
		return self();
	}
	private LzzRepairCacheMgr (){
		dao = new LzzDao();
		mLzzRepairs = new Vector<LzzRepair>();
		mLzzRepairsLoaded = false;
		mLzzRepairHash = new Hashtable<String,LzzRepair>();

	}
	synchronized private boolean loadLzzRepairs(){

		if(!mLzzRepairsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzRepair> array = (List<LzzRepair>)dao.queryAll("lzzrepair", LzzRepair.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzRepairs.add(array.get(i));
				mLzzRepairHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzRepairsLoaded = true;
		}
		return true;
	}


	public boolean loadAllDB(){
		loadLzzRepairs();	

		return true;
	}

	public boolean clearCache(){
		mLzzRepairs.clear();
		mLzzRepairsLoaded = false;
		mLzzRepairHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzRepair(Object obj) {
		loadLzzRepairs();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzRepair obj2 = (LzzRepair)obj;
		mLzzRepairs.add(obj2);
		mLzzRepairHash.put(obj2.getId(), obj2);
	}
	public void delLzzRepair(Object obj) {
		loadLzzRepairs();

		LzzRepair obj2 = (LzzRepair)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzRepairHash.get(obj2.getId()));

		for(LzzRepair msg : mLzzRepairs){
			if(msg.getId().equals(obj2.getId())){
				mLzzRepairs.remove(msg);
				break;
			}
		}
		mLzzRepairHash.remove(obj2.getId());
	}
	public void updateLzzRepair(Object obj) {
		loadLzzRepairs();
		
		for(int i = 0; i < mLzzRepairs.size(); i++){
			if(mLzzRepairs.get(i).getId().equals(((LzzRepair)obj).getId())){
					mLzzRepairs.set(i, (LzzRepair) obj);
					break;
			}
		}
		LzzRepair tmp = mLzzRepairHash.get(((LzzRepair)obj).getId());
		tmp.constructWith((LzzRepair)obj);
		
		//mLzzRepairHash.remove(((LzzRepair)obj).getId());
		//mLzzRepairHash.put(((LzzRepair)obj).getId(), (LzzRepair)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzRepair> getAllLzzRepair() {
		loadLzzRepairs();
		
		List<LzzRepair> objects = new ArrayList<LzzRepair>();
		for(int i = 0;i < mLzzRepairs.size(); i++)
		{
			objects.add(mLzzRepairs.get(i).clone());
		}
		return objects;
	}

	public LzzRepair getLzzRepairById(String id){
		if(null==id) return null;
		loadLzzRepairs();
		if(null==mLzzRepairHash.get(id)) return null;
		return mLzzRepairHash.get(id).clone();
	}
	


}
