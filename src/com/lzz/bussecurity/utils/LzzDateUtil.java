package com.lzz.bussecurity.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class LzzDateUtil {
    
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
	public static String getPreDay(String specifiedDay){ 
		
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = format.parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String preDay = format.format(c.getTime()); 
		return preDay; 
	} 
	
	public static String getNextDay(String specifiedDay){ 
		
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = format.parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 

		String nextDay = format.format(c.getTime()); 
		return nextDay; 
	} 
    
	public static List<Dictionary<String, String>> getDateBetween(String sdate, String edate) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<Dictionary<String, String>> rslt = new ArrayList<Dictionary<String,String>>();
		
		String last_day = lastDayOfMonth(sdate);
		String first_day = sdate;
		while(last_day.compareTo(edate)<0){
			Dictionary<String, String> dic = new Hashtable<String, String>();
			dic.put("sdate", first_day);
			dic.put("edate", last_day);
			rslt.add(dic);
			last_day = lastDayOfNextMonth(last_day);
			first_day = firstDayOfMonth(last_day);
		}
		
		Dictionary<String, String> dic = new Hashtable<String, String>();
		dic.put("sdate", first_day);
		dic.put("edate", edate);
		rslt.add(dic);
		
		return rslt;
	}
	
	public static String lastDayOfMonth(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));
        calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DATE, 0);  
	    return df.format(calendar.getTime());
	}
	
	private static String lastDayOfNextMonth(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));
        calendar.add(Calendar.MONTH, 2);  
        calendar.set(Calendar.DATE, 0);  
	    return df.format(calendar.getTime());
	}

	private static String firstDayOfMonth(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));
        calendar.set(Calendar.DATE, 1);
	    return df.format(calendar.getTime());
	}
	
	public static String lastDayOfWeek(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal =Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		try {
			if(sdf.parse(date).after(cal.getTime())){
				cal.add(Calendar.WEEK_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String rslt = sdf2.format(cal.getTime());
		return rslt;
	}
	
	public static String firstDayOfWeek(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal =Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		try {
			if(cal.getTime().after(sdf.parse(date))){
				cal.add(Calendar.WEEK_OF_YEAR, -1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String rslt = sdf2.format(cal.getTime());
		return rslt;
	}
	
	public static String getNow(String type){
		SimpleDateFormat sdf = null;
		switch(type){
		case "y":
			sdf = new SimpleDateFormat("yyyy");
			break;
		case "M":
			sdf = new SimpleDateFormat("yyyy-MM");
			break;
		case "d":
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case "d2":
			sdf = new SimpleDateFormat("yyyyMMdd");
			break;
		case "H":
			sdf = new SimpleDateFormat("yyyy-MM-dd HH");
			break;
		case "m":
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		case "s":
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		default:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 判断par时间是否在start_time 和 end_time中间
	 * @param par 参数时间， 格式yyyy-MM-dd HH:mm:ss
	 * @param start_time 时间段开始时间， 格式yyyy-MM-dd HH:mm:ss
	 * @param end_time 时间段结束时间，格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean isBetween(String par, String start_time, String end_time){
		boolean rslt = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(sdf.parse(par).after(sdf.parse(start_time))
					&& sdf.parse(end_time).after(sdf.parse(par))){
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rslt;
	}
	
	/**
	 * 获取N天前的日期字符串
	 * @param date 日期
	 * @param n 提前天数
	 * @return
	 */
	public static Date preNDaysByDate(Date date, int n){
		Calendar c = Calendar.getInstance();
		Date par_date=date;
		c.setTime(par_date);
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-n); 
		return c.getTime(); 
	}
	
	/**
	 * 获取N天前的时间字符串
	 * @param time 时间
	 * @param n 提前天数
	 * @return
	 */
	public static Date preNDaysByTime(Date time, int n){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-n); 

		return c.getTime();
	}
	
	/**
	 * 推迟分钟
	 * @param date
	 * @param dalay_min
	 * @return
	 */
	public static Date delayMinute(Date date, int dalay_min){
		Date rslt = null;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, dalay_min);
		rslt = c.getTime();
		return rslt;
	}
	
	public static boolean inOneMonth(Date date){
		if(null==date) return false;
		Date cur = new Date();
		return date.before(cur) && preNDaysByDate(cur, 30).before(date);
	}
	
}