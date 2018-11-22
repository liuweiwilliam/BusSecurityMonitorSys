package com.lzz.bussecurity.mgr;

import java.util.List;

import org.hibernate.Transaction;

import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.pojo.LzzID;
import com.lzz.bussecurity.service.LzzIDService;

public class LzzIDMgr {
	private static LzzIDMgr mSelf;
	
	private static Long smCurID = 0l;
	private static int smCurRestID = 0;
	private static int smBlockSize = 10000;
	private static int smInitStartID = 100000;
	
	public static LzzIDMgr self(){
		if(null==mSelf){
			mSelf = new LzzIDMgr();
		}
		
		return mSelf;
	}
	
	public String getID(){
		return getIDLong()+"";
	}
	
	synchronized private static Long getIDLong(){
		if(smCurRestID==0){
			getNewIDBlock();
		}

		smCurRestID--;
		return smCurID++;
	}

	private static void getNewIDBlock() {
		// TODO Auto-generated method stub
		LzzID id = getLzzID();
		
		smCurID = Long.parseLong(id.getNextid());
		smCurRestID = smBlockSize;
		Long next_id = smCurID + smBlockSize;
		id.setNextid(next_id+"");
		LzzIDService.self().updateLzzID(id);
	}
	
	private static LzzID getLzzID(){
		List<LzzID> ids = LzzIDService.self().getAllValidLzzID();
		
		if(ids.size()>0) return ids.get(0);
		
		LzzID id = new LzzID();
		id.setId("1");
		id.setNextid(""+smInitStartID);
		id.setDr("0");
		LzzIDService.self().saveLzzID(id);
		
		return id.clone();
	}
}
