package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzCamera;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzCameraCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzCamera> mLzzCameras;
	private boolean mLzzCamerasLoaded;
	private Hashtable<String, LzzCamera> mLzzCameraHash;


	// Singleton functions ( construction is private)
	private final static LzzCameraCacheMgr singleton = new LzzCameraCacheMgr ();	
	public static LzzCameraCacheMgr self(){
		return singleton;
	}
	public LzzCameraCacheMgr getSelf(){
		return self();
	}
	private LzzCameraCacheMgr (){
		dao = new LzzDao();
		mLzzCameras = new Vector<LzzCamera>();
		mLzzCamerasLoaded = false;
		mLzzCameraHash = new Hashtable<String,LzzCamera>();

	}
	synchronized private boolean loadLzzCameras(){

		if(!mLzzCamerasLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzCamera> array = (List<LzzCamera>)dao.queryAll("LzzCamera");
			
			for(int i = 0;i < array.size();i++){
				mLzzCameras.add(array.get(i));
				mLzzCameraHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzCamerasLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzCameras();	

		return true;
	}

	public boolean clearCache(){
		mLzzCameras.clear();
		mLzzCamerasLoaded = false;
		mLzzCameraHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCamera(Object obj) {
		loadLzzCameras();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzCamera obj2 = (LzzCamera)obj;
		mLzzCameras.add(obj2);
		mLzzCameraHash.put(obj2.getId(), obj2);
	}
	public void delLzzCamera(Object obj) {
		loadLzzCameras();

		LzzCamera obj2 = (LzzCamera)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzCameraHash.get(obj2.getId()));

		for(LzzCamera msg : mLzzCameras){
			if(msg.getId().equals(obj2.getId())){
				mLzzCameras.remove(msg);
				break;
			}
		}
		mLzzCameraHash.remove(obj2.getId());
		
	}
	public void updateLzzCamera(Object obj) {
		loadLzzCameras();
		
		for(int i = 0; i < mLzzCameras.size(); i++){
			if(mLzzCameras.get(i).getId().equals(((LzzCamera)obj).getId())){
					mLzzCameras.set(i, (LzzCamera) obj);
					break;
			}
		}
		LzzCamera tmp = mLzzCameraHash.get(((LzzCamera)obj).getId());
		tmp.constructWith((LzzCamera)obj);
		
		//mLzzCameraHash.remove(((LzzCamera)obj).getId());
		//mLzzCameraHash.put(((LzzCamera)obj).getId(), (LzzCamera)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzCamera> getAllLzzCamera() {
		loadLzzCameras();
		
		List<LzzCamera> objects = new ArrayList<LzzCamera>();
		for(int i = 0;i < mLzzCameras.size(); i++)
		{
			objects.add(mLzzCameras.get(i).clone());
		}
		return objects;
	}

	public LzzCamera getLzzCameraById(String id){
		if(null==id) return null;
		loadLzzCameras();
		if(null==mLzzCameraHash.get(id)) return null;
		return mLzzCameraHash.get(id).clone();
	}


}
