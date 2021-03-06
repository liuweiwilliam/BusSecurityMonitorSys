package com.lzz.bussecurity.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.bussecurity.pojo.LzzUser;

import com.lzz.bussecurity.pojo.LzzRole;

import com.lzz.bussecurity.pojo.LzzUserRole;

import com.lzz.bussecurity.pojo.LzzAuthority;

import com.lzz.bussecurity.pojo.LzzRoleAuthority;


import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.dao.LzzDao;
public class LzzUserCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzUser> mLzzUsers;
	private boolean mLzzUsersLoaded;
	private Hashtable<String, LzzUser> mLzzUserHash;

	private Vector<LzzRole> mLzzRoles;
	private boolean mLzzRolesLoaded;
	private Hashtable<String, LzzRole> mLzzRoleHash;

	private Vector<LzzUserRole> mLzzUserRoles;
	private boolean mLzzUserRolesLoaded;
	private Hashtable<String, LzzUserRole> mLzzUserRoleHash;

	private Vector<LzzAuthority> mLzzAuthoritys;
	private boolean mLzzAuthoritysLoaded;
	private Hashtable<String, LzzAuthority> mLzzAuthorityHash;

	private Vector<LzzRoleAuthority> mLzzRoleAuthoritys;
	private boolean mLzzRoleAuthoritysLoaded;
	private Hashtable<String, LzzRoleAuthority> mLzzRoleAuthorityHash;


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

		mLzzRoles = new Vector<LzzRole>();
		mLzzRolesLoaded = false;
		mLzzRoleHash = new Hashtable<String,LzzRole>();

		mLzzUserRoles = new Vector<LzzUserRole>();
		mLzzUserRolesLoaded = false;
		mLzzUserRoleHash = new Hashtable<String,LzzUserRole>();

		mLzzAuthoritys = new Vector<LzzAuthority>();
		mLzzAuthoritysLoaded = false;
		mLzzAuthorityHash = new Hashtable<String,LzzAuthority>();

		mLzzRoleAuthoritys = new Vector<LzzRoleAuthority>();
		mLzzRoleAuthoritysLoaded = false;
		mLzzRoleAuthorityHash = new Hashtable<String,LzzRoleAuthority>();

	}
	synchronized private boolean loadLzzUsers(){

		if(!mLzzUsersLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUser> array = (List<LzzUser>)dao.queryAll("lzzuser", LzzUser.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzUsers.add(array.get(i));
				mLzzUserHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUsersLoaded = true;
		}
		return true;
	}

	synchronized private boolean loadLzzRoles(){

		if(!mLzzRolesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzRole> array = (List<LzzRole>)dao.queryAll("lzzrole", LzzRole.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzRoles.add(array.get(i));
				mLzzRoleHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzRolesLoaded = true;
		}
		return true;
	}

	synchronized private boolean loadLzzUserRoles(){

		if(!mLzzUserRolesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserRole> array = (List<LzzUserRole>)dao.queryAll("lzzuserrole", LzzUserRole.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzUserRoles.add(array.get(i));
				mLzzUserRoleHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserRolesLoaded = true;
		}
		return true;
	}

	synchronized private boolean loadLzzAuthoritys(){

		if(!mLzzAuthoritysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzAuthority> array = (List<LzzAuthority>)dao.queryAll("lzzauthority", LzzAuthority.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzAuthoritys.add(array.get(i));
				mLzzAuthorityHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzAuthoritysLoaded = true;
		}
		return true;
	}

	synchronized private boolean loadLzzRoleAuthoritys(){

		if(!mLzzRoleAuthoritysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzRoleAuthority> array = (List<LzzRoleAuthority>)dao.queryAll("lzzroleauthority", LzzRoleAuthority.class);
			
			for(int i = 0;i < array.size();i++){
				mLzzRoleAuthoritys.add(array.get(i));
				mLzzRoleAuthorityHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzRoleAuthoritysLoaded = true;
		}
		return true;
	}


	public boolean loadAllDB(){
		loadLzzUsers();	

		loadLzzRoles();	

		loadLzzUserRoles();	

		loadLzzAuthoritys();	

		loadLzzRoleAuthoritys();	

		return true;
	}

	public boolean clearCache(){
		mLzzUsers.clear();
		mLzzUsersLoaded = false;
		mLzzUserHash.clear();

		mLzzRoles.clear();
		mLzzRolesLoaded = false;
		mLzzRoleHash.clear();

		mLzzUserRoles.clear();
		mLzzUserRolesLoaded = false;
		mLzzUserRoleHash.clear();

		mLzzAuthoritys.clear();
		mLzzAuthoritysLoaded = false;
		mLzzAuthorityHash.clear();

		mLzzRoleAuthoritys.clear();
		mLzzRoleAuthoritysLoaded = false;
		mLzzRoleAuthorityHash.clear();

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
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzRole(Object obj) {
		loadLzzRoles();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzRole obj2 = (LzzRole)obj;
		mLzzRoles.add(obj2);
		mLzzRoleHash.put(obj2.getId(), obj2);
	}
	public void delLzzRole(Object obj) {
		loadLzzRoles();

		LzzRole obj2 = (LzzRole)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzRoleHash.get(obj2.getId()));

		for(LzzRole msg : mLzzRoles){
			if(msg.getId().equals(obj2.getId())){
				mLzzRoles.remove(msg);
				break;
			}
		}
		mLzzRoleHash.remove(obj2.getId());
	}
	public void updateLzzRole(Object obj) {
		loadLzzRoles();
		
		for(int i = 0; i < mLzzRoles.size(); i++){
			if(mLzzRoles.get(i).getId().equals(((LzzRole)obj).getId())){
					mLzzRoles.set(i, (LzzRole) obj);
					break;
			}
		}
		LzzRole tmp = mLzzRoleHash.get(((LzzRole)obj).getId());
		tmp.constructWith((LzzRole)obj);
		
		//mLzzRoleHash.remove(((LzzRole)obj).getId());
		//mLzzRoleHash.put(((LzzRole)obj).getId(), (LzzRole)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzRole> getAllLzzRole() {
		loadLzzRoles();
		
		List<LzzRole> objects = new ArrayList<LzzRole>();
		for(int i = 0;i < mLzzRoles.size(); i++)
		{
			objects.add(mLzzRoles.get(i).clone());
		}
		return objects;
	}

	public LzzRole getLzzRoleById(String id){
		if(null==id) return null;
		loadLzzRoles();
		if(null==mLzzRoleHash.get(id)) return null;
		return mLzzRoleHash.get(id).clone();
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserRole(Object obj) {
		loadLzzUserRoles();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserRole obj2 = (LzzUserRole)obj;
		mLzzUserRoles.add(obj2);
		mLzzUserRoleHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserRole(Object obj) {
		loadLzzUserRoles();

		LzzUserRole obj2 = (LzzUserRole)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserRoleHash.get(obj2.getId()));

		for(LzzUserRole msg : mLzzUserRoles){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserRoles.remove(msg);
				break;
			}
		}
		mLzzUserRoleHash.remove(obj2.getId());
	}
	public void updateLzzUserRole(Object obj) {
		loadLzzUserRoles();
		
		for(int i = 0; i < mLzzUserRoles.size(); i++){
			if(mLzzUserRoles.get(i).getId().equals(((LzzUserRole)obj).getId())){
					mLzzUserRoles.set(i, (LzzUserRole) obj);
					break;
			}
		}
		LzzUserRole tmp = mLzzUserRoleHash.get(((LzzUserRole)obj).getId());
		tmp.constructWith((LzzUserRole)obj);
		
		//mLzzUserRoleHash.remove(((LzzUserRole)obj).getId());
		//mLzzUserRoleHash.put(((LzzUserRole)obj).getId(), (LzzUserRole)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserRole> getAllLzzUserRole() {
		loadLzzUserRoles();
		
		List<LzzUserRole> objects = new ArrayList<LzzUserRole>();
		for(int i = 0;i < mLzzUserRoles.size(); i++)
		{
			objects.add(mLzzUserRoles.get(i).clone());
		}
		return objects;
	}

	public LzzUserRole getLzzUserRoleById(String id){
		if(null==id) return null;
		loadLzzUserRoles();
		if(null==mLzzUserRoleHash.get(id)) return null;
		return mLzzUserRoleHash.get(id).clone();
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzAuthority(Object obj) {
		loadLzzAuthoritys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzAuthority obj2 = (LzzAuthority)obj;
		mLzzAuthoritys.add(obj2);
		mLzzAuthorityHash.put(obj2.getId(), obj2);
	}
	public void delLzzAuthority(Object obj) {
		loadLzzAuthoritys();

		LzzAuthority obj2 = (LzzAuthority)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzAuthorityHash.get(obj2.getId()));

		for(LzzAuthority msg : mLzzAuthoritys){
			if(msg.getId().equals(obj2.getId())){
				mLzzAuthoritys.remove(msg);
				break;
			}
		}
		mLzzAuthorityHash.remove(obj2.getId());
	}
	public void updateLzzAuthority(Object obj) {
		loadLzzAuthoritys();
		
		for(int i = 0; i < mLzzAuthoritys.size(); i++){
			if(mLzzAuthoritys.get(i).getId().equals(((LzzAuthority)obj).getId())){
					mLzzAuthoritys.set(i, (LzzAuthority) obj);
					break;
			}
		}
		LzzAuthority tmp = mLzzAuthorityHash.get(((LzzAuthority)obj).getId());
		tmp.constructWith((LzzAuthority)obj);
		
		//mLzzAuthorityHash.remove(((LzzAuthority)obj).getId());
		//mLzzAuthorityHash.put(((LzzAuthority)obj).getId(), (LzzAuthority)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzAuthority> getAllLzzAuthority() {
		loadLzzAuthoritys();
		
		List<LzzAuthority> objects = new ArrayList<LzzAuthority>();
		for(int i = 0;i < mLzzAuthoritys.size(); i++)
		{
			objects.add(mLzzAuthoritys.get(i).clone());
		}
		return objects;
	}

	public LzzAuthority getLzzAuthorityById(String id){
		if(null==id) return null;
		loadLzzAuthoritys();
		if(null==mLzzAuthorityHash.get(id)) return null;
		return mLzzAuthorityHash.get(id).clone();
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzRoleAuthority(Object obj) {
		loadLzzRoleAuthoritys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzRoleAuthority obj2 = (LzzRoleAuthority)obj;
		mLzzRoleAuthoritys.add(obj2);
		mLzzRoleAuthorityHash.put(obj2.getId(), obj2);
	}
	public void delLzzRoleAuthority(Object obj) {
		loadLzzRoleAuthoritys();

		LzzRoleAuthority obj2 = (LzzRoleAuthority)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzRoleAuthorityHash.get(obj2.getId()));

		for(LzzRoleAuthority msg : mLzzRoleAuthoritys){
			if(msg.getId().equals(obj2.getId())){
				mLzzRoleAuthoritys.remove(msg);
				break;
			}
		}
		mLzzRoleAuthorityHash.remove(obj2.getId());
	}
	public void updateLzzRoleAuthority(Object obj) {
		loadLzzRoleAuthoritys();
		
		for(int i = 0; i < mLzzRoleAuthoritys.size(); i++){
			if(mLzzRoleAuthoritys.get(i).getId().equals(((LzzRoleAuthority)obj).getId())){
					mLzzRoleAuthoritys.set(i, (LzzRoleAuthority) obj);
					break;
			}
		}
		LzzRoleAuthority tmp = mLzzRoleAuthorityHash.get(((LzzRoleAuthority)obj).getId());
		tmp.constructWith((LzzRoleAuthority)obj);
		
		//mLzzRoleAuthorityHash.remove(((LzzRoleAuthority)obj).getId());
		//mLzzRoleAuthorityHash.put(((LzzRoleAuthority)obj).getId(), (LzzRoleAuthority)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzRoleAuthority> getAllLzzRoleAuthority() {
		loadLzzRoleAuthoritys();
		
		List<LzzRoleAuthority> objects = new ArrayList<LzzRoleAuthority>();
		for(int i = 0;i < mLzzRoleAuthoritys.size(); i++)
		{
			objects.add(mLzzRoleAuthoritys.get(i).clone());
		}
		return objects;
	}

	public LzzRoleAuthority getLzzRoleAuthorityById(String id){
		if(null==id) return null;
		loadLzzRoleAuthoritys();
		if(null==mLzzRoleAuthorityHash.get(id)) return null;
		return mLzzRoleAuthorityHash.get(id).clone();
	}
	


}
