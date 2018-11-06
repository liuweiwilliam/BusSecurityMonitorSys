package com.lzz.bussecurity.timer;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.time.DateUtils;

import com.lzz.bussecurity.listener.LzzSocketListener;
import com.lzz.bussecurity.utils.LzzThread;

public class LzzBaseTaskManager implements ServletContextListener {
	 /**  
	  * 每天的毫秒数  
	  */  
	public static final long PERIOD_DAY = DateUtils.MILLIS_PER_DAY;
	/**  
	  * 一周内的毫秒数  
	  */  
	public static final long PERIOD_WEEK = PERIOD_DAY * 7;  
	 
	 /**  
	  * 无延迟  
	  */  
	public static final long NO_DELAY = 0;  
	
	private Timer timer;  

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(null!=timer){
			timer.cancel();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//timer = new Timer("数据库表备份",true);
		//timer.schedule(new LzzBaseTimerHandler(), NO_DELAY, 1000 * 60);
		System.out.println("server started");
		new LzzThread("com.lzz.bussecurity.listener.LzzSocketListener", "startListen").start();
		new LzzThread("com.lzz.bussecurity.listener.LzzDatagramSocketListener", "startListen").start();
		new LzzThread("com.lzz.bussecurity.listener.LzzHttpListener", "startListen").start();
	}
	
}
