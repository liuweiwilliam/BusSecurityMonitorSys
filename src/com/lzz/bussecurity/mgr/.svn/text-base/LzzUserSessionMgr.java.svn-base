package com.lzz.bussecurity.mgr;

import java.util.Hashtable;
import java.util.UUID;

import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.service.LzzUserService;

public class LzzUserSessionMgr {
	/**
	 * 用户ID到sessionId的对应
	 */
	public static Hashtable<String, String> userSessionIds = new Hashtable<String, String>();
	public static Hashtable<String, String> sessionUserIds = new Hashtable<String, String>();
	
	/**
	 * 记录用户登录信息
	 * @param user 用户对象
	 * @return 会话ID sessionid
	 */
	public static String recordLoginUser(LzzUser user){
		removeLoginUser(user.getId());
		String new_sessionid = getSessionId();
		userSessionIds.put(user.getId(), new_sessionid);
		sessionUserIds.put(new_sessionid, user.getId());
		return new_sessionid;
	}

	/**
	 * 将登陆的用户从记录中删除
	 * @param user
	 */
	private static void removeLoginUser(String user_id) {
		// TODO Auto-generated method stub
		if(userSessionIds.size()!=0
				&& userSessionIds.containsKey(user_id)){
			String session_id = userSessionIds.get(user_id);
			userSessionIds.remove(user_id);
			sessionUserIds.remove(session_id);
		}
	}
	
	/**
	 * 通过session_id查找用户对象
	 * @param session_id
	 * @return
	 */
	public static LzzUser getLoginUser(String session_id){
		if(null==session_id) return null;
		
		if(sessionUserIds.size()==0
				|| !sessionUserIds.containsKey(session_id)){
			return null;
		}
		
		String userid = sessionUserIds.get(session_id);
		return LzzUserService.self().getLzzUserById(userid);
	}
	
	/**
	 * 产生一个随机sessionId
	 * @return
	 */
	private static String getSessionId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}
