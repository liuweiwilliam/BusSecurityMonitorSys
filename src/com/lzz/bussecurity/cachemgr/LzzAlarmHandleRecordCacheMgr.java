package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzAlarmHandleRecord;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzAlarmHandleRecordCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzAlarmHandleRecord> mLzzAlarmHandleRecords;
	private boolean mLzzAlarmHandleRecordsLoaded;
	private Hashtable<String, LzzAlarmHandleRecord> mLzzAlarmHandleRecordHash;


	// Singleton functions ( construction is private)
	private final static LzzAlarmHandleRecordCacheMgr singleton = new LzzAlarmHandleRecordCacheMgr ();	
	public static LzzAlarmHandleRecordCacheMgr self(){
		return singleton;
	}
	public LzzAlarmHandleRecordCacheMgr getSelf(){
		return self();
	}
	private LzzAlarmHandleRecordCacheMgr (){
		dao = new LzzDao();
		mLzzAlarmHandleRecords = new Vector<LzzAlarmHandleRecord>();
		mLzzAlarmHandleRecordsLoaded = false;
		mLzzAlarmHandleRecordHash = new Hashtable<String,LzzAlarmHandleRecord>();

	}
	synchronized private boolean loadLzzAlarmHandleRecords(){

		if(!mLzzAlarmHandleRecordsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzAlarmHandleRecord> array = (List<LzzAlarmHandleRecord>)dao.queryAll("lzzalarmhandlerecord", LzzAlarmHandleRecord.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzAlarmHandleRecords.add(array.get(i));
				mLzzAlarmHandleRecordHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzAlarmHandleRecordsLoaded = true;
		}
		return true;
	}


	public boolean loadAllDB(){
		loadLzzAlarmHandleRecords();	

		return true;
	}

	public boolean clearCache(){
		mLzzAlarmHandleRecords.clear();
		mLzzAlarmHandleRecordsLoaded = false;
		mLzzAlarmHandleRecordHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzAlarmHandleRecord(Object obj) {
		loadLzzAlarmHandleRecords();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzAlarmHandleRecord obj2 = (LzzAlarmHandleRecord)obj;
		mLzzAlarmHandleRecords.add(obj2);
		mLzzAlarmHandleRecordHash.put(obj2.getId(), obj2);
	}
	public void delLzzAlarmHandleRecord(Object obj) {
		loadLzzAlarmHandleRecords();

		LzzAlarmHandleRecord obj2 = (LzzAlarmHandleRecord)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzAlarmHandleRecordHash.get(obj2.getId()));

		for(LzzAlarmHandleRecord msg : mLzzAlarmHandleRecords){
			if(msg.getId().equals(obj2.getId())){
				mLzzAlarmHandleRecords.remove(msg);
				break;
			}
		}
		mLzzAlarmHandleRecordHash.remove(obj2.getId());
	}
	public void updateLzzAlarmHandleRecord(Object obj) {
		loadLzzAlarmHandleRecords();
		
		for(int i = 0; i < mLzzAlarmHandleRecords.size(); i++){
			if(mLzzAlarmHandleRecords.get(i).getId().equals(((LzzAlarmHandleRecord)obj).getId())){
					mLzzAlarmHandleRecords.set(i, (LzzAlarmHandleRecord) obj);
					break;
			}
		}
		LzzAlarmHandleRecord tmp = mLzzAlarmHandleRecordHash.get(((LzzAlarmHandleRecord)obj).getId());
		tmp.constructWith((LzzAlarmHandleRecord)obj);
		
		//mLzzAlarmHandleRecordHash.remove(((LzzAlarmHandleRecord)obj).getId());
		//mLzzAlarmHandleRecordHash.put(((LzzAlarmHandleRecord)obj).getId(), (LzzAlarmHandleRecord)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzAlarmHandleRecord> getAllLzzAlarmHandleRecord() {
		loadLzzAlarmHandleRecords();
		
		List<LzzAlarmHandleRecord> objects = new ArrayList<LzzAlarmHandleRecord>();
		for(int i = 0;i < mLzzAlarmHandleRecords.size(); i++)
		{
			objects.add(mLzzAlarmHandleRecords.get(i).clone());
		}
		return objects;
	}

	public LzzAlarmHandleRecord getLzzAlarmHandleRecordById(String id){
		if(null==id) return null;
		loadLzzAlarmHandleRecords();
		if(null==mLzzAlarmHandleRecordHash.get(id)) return null;
		return mLzzAlarmHandleRecordHash.get(id).clone();
	}
	


}
