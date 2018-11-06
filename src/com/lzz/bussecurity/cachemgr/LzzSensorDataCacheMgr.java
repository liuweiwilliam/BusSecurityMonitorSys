package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzSensorData;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzSensorDataCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzSensorData> mLzzSensorDatas;
	private boolean mLzzSensorDatasLoaded;
	private Hashtable<String, LzzSensorData> mLzzSensorDataHash;


	// Singleton functions ( construction is private)
	private final static LzzSensorDataCacheMgr singleton = new LzzSensorDataCacheMgr ();	
	public static LzzSensorDataCacheMgr self(){
		return singleton;
	}
	public LzzSensorDataCacheMgr getSelf(){
		return self();
	}
	private LzzSensorDataCacheMgr (){
		dao = new LzzDao();
		mLzzSensorDatas = new Vector<LzzSensorData>();
		mLzzSensorDatasLoaded = false;
		mLzzSensorDataHash = new Hashtable<String,LzzSensorData>();

	}
	synchronized private boolean loadLzzSensorDatas(){
		if(!mLzzSensorDatasLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzSensorData> array = (List<LzzSensorData>)dao.queryAll("LzzSensorData");
			
			for(int i = 0;i < array.size();i++){
				mLzzSensorDatas.add(array.get(i));
				mLzzSensorDataHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzSensorDatasLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzSensorDatas();	

		return true;
	}

	public boolean clearCache(){
		mLzzSensorDatas.clear();
		mLzzSensorDatasLoaded = false;
		mLzzSensorDataHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensorData(Object obj) {
		loadLzzSensorDatas();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzSensorData obj2 = (LzzSensorData)obj;
		mLzzSensorDatas.add(obj2);
		mLzzSensorDataHash.put(obj2.getId(), obj2);
	}
	public void delLzzSensorData(Object obj) {
		loadLzzSensorDatas();

		LzzSensorData obj2 = (LzzSensorData)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzSensorDataHash.get(obj2.getId()));

		for(LzzSensorData msg : mLzzSensorDatas){
			if(msg.getId().equals(obj2.getId())){
				mLzzSensorDatas.remove(msg);
				break;
			}
		}
		mLzzSensorDataHash.remove(obj2.getId());
		
	}
	public void updateLzzSensorData(Object obj) {
		loadLzzSensorDatas();
		
		for(int i = 0; i < mLzzSensorDatas.size(); i++){
			if(mLzzSensorDatas.get(i).getId().equals(((LzzSensorData)obj).getId())){
					mLzzSensorDatas.set(i, (LzzSensorData) obj);
					break;
			}
		}
		LzzSensorData tmp = mLzzSensorDataHash.get(((LzzSensorData)obj).getId());
		tmp.constructWith((LzzSensorData)obj);
		
		//mLzzSensorDataHash.remove(((LzzSensorData)obj).getId());
		//mLzzSensorDataHash.put(((LzzSensorData)obj).getId(), (LzzSensorData)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzSensorData> getAllLzzSensorData() {
		loadLzzSensorDatas();
		
		List<LzzSensorData> objects = new ArrayList<LzzSensorData>();
		for(int i = 0;i < mLzzSensorDatas.size(); i++)
		{
			objects.add(mLzzSensorDatas.get(i).clone());
		}
		return objects;
	}

	public LzzSensorData getLzzSensorDataById(String id){
		if(null==id) return null;
		loadLzzSensorDatas();
		if(null==mLzzSensorDataHash.get(id)) return null;
		return mLzzSensorDataHash.get(id).clone();
	}


}
