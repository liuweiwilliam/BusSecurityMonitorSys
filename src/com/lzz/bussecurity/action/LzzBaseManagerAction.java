package com.lzz.bussecurity.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.lzz.bussecurity.mgr.LzzUserSessionMgr;
import com.lzz.bussecurity.pojo.LzzUser;
import com.lzz.bussecurity.service.LzzUserService;
import com.opensymphony.xwork2.ActionSupport;

public class LzzBaseManagerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ServletContextAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;
	protected LzzUser curUser;
	protected String postData;

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
		postData = request.getParameter("postData");
		if(null!=postData){
			try {
				postData = new String(postData.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				
			}
		}
		this.session = request.getSession();
		String session_id = request.getHeader("sessionId");
		if(null!=session_id){
			curUser = LzzUserSessionMgr.getLoginUser(session_id);
		}
	}
}
