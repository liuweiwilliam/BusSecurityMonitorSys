package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzCompany;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzCompanyCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzCompany> mLzzCompanys;
	private boolean mLzzCompanysLoaded;
	private Hashtable<String, LzzCompany> mLzzCompanyHash;


	// Singleton functions ( construction is private)
	private final static LzzCompanyCacheMgr singleton = new LzzCompanyCacheMgr ();	
	public static LzzCompanyCacheMgr self(){
		return singleton;
	}
	public LzzCompanyCacheMgr getSelf(){
		return self();
	}
	private LzzCompanyCacheMgr (){
		dao = new LzzDao();
		mLzzCompanys = new Vector<LzzCompany>();
		mLzzCompanysLoaded = false;
		mLzzCompanyHash = new Hashtable<String,LzzCompany>();

	}
	synchronized private boolean loadLzzCompanys(){

		if(!mLzzCompanysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzCompany> array = (List<LzzCompany>)dao.queryAll("LzzCompany");
			
			for(int i = 0;i < array.size();i++){
				mLzzCompanys.add(array.get(i));
				mLzzCompanyHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzCompanysLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzCompanys();	

		return true;
	}

	public boolean clearCache(){
		mLzzCompanys.clear();
		mLzzCompanysLoaded = false;
		mLzzCompanyHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCompany(Object obj) {
		loadLzzCompanys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzCompany obj2 = (LzzCompany)obj;
		mLzzCompanys.add(obj2);
		mLzzCompanyHash.put(obj2.getId(), obj2);
	}
	public void delLzzCompany(Object obj) {
		loadLzzCompanys();

		LzzCompany obj2 = (LzzCompany)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzCompanyHash.get(obj2.getId()));

		for(LzzCompany msg : mLzzCompanys){
			if(msg.getId().equals(obj2.getId())){
				mLzzCompanys.remove(msg);
				break;
			}
		}
		mLzzCompanyHash.remove(obj2.getId());
		
	}
	public void updateLzzCompany(Object obj) {
		loadLzzCompanys();
		
		for(int i = 0; i < mLzzCompanys.size(); i++){
			if(mLzzCompanys.get(i).getId().equals(((LzzCompany)obj).getId())){
					mLzzCompanys.set(i, (LzzCompany) obj);
					break;
			}
		}
		LzzCompany tmp = mLzzCompanyHash.get(((LzzCompany)obj).getId());
		tmp.constructWith((LzzCompany)obj);
		
		//mLzzCompanyHash.remove(((LzzCompany)obj).getId());
		//mLzzCompanyHash.put(((LzzCompany)obj).getId(), (LzzCompany)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzCompany> getAllLzzCompany() {
		loadLzzCompanys();
		
		List<LzzCompany> objects = new ArrayList<LzzCompany>();
		for(int i = 0;i < mLzzCompanys.size(); i++)
		{
			objects.add(mLzzCompanys.get(i).clone());
		}
		return objects;
	}

	public LzzCompany getLzzCompanyById(String id){
		if(null==id) return null;
		loadLzzCompanys();
		if(null==mLzzCompanyHash.get(id)) return null;
		return mLzzCompanyHash.get(id).clone();
	}


}
