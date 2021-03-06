package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzSensor;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzSensorCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzSensor> mLzzSensors;
	private boolean mLzzSensorsLoaded;
	private Hashtable<String, LzzSensor> mLzzSensorHash;


	// Singleton functions ( construction is private)
	private final static LzzSensorCacheMgr singleton = new LzzSensorCacheMgr ();	
	public static LzzSensorCacheMgr self(){
		return singleton;
	}
	public LzzSensorCacheMgr getSelf(){
		return self();
	}
	private LzzSensorCacheMgr (){
		dao = new LzzDao();
		mLzzSensors = new Vector<LzzSensor>();
		mLzzSensorsLoaded = false;
		mLzzSensorHash = new Hashtable<String,LzzSensor>();

	}
	synchronized private boolean loadLzzSensors(){

		if(!mLzzSensorsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzSensor> array = (List<LzzSensor>)dao.queryAll("lzzsensor", LzzSensor.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzSensors.add(array.get(i));
				mLzzSensorHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzSensorsLoaded = true;
		}
		return true;
	}


	public boolean loadAllDB(){
		loadLzzSensors();	

		return true;
	}

	public boolean clearCache(){
		mLzzSensors.clear();
		mLzzSensorsLoaded = false;
		mLzzSensorHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensor(Object obj) {
		loadLzzSensors();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzSensor obj2 = (LzzSensor)obj;
		mLzzSensors.add(obj2);
		mLzzSensorHash.put(obj2.getId(), obj2);
	}
	public void delLzzSensor(Object obj) {
		loadLzzSensors();

		LzzSensor obj2 = (LzzSensor)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzSensorHash.get(obj2.getId()));

		for(LzzSensor msg : mLzzSensors){
			if(msg.getId().equals(obj2.getId())){
				mLzzSensors.remove(msg);
				break;
			}
		}
		mLzzSensorHash.remove(obj2.getId());
	}
	public void updateLzzSensor(Object obj) {
		loadLzzSensors();
		
		for(int i = 0; i < mLzzSensors.size(); i++){
			if(mLzzSensors.get(i).getId().equals(((LzzSensor)obj).getId())){
					mLzzSensors.set(i, (LzzSensor) obj);
					break;
			}
		}
		LzzSensor tmp = mLzzSensorHash.get(((LzzSensor)obj).getId());
		tmp.constructWith((LzzSensor)obj);
		
		//mLzzSensorHash.remove(((LzzSensor)obj).getId());
		//mLzzSensorHash.put(((LzzSensor)obj).getId(), (LzzSensor)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzSensor> getAllLzzSensor() {
		loadLzzSensors();
		
		List<LzzSensor> objects = new ArrayList<LzzSensor>();
		for(int i = 0;i < mLzzSensors.size(); i++)
		{
			objects.add(mLzzSensors.get(i).clone());
		}
		return objects;
	}

	public LzzSensor getLzzSensorById(String id){
		if(null==id) return null;
		loadLzzSensors();
		if(null==mLzzSensorHash.get(id)) return null;
		return mLzzSensorHash.get(id).clone();
	}
	


}
