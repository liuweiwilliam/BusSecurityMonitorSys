package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzBus;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzBusCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzBus> mLzzBuss;
	private boolean mLzzBussLoaded;
	private Hashtable<String, LzzBus> mLzzBusHash;


	// Singleton functions ( construction is private)
	private final static LzzBusCacheMgr singleton = new LzzBusCacheMgr ();	
	public static LzzBusCacheMgr self(){
		return singleton;
	}
	public LzzBusCacheMgr getSelf(){
		return self();
	}
	private LzzBusCacheMgr (){
		dao = new LzzDao();
		mLzzBuss = new Vector<LzzBus>();
		mLzzBussLoaded = false;
		mLzzBusHash = new Hashtable<String,LzzBus>();

	}
	synchronized private boolean loadLzzBuss(){

		if(!mLzzBussLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzBus> array = (List<LzzBus>)dao.queryAll("LzzBus");
			
			for(int i = 0;i < array.size();i++){
				mLzzBuss.add(array.get(i));
				mLzzBusHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzBussLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzBuss();	

		return true;
	}

	public boolean clearCache(){
		mLzzBuss.clear();
		mLzzBussLoaded = false;
		mLzzBusHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzBus(Object obj) {
		loadLzzBuss();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzBus obj2 = (LzzBus)obj;
		mLzzBuss.add(obj2);
		mLzzBusHash.put(obj2.getId(), obj2);
	}
	public void delLzzBus(Object obj) {
		loadLzzBuss();

		LzzBus obj2 = (LzzBus)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzBusHash.get(obj2.getId()));

		for(LzzBus msg : mLzzBuss){
			if(msg.getId().equals(obj2.getId())){
				mLzzBuss.remove(msg);
				break;
			}
		}
		mLzzBusHash.remove(obj2.getId());
		
	}
	public void updateLzzBus(Object obj) {
		loadLzzBuss();
		
		for(int i = 0; i < mLzzBuss.size(); i++){
			if(mLzzBuss.get(i).getId().equals(((LzzBus)obj).getId())){
					mLzzBuss.set(i, (LzzBus) obj);
					break;
			}
		}
		LzzBus tmp = mLzzBusHash.get(((LzzBus)obj).getId());
		tmp.constructWith((LzzBus)obj);
		
		//mLzzBusHash.remove(((LzzBus)obj).getId());
		//mLzzBusHash.put(((LzzBus)obj).getId(), (LzzBus)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzBus> getAllLzzBus() {
		loadLzzBuss();
		
		List<LzzBus> objects = new ArrayList<LzzBus>();
		for(int i = 0;i < mLzzBuss.size(); i++)
		{
			objects.add(mLzzBuss.get(i).clone());
		}
		return objects;
	}

	public LzzBus getLzzBusById(String id){
		if(null==id) return null;
		loadLzzBuss();
		if(null==mLzzBusHash.get(id)) return null;
		return mLzzBusHash.get(id).clone();
	}


}
