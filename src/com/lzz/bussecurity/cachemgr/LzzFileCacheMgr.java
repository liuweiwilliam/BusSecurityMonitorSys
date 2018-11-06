package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzFileInfo;

import com.lzz.bussecurity.pojo.LzzFileGroup;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzFileCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzFileInfo> mLzzFileInfos;
	private boolean mLzzFileInfosLoaded;
	private Hashtable<String, LzzFileInfo> mLzzFileInfoHash;

	private Vector<LzzFileGroup> mLzzFileGroups;
	private boolean mLzzFileGroupsLoaded;
	private Hashtable<String, LzzFileGroup> mLzzFileGroupHash;


	// Singleton functions ( construction is private)
	private final static LzzFileCacheMgr singleton = new LzzFileCacheMgr ();	
	public static LzzFileCacheMgr self(){
		return singleton;
	}
	public LzzFileCacheMgr getSelf(){
		return self();
	}
	private LzzFileCacheMgr (){
		dao = new LzzDao();
		mLzzFileInfos = new Vector<LzzFileInfo>();
		mLzzFileInfosLoaded = false;
		mLzzFileInfoHash = new Hashtable<String,LzzFileInfo>();

		mLzzFileGroups = new Vector<LzzFileGroup>();
		mLzzFileGroupsLoaded = false;
		mLzzFileGroupHash = new Hashtable<String,LzzFileGroup>();

	}
	synchronized private boolean loadLzzFileInfos(){

		if(!mLzzFileInfosLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzFileInfo> array = (List<LzzFileInfo>)dao.queryAll("LzzFileInfo");
			
			for(int i = 0;i < array.size();i++){
				mLzzFileInfos.add(array.get(i));
				mLzzFileInfoHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzFileInfosLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzFileGroups(){

		if(!mLzzFileGroupsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzFileGroup> array = (List<LzzFileGroup>)dao.queryAll("LzzFileGroup");
			
			for(int i = 0;i < array.size();i++){
				mLzzFileGroups.add(array.get(i));
				mLzzFileGroupHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzFileGroupsLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzFileInfos();	

		loadLzzFileGroups();	

		return true;
	}

	public boolean clearCache(){
		mLzzFileInfos.clear();
		mLzzFileInfosLoaded = false;
		mLzzFileInfoHash.clear();

		mLzzFileGroups.clear();
		mLzzFileGroupsLoaded = false;
		mLzzFileGroupHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzFileInfo(Object obj) {
		loadLzzFileInfos();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzFileInfo obj2 = (LzzFileInfo)obj;
		mLzzFileInfos.add(obj2);
		mLzzFileInfoHash.put(obj2.getId(), obj2);
	}
	public void delLzzFileInfo(Object obj) {
		loadLzzFileInfos();

		LzzFileInfo obj2 = (LzzFileInfo)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzFileInfoHash.get(obj2.getId()));

		for(LzzFileInfo msg : mLzzFileInfos){
			if(msg.getId().equals(obj2.getId())){
				mLzzFileInfos.remove(msg);
				break;
			}
		}
		mLzzFileInfoHash.remove(obj2.getId());
		
	}
	public void updateLzzFileInfo(Object obj) {
		loadLzzFileInfos();
		
		for(int i = 0; i < mLzzFileInfos.size(); i++){
			if(mLzzFileInfos.get(i).getId().equals(((LzzFileInfo)obj).getId())){
					mLzzFileInfos.set(i, (LzzFileInfo) obj);
					break;
			}
		}
		LzzFileInfo tmp = mLzzFileInfoHash.get(((LzzFileInfo)obj).getId());
		tmp.constructWith((LzzFileInfo)obj);
		
		//mLzzFileInfoHash.remove(((LzzFileInfo)obj).getId());
		//mLzzFileInfoHash.put(((LzzFileInfo)obj).getId(), (LzzFileInfo)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzFileInfo> getAllLzzFileInfo() {
		loadLzzFileInfos();
		
		List<LzzFileInfo> objects = new ArrayList<LzzFileInfo>();
		for(int i = 0;i < mLzzFileInfos.size(); i++)
		{
			objects.add(mLzzFileInfos.get(i).clone());
		}
		return objects;
	}

	public LzzFileInfo getLzzFileInfoById(String id){
		if(null==id) return null;
		loadLzzFileInfos();
		if(null==mLzzFileInfoHash.get(id)) return null;
		return mLzzFileInfoHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzFileGroup(Object obj) {
		loadLzzFileGroups();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzFileGroup obj2 = (LzzFileGroup)obj;
		mLzzFileGroups.add(obj2);
		mLzzFileGroupHash.put(obj2.getId(), obj2);
	}
	public void delLzzFileGroup(Object obj) {
		loadLzzFileGroups();

		LzzFileGroup obj2 = (LzzFileGroup)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzFileGroupHash.get(obj2.getId()));

		for(LzzFileGroup msg : mLzzFileGroups){
			if(msg.getId().equals(obj2.getId())){
				mLzzFileGroups.remove(msg);
				break;
			}
		}
		mLzzFileGroupHash.remove(obj2.getId());
		
	}
	public void updateLzzFileGroup(Object obj) {
		loadLzzFileGroups();
		
		for(int i = 0; i < mLzzFileGroups.size(); i++){
			if(mLzzFileGroups.get(i).getId().equals(((LzzFileGroup)obj).getId())){
					mLzzFileGroups.set(i, (LzzFileGroup) obj);
					break;
			}
		}
		LzzFileGroup tmp = mLzzFileGroupHash.get(((LzzFileGroup)obj).getId());
		tmp.constructWith((LzzFileGroup)obj);
		
		//mLzzFileGroupHash.remove(((LzzFileGroup)obj).getId());
		//mLzzFileGroupHash.put(((LzzFileGroup)obj).getId(), (LzzFileGroup)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzFileGroup> getAllLzzFileGroup() {
		loadLzzFileGroups();
		
		List<LzzFileGroup> objects = new ArrayList<LzzFileGroup>();
		for(int i = 0;i < mLzzFileGroups.size(); i++)
		{
			objects.add(mLzzFileGroups.get(i).clone());
		}
		return objects;
	}

	public LzzFileGroup getLzzFileGroupById(String id){
		if(null==id) return null;
		loadLzzFileGroups();
		if(null==mLzzFileGroupHash.get(id)) return null;
		return mLzzFileGroupHash.get(id).clone();
	}


}
