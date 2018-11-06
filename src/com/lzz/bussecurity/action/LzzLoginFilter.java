package com.lzz.bussecurity.action;

//created by huangwei on 2017/7/25
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lzz.bussecurity.mgr.LzzUserSessionMgr;

//登录过滤器，系统必须先登录成功才能进入主页，但在浏览器里直接输入主页地址，发现也能进入，
//这个肯定不好，毫无安全性可言，登录过滤器可以避免未经登录即可进入主页

public class LzzLoginFilter implements Filter {
	//无需过滤的url放在该ArrayList里面
	List<String> donotfilterurl = new ArrayList<>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterchain) throws IOException, ServletException {
		System.out.println("orig hash : " + arg0.hashCode());
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		
		//System.out.println("request url : " + request.getRequestURI() + ",request hash : " + request.hashCode());
		if(request.getRequestURI().equals("/BusSecurityMonitorSys/")
			|| (request.getRequestURI().indexOf("entering") != -1)
			|| (request.getRequestURI().indexOf(".jpg") != -1)
			|| (request.getRequestURI().indexOf(".JPG") != -1)
			|| (request.getRequestURI().indexOf(".png") != -1) 
			|| (request.getRequestURI().indexOf(".PNG") != -1) 
			|| (request.getRequestURI().indexOf(".bmp") != -1) 
			|| (request.getRequestURI().indexOf(".gif") != -1) 
			|| (request.getRequestURI().indexOf(".ico") != -1)
			|| (request.getRequestURI().indexOf(".jpeg") != -1) 
			|| (request.getRequestURI().indexOf(".JPEG") != -1) 
			|| (request.getRequestURI().indexOf(".js") != -1 && request.getRequestURI().indexOf(".jsp") == -1) 
			|| (request.getRequestURI().indexOf(".css") != -1)
			|| (request.getRequestURI().indexOf(".woff2") != -1)
			|| (request.getRequestURI().indexOf("fonts/icomoon.ttf") != -1)
			|| (request.getRequestURI().indexOf("login.action") != -1)){
			
			filterchain.doFilter(arg0, arg1);
			return;
		}
		
		String session_id = request.getParameter("sessionId");
		if(null==session_id){
			session_id = request.getHeader("sessionId");
		}
		if(null==LzzUserSessionMgr.getLoginUser(session_id)){
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append("{\"success\":\"false\",\"msg\":\"notlogin\"}");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		filterchain.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
