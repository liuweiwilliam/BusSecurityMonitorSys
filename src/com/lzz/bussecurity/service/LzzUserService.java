package com.lzz.bussecurity.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.bussecurity.utils.LzzDateUtil;

import com.lzz.bussecurity.cachemgr.*;
import com.lzz.bussecurity.pojo.LzzUser;

import com.lzz.bussecurity.pojo.LzzRole;

import com.lzz.bussecurity.pojo.LzzUserRole;

import com.lzz.bussecurity.pojo.LzzAuthority;

import com.lzz.bussecurity.pojo.LzzRoleAuthority;


public class LzzUserService{

	// Singleton functions ( construction is private)
	private static LzzUserService mSelf;	
	public static LzzUserService self(){
		if(null==mSelf) mSelf = new LzzUserService();
		
		return mSelf;
	}

	private LzzUserService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUser(Object obj) {
		((LzzUser)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUser)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUser(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUser(Object obj) {
		LzzUserCacheMgr.self().delLzzUser(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserById(String id) {
		LzzUser obj = new LzzUser();
		obj.setId(id);
		delLzzUser(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUser(Object obj) {
		((LzzUser)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUser(obj);
	}
	
	public void saveOrUpdateLzzUser(Object obj){
		String id = ((LzzUser)obj).getId();
		LzzUser obj_check = getLzzUserById(id);
		if(null==obj_check){
			saveLzzUser(obj);
		}else{
			updateLzzUser(obj);
		}
	}
	
	private List<LzzUser> getAllLzzUser() {
		return LzzUserCacheMgr.self().getAllLzzUser();
	}
	
	public List<LzzUser> getAllValidLzzUser() {
		List<LzzUser> array_all = getAllLzzUser();
		List<LzzUser> rslt = new ArrayList<LzzUser>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUser> getAllLzzUserIgnoreDr() {
		return getAllLzzUser();
	}
	
	public LzzUser getLzzUserById(String id){
		return LzzUserCacheMgr.self().getLzzUserById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzRole(Object obj) {
		((LzzRole)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzRole)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzRole(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzRole(Object obj) {
		LzzUserCacheMgr.self().delLzzRole(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzRoleById(String id) {
		LzzRole obj = new LzzRole();
		obj.setId(id);
		delLzzRole(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzRole(Object obj) {
		((LzzRole)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzRole(obj);
	}
	
	public void saveOrUpdateLzzRole(Object obj){
		String id = ((LzzRole)obj).getId();
		LzzRole obj_check = getLzzRoleById(id);
		if(null==obj_check){
			saveLzzRole(obj);
		}else{
			updateLzzRole(obj);
		}
	}
	
	private List<LzzRole> getAllLzzRole() {
		return LzzUserCacheMgr.self().getAllLzzRole();
	}
	
	public List<LzzRole> getAllValidLzzRole() {
		List<LzzRole> array_all = getAllLzzRole();
		List<LzzRole> rslt = new ArrayList<LzzRole>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzRole> getAllLzzRoleIgnoreDr() {
		return getAllLzzRole();
	}
	
	public LzzRole getLzzRoleById(String id){
		return LzzUserCacheMgr.self().getLzzRoleById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserRole(Object obj) {
		((LzzUserRole)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserRole)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserRole(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserRole(Object obj) {
		LzzUserCacheMgr.self().delLzzUserRole(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserRoleById(String id) {
		LzzUserRole obj = new LzzUserRole();
		obj.setId(id);
		delLzzUserRole(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserRole(Object obj) {
		((LzzUserRole)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserRole(obj);
	}
	
	public void saveOrUpdateLzzUserRole(Object obj){
		String id = ((LzzUserRole)obj).getId();
		LzzUserRole obj_check = getLzzUserRoleById(id);
		if(null==obj_check){
			saveLzzUserRole(obj);
		}else{
			updateLzzUserRole(obj);
		}
	}
	
	private List<LzzUserRole> getAllLzzUserRole() {
		return LzzUserCacheMgr.self().getAllLzzUserRole();
	}
	
	public List<LzzUserRole> getAllValidLzzUserRole() {
		List<LzzUserRole> array_all = getAllLzzUserRole();
		List<LzzUserRole> rslt = new ArrayList<LzzUserRole>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserRole> getAllLzzUserRoleIgnoreDr() {
		return getAllLzzUserRole();
	}
	
	public LzzUserRole getLzzUserRoleById(String id){
		return LzzUserCacheMgr.self().getLzzUserRoleById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzAuthority(Object obj) {
		((LzzAuthority)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzAuthority)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzAuthority(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzAuthority(Object obj) {
		LzzUserCacheMgr.self().delLzzAuthority(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzAuthorityById(String id) {
		LzzAuthority obj = new LzzAuthority();
		obj.setId(id);
		delLzzAuthority(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzAuthority(Object obj) {
		((LzzAuthority)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzAuthority(obj);
	}
	
	public void saveOrUpdateLzzAuthority(Object obj){
		String id = ((LzzAuthority)obj).getId();
		LzzAuthority obj_check = getLzzAuthorityById(id);
		if(null==obj_check){
			saveLzzAuthority(obj);
		}else{
			updateLzzAuthority(obj);
		}
	}
	
	private List<LzzAuthority> getAllLzzAuthority() {
		return LzzUserCacheMgr.self().getAllLzzAuthority();
	}
	
	public List<LzzAuthority> getAllValidLzzAuthority() {
		List<LzzAuthority> array_all = getAllLzzAuthority();
		List<LzzAuthority> rslt = new ArrayList<LzzAuthority>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzAuthority> getAllLzzAuthorityIgnoreDr() {
		return getAllLzzAuthority();
	}
	
	public LzzAuthority getLzzAuthorityById(String id){
		return LzzUserCacheMgr.self().getLzzAuthorityById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzRoleAuthority(Object obj) {
		((LzzRoleAuthority)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzRoleAuthority)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzRoleAuthority(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzRoleAuthority(Object obj) {
		LzzUserCacheMgr.self().delLzzRoleAuthority(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzRoleAuthorityById(String id) {
		LzzRoleAuthority obj = new LzzRoleAuthority();
		obj.setId(id);
		delLzzRoleAuthority(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzRoleAuthority(Object obj) {
		((LzzRoleAuthority)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzRoleAuthority(obj);
	}
	
	public void saveOrUpdateLzzRoleAuthority(Object obj){
		String id = ((LzzRoleAuthority)obj).getId();
		LzzRoleAuthority obj_check = getLzzRoleAuthorityById(id);
		if(null==obj_check){
			saveLzzRoleAuthority(obj);
		}else{
			updateLzzRoleAuthority(obj);
		}
	}
	
	private List<LzzRoleAuthority> getAllLzzRoleAuthority() {
		return LzzUserCacheMgr.self().getAllLzzRoleAuthority();
	}
	
	public List<LzzRoleAuthority> getAllValidLzzRoleAuthority() {
		List<LzzRoleAuthority> array_all = getAllLzzRoleAuthority();
		List<LzzRoleAuthority> rslt = new ArrayList<LzzRoleAuthority>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzRoleAuthority> getAllLzzRoleAuthorityIgnoreDr() {
		return getAllLzzRoleAuthority();
	}
	
	public LzzRoleAuthority getLzzRoleAuthorityById(String id){
		return LzzUserCacheMgr.self().getLzzRoleAuthorityById(id);
	}



	public LzzUser getLzzUserByUname(String uname){
		List<LzzUser> array_all = getAllValidLzzUser();
		LzzUser rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUname()
				&& array_all.get(i).getUname().equals(uname)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzRole getLzzRoleByName(String name){
		List<LzzRole> array_all = getAllValidLzzRole();
		LzzRole rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getName()
				&& array_all.get(i).getName().equals(name)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzRole getLzzRoleByIsDefaultRole(String isDefaultRole){
		List<LzzRole> array_all = getAllValidLzzRole();
		LzzRole rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getIsDefaultRole()
				&& array_all.get(i).getIsDefaultRole().equals(isDefaultRole)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzAuthority getLzzAuthorityByName(String name){
		List<LzzAuthority> array_all = getAllValidLzzAuthority();
		LzzAuthority rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getName()
				&& array_all.get(i).getName().equals(name)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}


	public List<LzzUser> getLzzUserListByCompanyId(String companyId){
		List<LzzUser> array_all = getAllValidLzzUser();
		List<LzzUser> rslt = new ArrayList<LzzUser>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCompanyId()
				&& array_all.get(i).getCompanyId().equals(companyId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzUserRole> getLzzUserRoleListByUserId(String userId){
		List<LzzUserRole> array_all = getAllValidLzzUserRole();
		List<LzzUserRole> rslt = new ArrayList<LzzUserRole>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzRoleAuthority> getLzzRoleAuthorityListByRoleId(String roleId){
		List<LzzRoleAuthority> array_all = getAllValidLzzRoleAuthority();
		List<LzzRoleAuthority> rslt = new ArrayList<LzzRoleAuthority>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getRoleId()
				&& array_all.get(i).getRoleId().equals(roleId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}


	public LzzUserRole getLzzUserRoleByUserIdAndRoleId(String userId, String roleId){
		List<LzzUserRole> array_all = getAllValidLzzUserRole();
		LzzUserRole rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)
				&& null!=array_all.get(i).getRoleId()
				&& array_all.get(i).getRoleId().equals(roleId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzRoleAuthority getLzzRoleAuthorityByRoleIdAndAuthorityId(String roleId, String authorityId){
		List<LzzRoleAuthority> array_all = getAllValidLzzRoleAuthority();
		LzzRoleAuthority rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getRoleId()
				&& array_all.get(i).getRoleId().equals(roleId)
				&& null!=array_all.get(i).getAuthorityId()
				&& array_all.get(i).getAuthorityId().equals(authorityId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}




}



