package com.lzz.bussecurity.mgr;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.bussecurity.pojo.LzzAuthority;
import com.lzz.bussecurity.pojo.LzzRole;
import com.lzz.bussecurity.pojo.LzzRoleAuthority;
import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.pojo.LzzUserRole;
import com.lzz.bussecurity.service.LzzUserService;

/**
 * 用户管理类
 * @author LIUWEI
 *
 */
public class LzzUserMgr {
	/**
	 * 创建用户
	 * @param uname
	 * @param pwd
	 * @param company_id
	 * @param is_admin
	 * @return
	 */
	private static String createUser(
			String uname, 
			String pwd,
			String company_id,
			String is_admin){
		//新建用户
		LzzUser user = new LzzUser();
		user.setUname(uname);
		try {
			user.setPwd(LzzEncodeMgr.MD5(pwd, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		user.setCompanyId(company_id);
		user.setIsAdmin(is_admin);
		LzzUserService.self().saveLzzUser(user);
		
		//给用户分配系统默认角色
		String default_role = getDefaultRole();
		addUserRole(user.getId(), default_role);
		return user.getId();
	}
	
	/**
	 * 给用户添加角色
	 * @param userid
	 * @param roleid
	 * @return
	 */
	public static String addUserRole(String userid, String roleid){
		LzzUserRole user_role = LzzUserService.self().getLzzUserRoleByUserIdAndRoleId(userid, roleid);
		if(null==user_role){
			user_role = new LzzUserRole();
			user_role.setUserId(userid);
			user_role.setRoleId(roleid);
			LzzUserService.self().saveLzzUserRole(user_role);
		}
		return user_role.getId();
	}
	
	/**
	 * 给角色添加可操作菜单
	 * @param roleid
	 * @param authorityid
	 * @return
	 */
	public static String addRoleAuthoritys(String roleid, String authority_id){
		LzzRoleAuthority role_authoritys = LzzUserService.self().getLzzRoleAuthorityByRoleIdAndAuthorityId(roleid, authority_id);
		if(null==role_authoritys){
			role_authoritys = new LzzRoleAuthority();
			role_authoritys.setRoleId(roleid);
			role_authoritys.setAuthorityId(authority_id);
			LzzUserService.self().saveLzzRoleAuthority(role_authoritys);
		}
		return role_authoritys.getId();
	}
	
	/**
	 * 判断用户是否是管理员
	 * @param userid
	 * @return
	 */
	public static boolean isAdmin(String userid){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		return "1".equals(user.getIsAdmin());
	}
	
	/**
	 * 判断用户是具有相关角色
	 * @param roleid
	 * @return
	 */
	public static boolean hasRole(String userid, String roleid){
		LzzUserRole user_role = LzzUserService.self().getLzzUserRoleByUserIdAndRoleId(userid, roleid);
		return null!=user_role;
	}
	
	/**
	 * 判断用户是否有否菜单的权限
	 * @param userid
	 * @param authorityid
	 * @return
	 */
	public static boolean userHasAuthority(String userid, String authority_name){
		String authorityid = getAuthorityIdByName(authority_name);
		if(null==authorityid) return false;
		List<LzzUserRole> user_roles = LzzUserService.self().getLzzUserRoleListByUserId(userid);
		for(LzzUserRole user_role : user_roles){
			if(roleHasAuthority(user_role.getRoleId(), authorityid)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 获取用户拥有的权限名称
	 * @param userid
	 * @return
	 */
	public static JSONArray getUserAuthorityNames(String userid){
		JSONArray rslt = new JSONArray();
		List<LzzUserRole> user_roles = LzzUserService.self().getLzzUserRoleListByUserId(userid);
		for(LzzUserRole user_role : user_roles){
			List<LzzRoleAuthority> role_authoritys = LzzUserService.self().getLzzRoleAuthorityListByRoleId(user_role.getRoleId());
			for(LzzRoleAuthority role_authority : role_authoritys){
				String authority_name = getAuthorityName(role_authority.getAuthorityId());
				rslt.add(authority_name);
			}
		}
		
		return rslt;
	}
	
	/**
	 * 判断角色是否有否菜单的权限
	 * @param roleid
	 * @param authorityid
	 * @return
	 */
	public static boolean roleHasAuthority(String roleid, String authorityid){
		List<LzzRoleAuthority> role_authoritys = LzzUserService.self().getLzzRoleAuthorityListByRoleId(roleid);
		for(LzzRoleAuthority role_authority : role_authoritys){
			if(null!=authorityid
					&& authorityid.equals(role_authority.getAuthorityId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 通过菜单名称获取菜单ID
	 * @param name
	 * @return
	 */
	public static String getAuthorityIdByName(String name){
		LzzAuthority authority = LzzUserService.self().getLzzAuthorityByName(name);
		if(null==authority) return null;
		return authority.getId();
	}
	
	/**
	 * 通过权限名称
	 * @param name
	 * @return
	 */
	public static String getAuthorityName(String authority_id){
		LzzAuthority authority = LzzUserService.self().getLzzAuthorityById(authority_id);
		if(null==authority) return null;
		return authority.getName();
	}
	
	/**
	 * 获取用户角色的名称列表
	 * @param userid
	 * @return
	 */
	public static List<String> getUserRoleNames(String userid){
		List<String> rslt = new ArrayList<String>();
		
		List<LzzRole> roles = getUserRoles(userid);
		for(LzzRole role : roles){
			rslt.add(role.getName());
		}
		
		return rslt;
	}
	
	/**
	 * 获取用户角色列表
	 * @param userid
	 * @return
	 */
	public static List<LzzRole> getUserRoles(String userid){
		List<LzzRole> rslt = new ArrayList<LzzRole>();
		List<LzzUserRole> user_roles = LzzUserService.self().getLzzUserRoleListByUserId(userid);
		for(LzzUserRole user_role : user_roles){
			LzzRole role = LzzUserService.self().getLzzRoleById(user_role.getRoleId());
			rslt.add(role);
		}
		
		return rslt;
	}
	
	/**
	 * 获取系统默认用户角色
	 * @return 角色ID
	 */
	private static String getDefaultRole(){
		LzzRole role = LzzUserService.self().getLzzRoleByIsDefaultRole("1");
		if(null==role) return null;
		return role.getId();
	}

	/**
	 * 获取用户信息
	 * @param user
	 * @return
	 */
	public static JSONObject getUserJSONInfo(LzzUser user) {
		JSONObject rslt = new JSONObject();
		rslt.put("id", user.getId());
		rslt.put("lastName", user.getLastName());
		rslt.put("userName", user.getUname());
		rslt.put("pwd", user.getPwd());
		
		String roles = LzzUserMgr.getUserRoleNames(user.getId()).toString();
		roles = roles.replaceAll("\\[", "");
		roles = roles.replaceAll("\\]", "");
		rslt.put("roleName", roles);
		
		return rslt;
	}
	
	/**
	 * 获取用户姓名
	 * @param userid
	 * @return
	 */
	public static String getUserLastName(String userid){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		if(null==user) return null;
		return user.getLastName();
	}
	/**
	 * 获取角色信息
	 * @param user
	 * @return
	 */
	public static JSONObject getRoleJSONInfo(LzzRole role) {
		JSONObject rslt = new JSONObject();
		rslt.put("id", role.getId());
		rslt.put("roleName", role.getName());
		return rslt;
	}

	/**
	 * 判断用户是否可以使用该用户名
	 * @param user
	 * @param uname
	 * @return
	 */
	public static boolean checkUname(LzzUser user, String uname) {
		LzzUser user_check = LzzUserService.self().getLzzUserByUname(uname);
		if(null==user_check) return true;
		
		return user_check.getId().equals(user.getId());
	}
}
