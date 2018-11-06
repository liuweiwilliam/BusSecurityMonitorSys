package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzUser;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzUserCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzUser> mLzzUsers;
	private boolean mLzzUsersLoaded;
	private Hashtable<String, LzzUser> mLzzUserHash;


	// Singleton functions ( construction is private)
	private final static LzzUserCacheMgr singleton = new LzzUserCacheMgr ();	
	public static LzzUserCacheMgr self(){
		return singleton;
	}
	public LzzUserCacheMgr getSelf(){
		return self();
	}
	private LzzUserCacheMgr (){
		dao = new LzzDao();
		mLzzUsers = new Vector<LzzUser>();
		mLzzUsersLoaded = false;
		mLzzUserHash = new Hashtable<String,LzzUser>();

	}
	synchronized private boolean loadLzzUsers(){

		if(!mLzzUsersLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUser> array = (List<LzzUser>)dao.queryAll("LzzUser");
			
			for(int i = 0;i < array.size();i++){
				mLzzUsers.add(array.get(i));
				mLzzUserHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUsersLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzUsers();	

		return true;
	}

	public boolean clearCache(){
		mLzzUsers.clear();
		mLzzUsersLoaded = false;
		mLzzUserHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUser(Object obj) {
		loadLzzUsers();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUser obj2 = (LzzUser)obj;
		mLzzUsers.add(obj2);
		mLzzUserHash.put(obj2.getId(), obj2);
	}
	public void delLzzUser(Object obj) {
		loadLzzUsers();

		LzzUser obj2 = (LzzUser)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserHash.get(obj2.getId()));

		for(LzzUser msg : mLzzUsers){
			if(msg.getId().equals(obj2.getId())){
				mLzzUsers.remove(msg);
				break;
			}
		}
		mLzzUserHash.remove(obj2.getId());
		
	}
	public void updateLzzUser(Object obj) {
		loadLzzUsers();
		
		for(int i = 0; i < mLzzUsers.size(); i++){
			if(mLzzUsers.get(i).getId().equals(((LzzUser)obj).getId())){
					mLzzUsers.set(i, (LzzUser) obj);
					break;
			}
		}
		LzzUser tmp = mLzzUserHash.get(((LzzUser)obj).getId());
		tmp.constructWith((LzzUser)obj);
		
		//mLzzUserHash.remove(((LzzUser)obj).getId());
		//mLzzUserHash.put(((LzzUser)obj).getId(), (LzzUser)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUser> getAllLzzUser() {
		loadLzzUsers();
		
		List<LzzUser> objects = new ArrayList<LzzUser>();
		for(int i = 0;i < mLzzUsers.size(); i++)
		{
			objects.add(mLzzUsers.get(i).clone());
		}
		return objects;
	}

	public LzzUser getLzzUserById(String id){
		if(null==id) return null;
		loadLzzUsers();
		if(null==mLzzUserHash.get(id)) return null;
		return mLzzUserHash.get(id).clone();
	}


}
