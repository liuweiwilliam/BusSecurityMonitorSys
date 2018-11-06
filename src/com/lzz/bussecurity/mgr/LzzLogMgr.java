package com.lzz.bussecurity.mgr;

import java.util.ArrayList;
import java.util.List;

import com.lzz.bussecurity.pojo.LzzBus;
import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.service.LzzDictionaryService;
import com.lzz.bussecurity.service.LzzSensorDataService;
import com.lzz.bussecurity.utils.LzzConstString;

public class LzzLogMgr {
	static String normalStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_normal).getId();
	static String alarmHandlinglStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_AlarmHandling).getId();
	
	/**
	 * 设置公交报警已处理
	 * @param bus_id
	 */
	public static void setPrevAlarmHandled(String bus_id) {
		List<LzzSensorData> datas = getUnHandledAlarm(bus_id);
		for(LzzSensorData data : datas){
			data.setHandled("1");
			LzzSensorDataService.self().updateLzzSensorData(data);
		}
		
		//设置公交状态为报警处理中
		LzzBus bus = LzzBusService.self().getLzzBusById(bus_id);
		bus.setStatusId(alarmHandlinglStatus);
		LzzBusService.self().updateLzzBus(bus);
	}
	
	/**
	 * 获取公交未处理的报警
	 * @param bus_id
	 * @return
	 */
	public static List<LzzSensorData> getUnHandledAlarm(String bus_id){
		List<LzzSensorData> rslt = new ArrayList<LzzSensorData>();
		List<LzzSensorData> datas = LzzSensorDataService.self().getLzzSensorDataListByBusId(bus_id);
		for(int i=0; i<datas.size(); i++){
			LzzSensorData data = datas.get(i);
			if(LzzDicMgr.isAlarmStatus(data.getStatusId())
					&& !"1".equals(data.getHandled())){
				rslt.add(data);
			}
		}
		return rslt;
	}
	
}
