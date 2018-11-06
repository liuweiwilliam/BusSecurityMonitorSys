package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzBusLine;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzBusLineCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzBusLine> mLzzBusLines;
	private boolean mLzzBusLinesLoaded;
	private Hashtable<String, LzzBusLine> mLzzBusLineHash;


	// Singleton functions ( construction is private)
	private final static LzzBusLineCacheMgr singleton = new LzzBusLineCacheMgr ();	
	public static LzzBusLineCacheMgr self(){
		return singleton;
	}
	public LzzBusLineCacheMgr getSelf(){
		return self();
	}
	private LzzBusLineCacheMgr (){
		dao = new LzzDao();
		mLzzBusLines = new Vector<LzzBusLine>();
		mLzzBusLinesLoaded = false;
		mLzzBusLineHash = new Hashtable<String,LzzBusLine>();

	}
	synchronized private boolean loadLzzBusLines(){

		if(!mLzzBusLinesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzBusLine> array = (List<LzzBusLine>)dao.queryAll("LzzBusLine");
			
			for(int i = 0;i < array.size();i++){
				mLzzBusLines.add(array.get(i));
				mLzzBusLineHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzBusLinesLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzBusLines();	

		return true;
	}

	public boolean clearCache(){
		mLzzBusLines.clear();
		mLzzBusLinesLoaded = false;
		mLzzBusLineHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzBusLine(Object obj) {
		loadLzzBusLines();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzBusLine obj2 = (LzzBusLine)obj;
		mLzzBusLines.add(obj2);
		mLzzBusLineHash.put(obj2.getId(), obj2);
	}
	public void delLzzBusLine(Object obj) {
		loadLzzBusLines();

		LzzBusLine obj2 = (LzzBusLine)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzBusLineHash.get(obj2.getId()));

		for(LzzBusLine msg : mLzzBusLines){
			if(msg.getId().equals(obj2.getId())){
				mLzzBusLines.remove(msg);
				break;
			}
		}
		mLzzBusLineHash.remove(obj2.getId());
		
	}
	public void updateLzzBusLine(Object obj) {
		loadLzzBusLines();
		
		for(int i = 0; i < mLzzBusLines.size(); i++){
			if(mLzzBusLines.get(i).getId().equals(((LzzBusLine)obj).getId())){
					mLzzBusLines.set(i, (LzzBusLine) obj);
					break;
			}
		}
		LzzBusLine tmp = mLzzBusLineHash.get(((LzzBusLine)obj).getId());
		tmp.constructWith((LzzBusLine)obj);
		
		//mLzzBusLineHash.remove(((LzzBusLine)obj).getId());
		//mLzzBusLineHash.put(((LzzBusLine)obj).getId(), (LzzBusLine)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzBusLine> getAllLzzBusLine() {
		loadLzzBusLines();
		
		List<LzzBusLine> objects = new ArrayList<LzzBusLine>();
		for(int i = 0;i < mLzzBusLines.size(); i++)
		{
			objects.add(mLzzBusLines.get(i).clone());
		}
		return objects;
	}

	public LzzBusLine getLzzBusLineById(String id){
		if(null==id) return null;
		loadLzzBusLines();
		if(null==mLzzBusLineHash.get(id)) return null;
		return mLzzBusLineHash.get(id).clone();
	}


}
