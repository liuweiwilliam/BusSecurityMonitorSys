package com.lzz.bussecurity.mgr;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.bussecurity.pojo.LzzCompany;
import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.service.LzzCompanyService;
import com.lzz.bussecurity.service.LzzUserService;

public class LzzCompanyMgr {
	public static JSONObject getCompanyList(String userid){
		JSONObject rslt = new JSONObject();
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		if(null==user) return rslt;
		String company_id = user.getCompanyId();
		return getChildCompanys(company_id);
	}
	
	public static JSONObject getChildCompanys(String company_id){
		JSONObject rslt = new JSONObject();
		
		LzzCompany cur_company = LzzCompanyService.self().getLzzCompanyById(company_id);
		rslt.put("id", company_id);
		rslt.put("companyName", cur_company.getName());
		
		JSONArray under_companys = new JSONArray();
		List<LzzCompany> companys_under = LzzCompanyService.self().getLzzCompanyListByParentId(company_id);
		for(LzzCompany company : companys_under){
			JSONObject sin = new JSONObject();
			sin.put("id", company.getId());
			sin.put("companyName", company.getName());
			sin.put("childCompanys", getChildCompanys(company.getId()));
			under_companys.add(sin);
		}
		rslt.put("childCompanys", under_companys);
			
		return rslt;
	}
}
