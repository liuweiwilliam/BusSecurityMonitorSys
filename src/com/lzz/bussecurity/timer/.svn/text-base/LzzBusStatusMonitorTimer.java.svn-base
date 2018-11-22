package com.lzz.bussecurity.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.hibernate.Transaction;

import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzDicMgr;
import com.lzz.bussecurity.pojo.LzzBus;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.utils.LzzDateUtil;

public class LzzBusStatusMonitorTimer extends TimerTask {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void run() {
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		try{
			List<LzzBus> buses = LzzBusService.self().getAllValidLzzBus();
			for(LzzBus bus : buses){
				String last_time = bus.getLastSensorDataTime();
				if(null==last_time){
					bus.setLastSensorDataTime(LzzDateUtil.getNow("s"));
					LzzBusService.self().updateLzzBus(bus);
					continue;
				}
				
				Date compare_time1 = LzzDateUtil.delayMinute(new Date(), -10);
				Date compare_time2 = LzzDateUtil.delayMinute(new Date(), -60*24*7);
				Date last_time_d = sdf.parse(last_time);
				String status = bus.getStatusId();
				if(last_time_d.before(compare_time1)){//超过了设置的离线时长
					status = LzzDicMgr.offLineStatus;
				}
				
				if(last_time_d.before(compare_time2)){//超过了设置的长期故障离线时长
					status = LzzDicMgr.longOffLineStatus;
				}
				
				if(!status.equals(bus.getStatusId())){
					bus.setStatusId(status);
					LzzBusService.self().updateLzzBus(bus);
				}
			}
			
			ts.commit();
		}catch(Exception e){
			ts.rollback();
		}finally{
			LzzFactory.closeSession();
		}
	}
}
