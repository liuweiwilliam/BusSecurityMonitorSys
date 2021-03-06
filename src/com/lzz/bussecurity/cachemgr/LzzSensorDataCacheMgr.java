package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzSensorData;

import com.lzz.bussecurity.pojo.LzzSensorAlarmData;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzSensorDataCacheMgr{
  private Session session;
	private LzzDao dao;

	private Vector<LzzSensorAlarmData> mLzzSensorAlarmDatas;
	private boolean mLzzSensorAlarmDatasLoaded;
	private Hashtable<String, LzzSensorAlarmData> mLzzSensorAlarmDataHash;


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

		mLzzSensorAlarmDatas = new Vector<LzzSensorAlarmData>();
		mLzzSensorAlarmDatasLoaded = false;
		mLzzSensorAlarmDataHash = new Hashtable<String,LzzSensorAlarmData>();

	}

	synchronized private boolean loadLzzSensorAlarmDatas(){

		if(!mLzzSensorAlarmDatasLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzSensorAlarmData> array = (List<LzzSensorAlarmData>)dao.queryAll("lzzsensoralarmdata", LzzSensorAlarmData.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzSensorAlarmDatas.add(array.get(i));
				mLzzSensorAlarmDataHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzSensorAlarmDatasLoaded = true;
		}
		return true;
	}


	public boolean loadAllDB(){

		loadLzzSensorAlarmDatas();	

		return true;
	}

	public boolean clearCache(){

		mLzzSensorAlarmDatas.clear();
		mLzzSensorAlarmDatasLoaded = false;
		mLzzSensorAlarmDataHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensorData(Object obj) {
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		tmp_dao.save(obj);
	}
	public void delLzzSensorData(Object obj) {
		LzzSensorData obj2 = (LzzSensorData)obj;
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		tmp_dao.delete(obj);
	}
	public void updateLzzSensorData(Object obj) {
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		tmp_dao.update(obj);
	}

	public List<LzzSensorData> getAllLzzSensorData() {
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		return (List<LzzSensorData>)tmp_dao.queryAll("lzzsensordata", LzzSensorData.class);
	}

	public LzzSensorData getLzzSensorDataById(String id){
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		LzzSensorData data = (LzzSensorData)tmp_dao.queryById("lzzsensordata", LzzSensorData.class, id);
		if(null==data) return null;
		return data.clone();
	}
	
	public List<LzzSensorData> getLzzSensorDataList(String start_index, String size){
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		List<LzzSensorData> list = tmp_dao.queryWithLimitCount("lzzsensordata", LzzSensorData.class, start_index, size);
		return list;
	}

	public Long getLzzSensorDatasTotalCount(){
		Session session = LzzFactory.currentSession();
		LzzDao tmp_dao = new LzzDao();
		tmp_dao.setSession(session);
		return tmp_dao.queryCount("lzzsensordata");
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzSensorAlarmData(Object obj) {
		loadLzzSensorAlarmDatas();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzSensorAlarmData obj2 = (LzzSensorAlarmData)obj;
		mLzzSensorAlarmDatas.add(obj2);
		mLzzSensorAlarmDataHash.put(obj2.getId(), obj2);
	}
	public void delLzzSensorAlarmData(Object obj) {
		loadLzzSensorAlarmDatas();

		LzzSensorAlarmData obj2 = (LzzSensorAlarmData)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzSensorAlarmDataHash.get(obj2.getId()));

		for(LzzSensorAlarmData msg : mLzzSensorAlarmDatas){
			if(msg.getId().equals(obj2.getId())){
				mLzzSensorAlarmDatas.remove(msg);
				break;
			}
		}
		mLzzSensorAlarmDataHash.remove(obj2.getId());
	}
	public void updateLzzSensorAlarmData(Object obj) {
		loadLzzSensorAlarmDatas();
		
		for(int i = 0; i < mLzzSensorAlarmDatas.size(); i++){
			if(mLzzSensorAlarmDatas.get(i).getId().equals(((LzzSensorAlarmData)obj).getId())){
					mLzzSensorAlarmDatas.set(i, (LzzSensorAlarmData) obj);
					break;
			}
		}
		LzzSensorAlarmData tmp = mLzzSensorAlarmDataHash.get(((LzzSensorAlarmData)obj).getId());
		tmp.constructWith((LzzSensorAlarmData)obj);
		
		//mLzzSensorAlarmDataHash.remove(((LzzSensorAlarmData)obj).getId());
		//mLzzSensorAlarmDataHash.put(((LzzSensorAlarmData)obj).getId(), (LzzSensorAlarmData)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzSensorAlarmData> getAllLzzSensorAlarmData() {
		loadLzzSensorAlarmDatas();
		
		List<LzzSensorAlarmData> objects = new ArrayList<LzzSensorAlarmData>();
		for(int i = 0;i < mLzzSensorAlarmDatas.size(); i++)
		{
			objects.add(mLzzSensorAlarmDatas.get(i).clone());
		}
		return objects;
	}

	public LzzSensorAlarmData getLzzSensorAlarmDataById(String id){
		if(null==id) return null;
		loadLzzSensorAlarmDatas();
		if(null==mLzzSensorAlarmDataHash.get(id)) return null;
		return mLzzSensorAlarmDataHash.get(id).clone();
	}
	


}
